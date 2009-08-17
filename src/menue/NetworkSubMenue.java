package menue;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



public class NetworkSubMenue extends JPanel{
	private JButton btn_HostGame;
	private JButton btn_JoinGame;
	private JButton btn_Back;
	private ButtonListener lis_BtnListener = new ButtonListener();
	
public NetworkSubMenue(){
		
		this.setSize(800, 600);
		
		//Buttons erstellen
		btn_JoinGame = new JButton("Netzwerkspiel beitreten");
		btn_HostGame = new JButton("Netzwerkspiel Starten");
		btn_Back = new JButton("Zurück");
		
		//ActionCommand
		btn_JoinGame.setActionCommand("btn_JoinGame");
		btn_HostGame.setActionCommand("btn_HostGame");
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzufügen
		btn_JoinGame.addActionListener(lis_BtnListener);
		btn_HostGame.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzufügen der Buttons
		add(btn_JoinGame);
		add(btn_HostGame);
		add(btn_Back);
		
	
	}
	
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals("btn_JoinGame")){
				BaseFrame.getBaseFrame().setJPanel(new JoinNetworkGameMenue());
			}
			if (e.getActionCommand().equals("btn_HostGame")){
				BaseFrame.getBaseFrame().setJPanel(new CreateNetworkGameMenue());
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
			}
			
		}

	}
	
}
