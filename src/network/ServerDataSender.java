package network;

import game.Position;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;

import map.Map;

public class ServerDataSender{
	
	private PrintWriter sockOut;
	private Object[][] playerData;
	private int lives;
	private Map map;
	private String playerNames;
		
	//UDP
	private DatagramSocket socket;
	private DatagramPacket paket;
	private byte[]daten = new byte[4];
	private String temp;
	private byte b1, b2, b3, btemp;
	private final static int move = 0, sound = 1, act = 2, item = 3;
	private final static int itemsound = 0, jumpsound = 1;
	private final static int dead = 0, kill = 1, reIt  =2, frame = 3;
	private final static int addItem = 0, removeItem = 1;
	
	public ServerDataSender(String playerNames, Object[][] playerData, Map map, int lives){
		
		this.playerData = playerData;
		this.map = map;
		this.lives = lives;
		this.playerNames = playerNames;
		//UDP
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("UDP ging nicht");
		}
	
		if(init()){
			System.out.println("ServerPositionReceiver hat init und wird gestartet");	
		}
	}
	
	private boolean init(){
		int j = 0;
		while(playerData[j][1] != null){
			sockOut = (PrintWriter)playerData[j][1];
			sockOut.println(lives);//leben werden gesendet
			sockOut.println(playerNames);
			sockOut.println(map.getMapName());
			sockOut.println(j+1);		
			++j;
		}
		return true;
	}
	

	public void sendPosition(int x, int y, int playerNumber){
		int j = 0;
		//UPD
		//temp = ("Move:" + playernumber + ":" + x + ":" + y );
		//daten = temp.getBytes();
		
		
		daten[0] =(byte) (move << 6);  //die 2 bits rachts werden nach links verschoben
		btemp = (byte) ((x) >> 4 ); // x hat 10 bit im ersten byte b1 sind noch 6 bit frei, 
		//deswegen werden vier bit entfernt in dem sie die ganzen 10 bit nach rechtsverschoben werden
		daten[0] = (byte) (daten[0] | btemp);
		daten[1] = (byte) (x << 4);
		btemp = (byte) (y >> 6);
		daten[1] = (byte) (daten[1] | btemp);
		daten[2] = (byte) ( (y << 2) | ((playerNumber)&3));
		
		while(playerData[j][1] != null){

			
			paket = (DatagramPacket)playerData[j][2];
			paket.setData(daten);
			try {
				socket.send(paket);
			} catch (IOException e) {
				System.out.println("UDP senden fehlgeschlagen");
			}
			++j;
		}
	}
	public void sendSound(String soundnummer){
		int j = 0;
		
		daten[0] =(byte) (sound << 6);  //die 2 bits rachts werden nach links verschoben

			if(soundnummer.equals("item")){
				btemp = (byte)(itemsound << 5);
				daten[0] = (byte) (daten[0] | btemp);
			}
			if(soundnummer.equals("jump")){
				btemp = (byte)(jumpsound << 5);
				daten[0] = (byte) (daten[0] | btemp);
			}
			while(playerData[j][1] != null){
				//UDP
				paket = (DatagramPacket)playerData[j][2];
				paket.setData(daten);
				try {
					socket.send(paket);
				} catch (IOException e) {
					System.out.println("UDP senden fehlgeschlagen");
				}
				++j;	
			}
	}
	
	
	public void sendAct(String actNumber, int playerNumber){
		int j = 0;
		
		daten[0] =(byte) (act << 6); 
		
			if(actNumber.equals("dead")){
				btemp = (byte)(dead << 4);
				daten[0] = (byte) (daten[0] | btemp);
				btemp = (byte) (playerNumber << 2);
				daten[0] = (byte) (daten[0] | btemp);
			}
			if(actNumber.equals("kill")){
				btemp = (byte)(kill << 4);
				daten[0] = (byte) (daten[0] | btemp);
				btemp = (byte) (playerNumber << 2);
				daten[0] = (byte) (daten[0] | btemp);
							
			}
			if(actNumber.equals("reIt")){
				btemp = (byte)(reIt << 4);
				daten[0] = (byte) (daten[0] | btemp);
				btemp = (byte) (playerNumber << 2);
				daten[0] = (byte) (daten[0] | btemp);
			}
			if(actNumber.equals("")){
				//für frame gibts extra methode
			}
		
		while(playerData[j][1] != null){

			//udp
			paket = (DatagramPacket)playerData[j][2];
			paket.setData(daten);
			try {
				socket.send(paket);
			} catch (IOException e) {
				System.out.println("UDP senden fehlgeschlagen");
			}
			
			++j;
			
		}	
	}
	
	public void sendItem (String itemNumber, int x, int y, int playerNumber, int ability){
		int j = 0;
		
		if(itemNumber.equals("Item")){
			daten[0] =(byte) (item << 6);  //die 2 bits rachts werden nach links verschoben
			btemp = (byte)(addItem << 5);
			daten[0] = (byte) (daten[0] | btemp);
			
			btemp = (byte)((x>>5)& 31); //oder mit 5 stelliger bit zahl
			daten[0] = (byte) (daten[0] | btemp);
			
			daten[1] = (byte) (x  << 3);
			btemp = (byte) ((y>>7) & 7); //letzten 3
			daten[1] = (byte) (daten[1] | btemp);
			
			daten[2] = (byte) (y << 1);
		}
		if(itemNumber.equals("Itemr")){
			
			daten[0] =(byte) (item << 6);  //die 2 bits rachts werden nach links verschoben
			btemp = (byte)(removeItem << 5);
			daten[0] = (byte) (daten[0] | btemp);
			
			btemp = (byte)((x>>5)& 31); //oder mit 5 stelliger bit zahl
			daten[0] = (byte) (daten[0] | btemp);
			
			daten[1] = (byte) (x  << 3);
			btemp = (byte) ((y>>7) & 7); //letzten 3
			daten[1] = (byte) (daten[1] | btemp);
			
			daten[2] = (byte) (y << 1);
			
			
			daten[3] = (byte) (playerNumber << 6);
			 
			btemp = (byte)(ability << 4);
			daten[3] = (byte) (daten[3] | btemp);
		}
		
		while(playerData[j][1] != null){
			
			//udp
			paket = (DatagramPacket)playerData[j][2];
			paket.setData(daten);
			try {
				socket.send(paket);
			} catch (IOException e) {
				System.out.println("UDP senden fehlgeschlagen");
			}
		++j;
		}
	}
	
	public void sendCurrentFrame(int playerNumber, int currentFrame){
		int j = 0;
		//System.out.println("Sendet current Frame " + currentFrame + " an " + playerNumber);
		daten[0] =(byte) (act << 6); 
		btemp = (byte)(frame << 4);
		daten[0] = (byte) (daten[0] | btemp);
		btemp = (byte) (playerNumber << 2);
		daten[0] = (byte) (daten[0] | btemp);
		
		daten[1] = (byte) (currentFrame << 4);
		
		while(playerData[j][1] != null){
			
			//udp
			paket = (DatagramPacket)playerData[j][2];
			paket.setData(daten);
			try {
				socket.send(paket);
			} catch (IOException e) {
				System.out.println("UDP senden fehlgeschlagen");
			}
		
		++j;
		}
	}
}

