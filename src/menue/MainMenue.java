package menue;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MainMenue extends JPanel {

	private Button btn_Help;
	private Button btn_NewLocalGame;
	private Button btn_NewNetworkGame;
	private Button btn_Highscore;
	private ButtonListener lis_BtnListner = new ButtonListener();
	
	public MainMenue() {
		this.setSize(800, 600);
		
		
		//Buttons erstellen
		btn_Help = new Button("Hilfe");
		btn_NewLocalGame = new Button("Neues lokales Spiel");
		btn_NewNetworkGame = new Button("Neues NetzwerkSpiel");
		btn_Highscore = new Button("Highscore");
		
		//Actioncommand
		btn_Help.setActionCommand("btn_Help");
		btn_NewLocalGame.setActionCommand("btn_NewLocalGame");
		btn_NewNetworkGame.setActionCommand("btn_NewNetworkGame");
		btn_Highscore.setActionCommand("btn_Highscore");
		
		//Actionlistener hinzufügen
		btn_Help.addActionListener(lis_BtnListner);
		btn_NewLocalGame.addActionListener(lis_BtnListner);
		btn_NewNetworkGame.addActionListener(lis_BtnListner);
		btn_Highscore.addActionListener(lis_BtnListner);
		
		//Hinzufügen der Buttons
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
				
			}
			if (e.getActionCommand().equals("btn_Highscore")){
				//Link für Highscore aufrufen
			}
			
		}

	}

}
