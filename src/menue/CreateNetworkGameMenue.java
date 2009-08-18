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
		this.setLayout(null);
		this.setSize(800, 600);
		
		//Instanzen erstellen
		btn_Start = new JButton("Start");
		btn_PlayerListenerStartStop = new JButton("Auf Spieler warten");
		btn_Back = new JButton("Zur�ck");
		
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
		
		lbl_ServerName.setBounds(100, 100, 100, 30);
		txt_ServerName.setBounds(250, 100, 100, 30);
		
		lbl_ServerPort.setBounds(100, 150, 100, 30);
		txt_ServerPort.setBounds(250, 150, 100, 30);
		
		lbl_IP.setBounds(100, 200, 100, 30);
		txt_IP.setBounds(250, 200, 100, 30);
		
		lbl_PlayerName.setBounds(100, 250, 100, 30);
		txt_PlayerName.setBounds(250, 250, 100, 30);
		
		lbl_MaxPlayer.setBounds(100, 350, 100, 30);
		txt_MaxPlayer.setBounds(250, 350, 100, 30);
		
		lbl_PlayerLife.setBounds(100, 300, 100, 30);
		txt_PlayerLife.setBounds(250, 300, 100, 30);
		
		btn_Back.setBounds(100, 400, 100, 30);
		btn_PlayerListenerStartStop.setBounds(250, 400, 100, 30);
		btn_Start.setBounds(400, 400, 100, 30);
		
		
		//ActionCommand
		btn_Start.setActionCommand("btn_Start");
		btn_PlayerListenerStartStop.setActionCommand("btn_PlayerListenerStartStop");
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzuf�gen
		btn_Start.addActionListener(lis_BtnListener);
		btn_PlayerListenerStartStop.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzuf�gen der Objekte zum JPane
		add(btn_Start);
		add(btn_PlayerListenerStartStop);
		add(btn_Back);
		add(lbl_ServerName);
		add(lbl_ServerPort);
		add(lbl_IP);
		add(lbl_PlayerName);
		add(lbl_MaxPlayer);
		add(lbl_PlayerLife);
		add(txt_ServerName);
		add(txt_ServerPort);
		add(txt_IP);
		add(txt_PlayerName);
		add(txt_MaxPlayer);
		add(txt_PlayerLife);
		
	}
	
	public String getPort(){
		return txt_ServerPort.getText();
	}
	
	public void setPlayerListenerButton(Boolean b){
		btn_PlayerListenerStartStop.setEnabled(b);
	}
	
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals("btn_Start")){
				System.out.println("btn_Start");
			}
			if (e.getActionCommand().equals("btn_PlayerListenerStartStop")){
				System.out.println("btn_PlayerListenerStartStop");
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			
		}

	}
	

}
