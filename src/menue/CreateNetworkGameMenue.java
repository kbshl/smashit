package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CreateNetworkGameMenue extends JPanel{
	
	private JButton btn_Start;
	private JButton btn_PlayerListenerStartStop;
	private JButton btn_Back;
	private JLabel lbl_ServerName;
	private JLabel lbl_ServerPort;
	private JLabel lbl_IP;
	private JLabel lbl_PlayerName;
	private JLabel lbl_MaxPlayer;
	private JLabel lbl_PlayerLife;
	private JTextField txt_ServerName;
	private JTextField txt_ServerPort;
	private JTextField txt_IP;
	private JTextField txt_PlayerName;
	private JTextField txt_MaxPlayer;
	private JTextField txt_PlayerLife;
	private ButtonListener lis_BtnListener = new ButtonListener();
	
	public CreateNetworkGameMenue(){
		
		this.setSize(800, 600);
		
		//Instanzen erstellen
		btn_Start = new JButton("tuut");
		btn_PlayerListenerStartStop = new JButton("Netzwerkspiel beitreten");
		btn_Back = new JButton("Zurück");
		
		lbl_ServerName = new JLabel("Servername");
		lbl_ServerPort = new JLabel("Port");
		lbl_IP = new JLabel("IP");
		lbl_PlayerName = new JLabel("Spieler Name");
		lbl_MaxPlayer = new JLabel("Spieler Anzahl");
		lbl_PlayerLife = new JLabel("Leben");
		
		txt_ServerName = new JTextField();
		txt_ServerPort = new JTextField();
		txt_IP = new JTextField();
		txt_PlayerName = new JTextField();
		txt_MaxPlayer = new JTextField();
		txt_PlayerLife = new JTextField();
		
		//ActionCommand
		
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzufügen
	
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzufügen der Buttons
	
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