////////////////TCP/IP
//
//
//package network;
//
//import game.Position;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.util.Vector;
//
//import map.Map;
//
//public class ServerDataSender{
//	private Vector<FullPlayer> p;
//	private PrintWriter sockOut;
//	private Object[][] playerData;
//	private int lives;
//	private Map map;
//	private String playerNames;
//	private String temp;
//	
//	
////	//UDP
////	DatagramSocket socket;
////	DatagramPacket paket;
////	byte[]daten = new byte[64];
////	
//	//braucht alle Player!! und alle printWriter
//	public ServerDataSender(String playerNames, Object[][] playerData, Map map, int lives){
//		
//		this.playerData = playerData;
//		//this.sockOut = sockOut;
//		this.map = map;
//		this.lives = lives;
//		this.playerNames = playerNames;
////		//UDP
////		try {
////			socket = new DatagramSocket();
////		} catch (SocketException e) {
////			System.out.println("UDP ging nicht");
////		}
//	
//		if(init()){
//			System.out.println("ServerPositionReceiver hat init und wird gestartet");	
//		}
//	}
//	
//	private boolean init(){
//		int j = 0;
//		while(playerData[j][1] != null){
//			//Move_P0_X_100_Y_200
//			sockOut = (PrintWriter)playerData[j][1];
//			sockOut.println(lives);//leben werden gesendet
//			sockOut.println(playerNames);
//			sockOut.println(map.getMapName());
//			sockOut.println(j+1);		
//			++j;
//		}
//		return true;
//	}
//	
//	
//	//schleifen -> for each!!!
//	public void sendPosition(int x, int y, int playernumber){
//		int j = 0;
//		
//		while(playerData[j][1] != null){
//
//			
//			//TCPip
//			sockOut = (PrintWriter)playerData[j][1];
//			sockOut.println("Move:" + playernumber + ":" + x + ":" + y);
//			//UPD
////			temp = ("Move:" + playernumber + ":" + x + ":" + y );
////			daten = temp.getBytes();
////			paket = (DatagramPacket)playerData[j][2];
////			paket.setData(daten);
////			//System.out.println("Am Server gesendet: " + daten.toString());
////			try {
////				socket.send(paket);
////			} catch (IOException e) {
////				System.out.println("UDP senden fehlgeschlagen");
////			}
//			++j;
//		}
//	}
//	public void sendSound(String sound){//playernummer raus
//		int j = 0;
//		//sound ist entweder item oder jump
//		
//			if(sound.equals("item")){
//				temp = ("Sound:" + sound);
//			}
//			if(sound.equals("jump")){
//				temp = ("Sound:" + sound);
//			}
////			daten = temp.getBytes();
//			
//			while(playerData[j][1] != null){
//				//TCP
//				sockOut = (PrintWriter)playerData[j][1];
//				sockOut.println(temp);
//				
//				//UDP
////				paket = (DatagramPacket)playerData[j][2];
////				paket.setData(daten);
////				try {
////					socket.send(paket);
////				} catch (IOException e) {
////					System.out.println("UDP senden fehlgeschlagen");
////				}
//				++j;	
//			}
//	}
//	
//	
//	public void sendAct(String act, int playerNumber){
//		int j = 0;
//		while(playerData[j][1] != null){
//			
//		//tcp
//		sockOut = (PrintWriter)playerData[j][1];
//		sockOut.println("Act:" + act +":" + playerNumber );
//		
//		//udp
////		temp = ("Act:" + act +":" + (playerNumber));
////		daten = temp.getBytes();
////		paket = (DatagramPacket)playerData[j][2];
////		paket.setData(daten);
////		try {
////			socket.send(paket);
////		} catch (IOException e) {
////			System.out.println("UDP senden fehlgeschlagen");
////		}
//		
//		++j;
//		
//		}	
//	}
//	
//	public void sendItem (String itemNumber, int x, int y, int playerNumber, int ability){
//		int j = 0;
//		while(playerData[j][1] != null){
//			
//			//tcp
//			sockOut = (PrintWriter)playerData[j][1];
//			sockOut.println(itemNumber + ":" + x + ":" + y + ":" + playerNumber + ":" + ability);
//			
//			//udp
////			temp = (item);
////			daten = temp.getBytes();
////			paket = (DatagramPacket)playerData[j][2];
////			paket.setData(daten);
////			//System.out.println("Am Server gesendet: " + daten.toString());
////			try {
////				socket.send(paket);
////			} catch (IOException e) {
////				System.out.println("UDP senden fehlgeschlagen");
////			}
//		
//		++j;
//		}
//	}
//	
//	public void sendCurrentFrame(int playerNumber, int currentFrame){
//		int j = 0;
//		while(playerData[j][1] != null){
//			
//			//tpc
//			
//			sockOut = (PrintWriter)playerData[j][1];
//			sockOut.println("Frame:" + playerNumber +":" + currentFrame);
//			
//			//udp
////			temp = ("Frame:" + (playerNumber) +":" + currentFrame);
////			
////			daten = temp.getBytes();
////			paket = (DatagramPacket)playerData[j][2];
////			paket.setData(daten);
////			//System.out.println("Am Server gesendet: " + daten.toString());
////			try {
////				socket.send(paket);
////			} catch (IOException e) {
////				System.out.println("UDP senden fehlgeschlagen");
////			}
//		
//		++j;
//		}
//	}
//}

