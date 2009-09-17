package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.Map;

import network.FullPlayer;

import network.NetworkGameController;
import network.ServerPositionReceiver;
import network.ServerPositionSender;


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
	private JList lst_ConnectedPlayer;
	
	private Object[][] playerData;
	private Vector<FullPlayer> player  = new Vector<FullPlayer>();
	private Vector<String> vtr_PlayerNames = new Vector<String>();
	private PlayerListenerController pLC;
	private CreateNetworkGameMenue cNGM;
	private ButtonListener lis_BtnListener = new ButtonListener();
	
	public CreateNetworkGameMenue(){
		this.setLayout(null);
		this.setSize(800, 600);
		
		cNGM = this;
		
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
		txt_ServerPort = new JTextField("7777");
		txt_IP = new JTextField();
		txt_PlayerName = new JTextField();
		txt_MaxPlayer = new JTextField();
		txt_PlayerLife = new JTextField();
		
		lst_ConnectedPlayer = new JList();
		
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
		
		lst_ConnectedPlayer.setBounds(550, 50, 200, 400);
		
		
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
		add(lst_ConnectedPlayer);
		
	}
	
	public int getPort(){
		return Integer.parseInt(txt_ServerPort.getText());
	}
	
	public void setPlayerList(Vector<String> l){
		lst_ConnectedPlayer.setListData(l);
		vtr_PlayerNames = l;
	}
	
	
	public void setPlayerData(Object[][] data){
		this.playerData = data;
		//0 = SockIn
		//1 = SockOut
	}
	//erstellt die spieler/ map /sender receiver / und �bergibt an LocalGameController
	private void startGame(){
		Map map = new Map("TestMap.xml");
		
		ServerPositionSender sPS = new ServerPositionSender(getPlayerNames2(), playerData, map, 4);
		//map erstellen
		
		//Spieler erstellen + sender Receiver erstellen
		int i = 0;
		player.add((new FullPlayer("serverSpieler",1, map, player, true, null, 10, sPS)));
		
		System.out.println("server spieler erstellt");
		++i;
		while(playerData[i-1][0] != null){
//			sPS = new ServerPositionSender(player, playerData, map, 4);
			
			player.add(new FullPlayer(vtr_PlayerNames.get(i-1), i+1, map, player, false, null, 10, sPS));//erstellt einen Normalen Player und den rest HostPlayer
			new ServerPositionReceiver((FullPlayer)player.get(i), (BufferedReader)playerData[i-1][0]);
			
			++i;
			System.out.println("andere spieler erstellt");
			
		}
		
		new NetworkGameController(player, map);
	}
	
	
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals("btn_Start")){
				startGame();
			}
			if (e.getActionCommand().equals("btn_PlayerListenerStartStop")){
				
				if(btn_PlayerListenerStartStop.getText().equals("Auf Spieler warten")){
					lst_ConnectedPlayer.setListData(new String[] {});
					pLC = new PlayerListenerController(cNGM);
					btn_PlayerListenerStartStop.setText("Genug gewartet");
				}
				else{
					pLC.interrupt();
					
					
					btn_PlayerListenerStartStop.setText("Auf Spiler warten");
					
				}
				
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			
		}

	}
	
	private String getPlayerNames(){
		String s = "";
		for(int i = 0; i<player.size(); i++){
			System.out.println(player.get(i).getName() + " wird zum String geadded");
			s = s + player.get(i).getName() + ":" ;
		}
		s = s + player.lastElement().getName();
		return s;
	}
	
	private String getPlayerNames2(){
		String s = "LocalPlayer";//name aus spieler feld
		for(int i = 0; i<vtr_PlayerNames.size(); i++){
			//System.out.println(vtr_PlayerNames.get(i).getName() + " wird zum String geadded");
			s = s + ":" + vtr_PlayerNames.get(i) ;
		}
		//s = s + player.lastElement().getName();
		System.out.println(s);
		return s;
	}
	
	
	

}
