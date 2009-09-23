package menue;

import game.Finals;
import game.Player;
import game.PlayerStats;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;
import java.sql.*;

import javax.swing.JLabel;

public class GameStatsMenue extends GamePanel {

	private GameButton btn_Next = new GameButton(680, 565, "Weiter");
	private GameButton btn_Eintragen = new GameButton(570, 565, "Eintragen");
	private Vector<PlayerStats> players = new Vector<PlayerStats>();
	
	private ButtonListener lis_BtnListener = new ButtonListener();

	public GameStatsMenue(Vector<Player> p) {
		super("gamepanel_gamestatsmenue.jpg");
		for(Player player : p){
			players.add(player.getPlayerStats());
		}
		Collections.sort(players);
		
		for(int i = 0; i < players.size(); ++i){
			JLabel label1 = new JLabel(players.get(i).getName());
			JLabel label2 = new JLabel(players.get(i).getKills()+"");
			JLabel label3 = new JLabel(players.get(i).getLifes()+"");
			JLabel label4 = new JLabel(players.get(i).getPoints()+"");
			
			label1.setBounds(260, 200+(i*40), 150, 30);
			label2.setBounds(400, 200+(i*40), 150, 30);
			label3.setBounds(460, 200+(i*40), 150, 30);
			label4.setBounds(520, 200+(i*40), 150, 30);
			
			add(label1);
			add(label2);
			add(label3);
			add(label4);
		}
		
		JLabel name = new JLabel("Name");
		JLabel kills = new JLabel("Kills");
		JLabel leben = new JLabel("Leben");
		JLabel punkte = new JLabel("Punkte");
		
		name.setBounds(260, 160, 150, 30);
		kills.setBounds(400, 160, 150, 30);
		leben.setBounds(460, 160, 150, 30);
		punkte.setBounds(520, 160, 150, 30);

		btn_Next.setActionCommand("btn_Next");
		btn_Eintragen.setActionCommand("btn_Eintragen");
		btn_Next.addActionListener(lis_BtnListener);
		btn_Eintragen.addActionListener(lis_BtnListener);
		
		add(name);
		add(kills);
		add(leben);
		add(punkte);
		add(btn_Next);
		add(btn_Eintragen);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Next")) {
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
			}
			
			if (e.getActionCommand().equals("btn_Eintragen")) {
				writeHighscoreToDB();
			}

		}
		
		private void writeHighscoreToDB(){
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://" + Finals.DB_SERVER + ":3306/" + Finals.DB_NAME;
			
			Connection con = null;
			Statement anw = null;
			ResultSet result = null;
			
			try{
				Class.forName(driver);
				con = DriverManager.getConnection(url, Finals.DB_USER, Finals.DB_PASSWORD);
			}catch(ClassNotFoundException cnfe){
				System.out.println("Konnte Treiber nicht laden");
			}catch(SQLException e){
				System.out.println("Konnte keine Verbindung zur DB herstellen");
			}
			
			try{
				anw = con.createStatement();
			}catch(SQLException e){
				System.out.println("Konnte kein Statement erstellen");
			}
						
			String insertSQL = "INSERT INTO highscore VALUES('" + players.get(0).getName() + "'," + players.get(0).getKills() + "," + 
								players.get(0).getLifes() + "," + players.get(0).getPoints() + ")";
			
			try{
				int queryErfolgreich = anw.executeUpdate(insertSQL);
					System.out.println("queryErfolgreich nach Insert: " + queryErfolgreich);
			}catch(SQLException e){
				System.out.println("Fehler beim ausführen der SQL-Query");
			}
			
			openWebsite(Finals.HIGHSCORE_URL);
		}// Ende Methode writeHighscoreToDB
		
		private void openWebsite(String link) {
			if (!java.awt.Desktop.isDesktopSupported()) {
				System.err.println("Desktop is not supported (fatal)");
				System.exit(1);
			}

			if (link.length() == 0) {
				System.out.println("Usage: OpenURI [URI [URI ... ]]");
				System.exit(0);
			}

			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				System.err
						.println("Desktop doesn't support the browse action (fatal)");
				System.exit(1);
			}

			try {
				java.net.URI uri = new java.net.URI(link);
				desktop.browse(uri);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}// Ende Methode openWebsite
	}
}
