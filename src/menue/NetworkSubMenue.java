package menue;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



public class NetworkSubMenue extends GamePanel{
	private GameButton btn_HostGame = new GameButton(20, 20,"Spiel Starten");
	private GameButton btn_JoinGame = new GameButton(20,55,"Spiel beitreten");
	private GameButton btn_Back = new GameButton(20, 565, "Zur�ck");
	private ButtonListener lis_BtnListener = new ButtonListener();
	
public NetworkSubMenue(){
	super("gamepanel_mainmenue.jpg");
		
		//ActionCommand
		btn_JoinGame.setActionCommand("btn_JoinGame");
		btn_HostGame.setActionCommand("btn_HostGame");
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzuf�gen
		btn_JoinGame.addActionListener(lis_BtnListener);
		btn_HostGame.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzuf�gen der Buttons
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
