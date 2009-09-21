package menue;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.Map;
import network.ClientGameController;
import network.ClientPositionReceiver;
import network.ClientPositionSender;
import network.FullPlayer;



public class JoinNetworkGameMenue extends GamePanel{
	private GameButton btn_Connect= new GameButton(680, 565,"Verbinden");
	private GameButton btn_Back= new GameButton(20, 565, "Zurück");
	
	private JLabel lbl_ServerPort;
	private JLabel lbl_IP;
	private JLabel lbl_PlayerName;
	
	private JTextField txt_ServerPort;
	private JTextField txt_IP;
	private JTextField txt_PlayerName;

	private ButtonListener lis_BtnListener = new ButtonListener();
	
	private Socket clientSocket = null;
	private PrintWriter sockout = null;
	private BufferedReader sockin = null;
	
	private int playerLifes;
	private String[] playerNames;
	private Map map;
	private int playerNumber;
	
	public JoinNetworkGameMenue(){
		super("gamepanel_mainmenue.jpg");

		//Instanzen erstellen
		
		lbl_ServerPort = new JLabel("Port");
		lbl_IP = new JLabel("IP");
		lbl_PlayerName = new JLabel("Spieler Name");
		
		txt_ServerPort = new JTextField("7777");
		txt_IP = new JTextField("localhost");
		txt_PlayerName = new JTextField("tuuten");
		
		lbl_ServerPort.setBounds(100, 50, 100, 30);
		txt_ServerPort.setBounds(250, 50, 100, 30);
		
		lbl_IP.setBounds(100, 100, 100, 30);
		txt_IP.setBounds(250, 100, 100, 30);
		
		lbl_PlayerName.setBounds(100, 150, 100, 30);
		txt_PlayerName.setBounds(250, 150, 100, 30);

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
	
					sockout.println(txt_PlayerName.getText());
					sockin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				}catch(IOException excep){
					System.out.println(excep.getMessage());
				}
				
				try{
					String input = "";
					//Initialize
					System.out.println("warten auf Init");
					//PlayerLifes
					input = sockin.readLine();
					System.out.println("playerleben bekommen");
					playerLifes = Integer.parseInt(input);
					input = sockin.readLine();
					System.out.println("playernames bekommen" + input);
					playerNames = input.split(":");//Peter:Klaus:Tobi:Nukki
					//input = sockin.readLine();
					//map = new Map(input);
					input = sockin.readLine();
					
					playerNumber = Integer.parseInt(input);
					System.out.println("playerNumber = " + playerNumber);
				}
				catch(Exception ex){
					
				}
				
				
				System.out.println("Init Done");
				
				
				ClientPositionSender cPS = new ClientPositionSender(sockout);
				
				Map map = new Map("Standartmap1.xml");
				Vector<FullPlayer> player = new Vector<FullPlayer>();
				
				for(int i = 0; i<playerNames.length; i++){
					
					if(i == playerNumber){
						player.add(new FullPlayer(playerNames[i], i+1, map, player, false, cPS, playerLifes, null));
						System.out.println("Player number " + i + " wurde erstellt");
					}
					else{
						player.add(new FullPlayer(playerNames[i], i+1, map, player, false, null, playerLifes, null));
						System.out.println("Player number " + i + " wurde erstellt");
					}
					
					
					//player.add(new FullPlayer("tut", 2, map, player, false, cPS));
				}
				
				
				//player.add(new FullPlayer("tut", 1, map, player, false, null));
				//player.add(new FullPlayer("tut", 2, map, player, false, cPS));
				
				ClientGameController cGC = new ClientGameController(player, map, playerNumber);
				
				try {
					new ClientPositionReceiver(sockin, player, clientSocket.getInputStream(), cGC);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Map wird über CPR gesendet und dann auf this.map übertragen
				//wird als xml übertragen map.getXMLData()
				
				
				//Playerlifes wird auch vom CPR empfangen und beim erstellen der Player mitübergeben
				
				//evtl laden fortschritt
				
				//Eigener Player Name
				
				//Textfeld für Gamestats muss angesprochen werden
				
				//Zusätzlich angezeigte Elemente müssen übertragen werden. Wenn CPS die map kenn, kein problem
				
				//ClientGameController cGC = new ClientGameController(player, map, playerNumber);
				
			}
			if (e.getActionCommand().equals("btn_Back")){
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			
		}

	}

}
