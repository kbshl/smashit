package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.Player;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class GameStatsMenue extends JPanel{
	
	private JTable tbl_GameStats;
	private JButton btn_Next;
	private Vector<Player> players;
	private double time;
	
	private ButtonListener lis_BtnListener = new ButtonListener();
	
	public GameStatsMenue(Vector<Player> p, double t){
		players = p;
		time = t;
		
		this.setLayout(null);
		this.setSize(800, 600);
		//Instanzen erstellen
		tbl_GameStats = new JTable(5, 5);
		btn_Next = new JButton("Weiter");
		
		tbl_GameStats.setBounds(100, 100, 600, 300);
		btn_Next.setBounds(100, 400, 100, 30);
		//ActionCommand
		btn_Next.setActionCommand("btn_Next");
		
		
		//Actionlistener hinzufügen
		btn_Next.addActionListener(lis_BtnListener);
	
		
		//Hinzufügen der Objekte zum JPane
		add(btn_Next);
		add(tbl_GameStats);
	
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Next")){
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
			}
			
		}

	}

}
