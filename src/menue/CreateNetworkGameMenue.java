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


public class CreateNetworkGameMenue extends GamePanel{
	
	private GameButton btn_Start;
	private GameButton btn_PlayerListenerStartStop;
	private GameButton btn_Back;
	
	private JLabel lbl_PlayerName;
	private JLabel lbl_MaxPlayer;
	private JLabel lbl_PlayerLife;
	private JLabel lbl_RegPlayer;
	
	private GameTextField txt_PlayerName;
	private GameTextField txt_MaxPlayer;
	private GameTextField txt_PlayerLife;
	private JList lst_ConnectedPlayer;
	private MapList lst_Maps;
	
	private Object[][] playerData;
	private Vector<FullPlayer> player  = new Vector<FullPlayer>();
	private Vector<String> vtr_PlayerNames = new Vector<String>();
	private PlayerListenerController pLC;
	private CreateNetworkGameMenue cNGM;
	private ButtonListener lis_BtnListener = new ButtonListener();
	
	public CreateNetworkGameMenue(){
		super("gamepanel_mainmenue.jpg");
		this.setLayout(null);
		this.setSize(800, 600);
		
		cNGM = this;
		
		//Instanzen erstellen
		btn_Start = new GameButton(100, 200,"Start");
		btn_PlayerListenerStartStop = new GameButton(250, 200,"");
		btn_PlayerListenerStartStop.setText("Horchen");
		btn_Back = new GameButton(400, 200,"Zurück");
		
		btn_Start.setEnabled(false);
		
		
		lbl_PlayerName = new JLabel("Spieler Name");
		lbl_MaxPlayer = new JLabel("Spieler Anzahl");
		lbl_PlayerLife = new JLabel("Leben");
		lbl_RegPlayer = new JLabel("Angemeldete Spieler");
		
		txt_PlayerName = new GameTextField(250, 50);
		txt_MaxPlayer = new GameTextField(250, 100);
		txt_PlayerLife = new GameTextField(250, 150);
		
		txt_PlayerName.setText("ServerSpieler");
		txt_MaxPlayer.setText("8");
		txt_PlayerLife.setText("10");
		
		lst_ConnectedPlayer = new JList();
		lst_Maps = new MapList(550, 50);
		
		lbl_PlayerName.setBounds(100, 50, 100, 30);
		lbl_MaxPlayer.setBounds(100, 100, 100, 30);
		lbl_PlayerLife.setBounds(100, 150, 100, 30);
		lbl_RegPlayer.setBounds(100, 250, 200, 30);
		
		//btn_Back.setBounds(100, 400, 100, 30);
		//btn_PlayerListenerStartStop.setBounds(250, 400, 100, 30);
		//btn_Start.setBounds(400, 400, 100, 30);
		
		lst_ConnectedPlayer.setBounds(100, 300, 300, 150);
		
		
		//ActionCommand
		btn_Start.setActionCommand("btn_Start");
		btn_PlayerListenerStartStop.setActionCommand("btn_PlayerListenerStartStop");
		btn_Back.setActionCommand("btn_Back");
		
		//Actionlistener hinzufügen
		btn_Start.addActionListener(lis_BtnListener);
		btn_PlayerListenerStartStop.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);
		
		//Hinzufügen der Objekte zum JPane
		add(btn_Start);
		add(btn_PlayerListenerStartStop);
		add(btn_Back);
		
		add(lbl_PlayerName);
		add(lbl_MaxPlayer);
		add(lbl_PlayerLife);
		add(lbl_RegPlayer);
		
		add(txt_PlayerName);
		add(txt_MaxPlayer);
		add(txt_PlayerLife);
		add(lst_ConnectedPlayer);
		add(lst_Maps);
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
	//erstellt die spieler/ map /sender receiver / und übergibt an LocalGameController
	private void startGame(){
		Map map = new Map(lst_Maps.getSelectedMap());
		
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
		
		new NetworkGameController(player, map, sPS);
	}
	
	
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals("btn_Start")){
				startGame();
			}
			if (e.getActionCommand().equals("btn_PlayerListenerStartStop")){
				
				if(true){ //!txt_PlayerName.getText().equals("") && !txt_MaxPlayer.getText().equals("") && !txt_PlayerLife.getText().equals("")
					
					txt_PlayerName.setEditable(false);
					txt_MaxPlayer.setEditable(false);
					txt_PlayerLife.setEditable(false);
					//System.out.println("Im Listener "+  btn_PlayerListenerStartStop.getText() + "arsch");
					if(btn_PlayerListenerStartStop.getText().equals("Horchen")){//Start
						//System.out.println("Im Horchen");
						lst_ConnectedPlayer.setListData(new String[] {});
						pLC = new PlayerListenerController(cNGM);
						btn_PlayerListenerStartStop.setText("Genug gewartet");
						//btn_PlayerListenerStartStop.setEnabled(false);
					}
					else{//Stop
						
						//System.out.println(vtr_PlayerNames.size());//
						if(vtr_PlayerNames.size() >= 1){//mindestens ein player angemeldet
							pLC.interrupt();
							btn_PlayerListenerStartStop.setEnabled(false);
							btn_Start.setEnabled(true);
						}
						else{//kein spieler angemeldet
							//pLC.close();
							/////////////////
							
							btn_PlayerListenerStartStop.setEnabled(true);
						}
						
						//btn_PlayerListenerStartStop.setText("Auf Spiler warten");
						
					}
				}
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
				
				if(pLC != null){
					pLC.interrupt();
					pLC.close();
				}
				
			}
			
		}

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
