package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class JoinNetworkGameMenue extends JPanel{
	private JButton btn_Connect;
	private JButton btn_Back;
	
	private JLabel lbl_ServerPort;
	private JLabel lbl_IP;
	private JLabel lbl_PlayerName;

	
	private JTextField txt_ServerPort;
	private JTextField txt_IP;
	private JTextField txt_PlayerName;

	private ButtonListener lis_BtnListener = new ButtonListener();
	
	public JoinNetworkGameMenue(){
		
		this.setSize(800, 600);
		//Instanzen erstellen
		btn_Connect = new JButton("Verbinden");
		btn_Back = new JButton("Zur�ck");
		
		lbl_ServerPort = new JLabel("Port");
		lbl_IP = new JLabel("IP");
		lbl_PlayerName = new JLabel("Spieler Name");
		
		txt_ServerPort = new JTextField();
		txt_IP = new JTextField();
		txt_PlayerName = new JTextField();
		
		
		//ActionCommand
		btn_Connect.setActionCommand("btn_Connect");
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzuf�gen
		btn_Connect.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzuf�gen der Objekte zum JPane
		add(btn_Connect);
		add(btn_Back);
		
		add(lbl_ServerPort);
		add(lbl_IP);
		add(lbl_PlayerName);
		
		add(txt_ServerPort);
		add(txt_IP);
		add(txt_PlayerName);
	
	}
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Connect")){
				//tcp_connection aufbauen
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			
		}

	}

}
