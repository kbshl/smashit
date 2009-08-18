package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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
	
	private Socket clientSocket = null;
	private PrintWriter sockout = null;
	
	public JoinNetworkGameMenue(){
		this.setLayout(null);
		this.setSize(800, 600);
		//Instanzen erstellen
		btn_Connect = new JButton("Verbinden");
		btn_Back = new JButton("Zurück");
		
		lbl_ServerPort = new JLabel("Port");
		lbl_IP = new JLabel("IP");
		lbl_PlayerName = new JLabel("Spieler Name");
		
		txt_ServerPort = new JTextField();
		txt_IP = new JTextField();
		txt_PlayerName = new JTextField();
		
		lbl_ServerPort.setBounds(100, 50, 100, 30);
		txt_ServerPort.setBounds(250, 50, 100, 30);
		
		lbl_IP.setBounds(100, 100, 100, 30);
		txt_IP.setBounds(250, 100, 100, 30);
		
		lbl_PlayerName.setBounds(100, 150, 100, 30);
		txt_PlayerName.setBounds(250, 150, 100, 30);
		
		btn_Connect.setBounds(100, 200, 100, 30);
		btn_Back.setBounds(250, 200, 100, 30);
		
		
		//ActionCommand
		btn_Connect.setActionCommand("btn_Connect");
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzufügen
		btn_Connect.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzufügen der Objekte zum JPane
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
				
				try{
					clientSocket = new Socket(txt_IP.getText(), Integer.parseInt(txt_ServerPort.getText()));
					sockout = new PrintWriter(clientSocket.getOutputStream(), true);
	
					sockout.println(lbl_PlayerName.getText());
					
				}catch(IOException excep){
					System.out.println(excep.getMessage());
				}
			
				//tcp_connection aufbauen
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			
		}

	}

}
