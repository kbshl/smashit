package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenue extends JPanel {

	private JButton btn_Help;
	private JButton btn_NewLocalGame;
	private JButton btn_NewNetworkGame;
	private JButton btn_Highscore;
	private ButtonListener lis_BtnListener = new ButtonListener();
	
	public MainMenue() {
		this.setSize(800, 600);
		this.setLayout(null);
		
		
		//Buttons erstellen
		btn_Help = new JButton("Hilfe");
		btn_NewLocalGame = new JButton("Neues lokales Spiel");
		btn_NewNetworkGame = new JButton("Neues NetzwerkSpiel");
		btn_Highscore = new JButton("Highscore");
		
		btn_Help.setBounds(10, 20, 140, 30);
		btn_Highscore.setBounds(10, 60, 140, 30);
		btn_NewLocalGame.setBounds(10, 100, 140, 30);
		btn_NewNetworkGame.setBounds(10, 140, 140, 30);
		
		//Actioncommand
		btn_Help.setActionCommand("btn_Help");
		btn_NewLocalGame.setActionCommand("btn_NewLocalGame");
		btn_NewNetworkGame.setActionCommand("btn_NewNetworkGame");
		btn_Highscore.setActionCommand("btn_Highscore");
		
		//Actionlistener hinzuf�gen
		btn_Help.addActionListener(lis_BtnListener);
		btn_NewLocalGame.addActionListener(lis_BtnListener);
		btn_NewNetworkGame.addActionListener(lis_BtnListener);
		btn_Highscore.addActionListener(lis_BtnListener);
		
		//Hinzuf�gen der Buttons
		add(btn_Help);
		add(btn_Highscore);
		add(btn_NewLocalGame);
		add(btn_NewNetworkGame);
		
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals("btn_Help")){
				//Link im Browser aufrufen
			}
			if (e.getActionCommand().equals("btn_NewLocalGame")){
				BaseFrame.getBaseFrame().setJPanel(new CreateLocalGameMenue());
			}
			if (e.getActionCommand().equals("btn_NewNetworkGame")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			if (e.getActionCommand().equals("btn_Highscore")){
				//Link f�r Highscore aufrufen
			}
			
		}

	}

}