////////////////UDP

//package network;
//
//import game.Position;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.util.Vector;
//
//import map.Map;
//
//public class ServerDataSender{
//	private Vector<FullPlayer> p;
//	private PrintWriter sockOut;
//	private Object[][] playerData;
//	private int lives;
//	private Map map;
//	private String playerNames;
//	
//	
//	//UDP
//	DatagramSocket socket;
//	DatagramPacket paket;
//	byte[]daten = new byte[64];
//	private String temp;
//	//braucht alle Player!! und alle printWriter
//	public ServerDataSender(String playerNames, Object[][] playerData, Map map, int lives){
//		this.p = p;
//		this.playerData = playerData;
//		//this.sockOut = sockOut;
//		this.map = map;
//		this.lives = lives;
//		
//		this.playerNames = playerNames;
//		//UDP
//		try {
//			socket = new DatagramSocket();
//		} catch (SocketException e) {
//			System.out.println("UDP ging nicht");
//		}
//	
//		if(init()){
//			System.out.println("ServerPositionReceiver hat init und wird gestartet");	
//		}
//	}
//	
//	private boolean init(){
//		int j = 0;
//		while(playerData[j][1] != null){
//			//Move_P0_X_100_Y_200
//			sockOut = (PrintWriter)playerData[j][1];
//			sockOut.println(lives);//leben werden gesendet
//			sockOut.println(playerNames);
//			sockOut.println(map.getMapName());
//			sockOut.println(j+1);		
//			++j;
//		}
//		return true;
//	}
//	
//	private String getPlayerNames(){
//		String s = "";
//		for(int i = 0; i<p.size(); i++){
//			System.out.println(p.get(i).getName() + " wird zum String geadded");
//			s = s + p.get(i).getName() + ":" ;
//		}
//		s = s + p.lastElement().getName();
//		return s;
//	}
//	//schleifen -> for each!!!
//	public void sendPosition(int x, int y, int playernumber){
//		int j = 0;
//		
//		while(playerData[j][1] != null){
//
//			
//			//TCPip
//			//Move_P0_X_100_Y_200
////			sockOut = (PrintWriter)playerData[j][1];
////			sockOut.println("Move:" + playernumber + ":" + x + ":" + y);
//			//UPD
//			temp = ("Move:" + playernumber + ":" + x + ":" + y );
//			daten = temp.getBytes();
//			paket = (DatagramPacket)playerData[j][2];
//			paket.setData(daten);
//			//System.out.println("Am Server gesendet: " + daten.toString());
//			try {
//				socket.send(paket);
//			} catch (IOException e) {
//				System.out.println("UDP senden fehlgeschlagen");
//			}
//			++j;
//		}
//	}
//	public void sendSound(String sound){//playernummer raus
//		int j = 0;
//		//sound ist entweder item oder jump
//		
//			if(sound.equals("item")){
//				temp = ("Sound:" + sound);
//			}
//			if(sound.equals("jump")){
//				temp = ("Sound:" + sound);
//			}
//			daten = temp.getBytes();
//			
//			while(playerData[j][1] != null){
//				//TCP
////				sockOut = (PrintWriter)playerData[j][1];
////				sockOut.println(temp);
//				
//				//UDP
//				paket = (DatagramPacket)playerData[j][2];
//				paket.setData(daten);
//				try {
//					socket.send(paket);
//				} catch (IOException e) {
//					System.out.println("UDP senden fehlgeschlagen");
//				}
//				++j;	
//			}
//	}
//	
//	
//	public void sendAct(String act, int playerNumber){
//		int j = 0;
//		while(playerData[j][1] != null){
//			
//		//tcp
////		sockOut = (PrintWriter)playerData[j][1];
////		sockOut.println("Act:" + act +":" + (playerNumber-1) );
//		
//		//udp
//		temp = ("Act:" + act +":" + (playerNumber));
//		daten = temp.getBytes();
//		paket = (DatagramPacket)playerData[j][2];
//		paket.setData(daten);
//		try {
//			socket.send(paket);
//		} catch (IOException e) {
//			System.out.println("UDP senden fehlgeschlagen");
//		}
//		
//		++j;
//		
//		}	
//	}
//	
//	public void sendItem (String itemNumber, int x, int y, int playerNumber, int ability){
//		int j = 0;
//		while(playerData[j][1] != null){
//	
//			
//				
//				if(itemNumber.equals("Item")){
//					temp = (itemNumber + ":" + x + ":" +y+ ":" + 0 + ":" + 0);
//				}
//				if(itemNumber.equals("Itemr")){
//					temp = (itemNumber + ":" + x + ":" +y+ ":" + playerNumber + ":" + ability);
//
//				}
//			
//
//			//udp
//			//temp = (itemNumber + ":" + x + ":" +y+ "" + playerNumber + ":" + ability);
//			daten = temp.getBytes();
//			paket = (DatagramPacket)playerData[j][2];
//			paket.setData(daten);
//			//System.out.println("Am Server gesendet: " + daten.toString());
//			try {
//				socket.send(paket);
//			} catch (IOException e) {
//				System.out.println("UDP senden fehlgeschlagen");
//			}
//		
//		++j;
//		}
//	}
//	
//	public void sendCurrentFrame(int playerNumber, int currentFrame){
//		int j = 0;
//		while(playerData[j][1] != null){
//			
//			//udp
//			temp = ("Frame:" + (playerNumber) +":" + currentFrame);
//			
//			daten = temp.getBytes();
//			paket = (DatagramPacket)playerData[j][2];
//			paket.setData(daten);
//			//System.out.println("Am Server gesendet: " + daten.toString());
//			try {
//				socket.send(paket);
//			} catch (IOException e) {
//				System.out.println("UDP senden fehlgeschlagen");
//			}
//		
//		++j;
//		}
//	}
//}