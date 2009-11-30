//UDP bit.code

package network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;

import manager.SoundManager;
import map.Item;
import menue.BaseFrame;
import menue.MainMenue;

public class ClientDataReceiver extends Thread{
	
	private Vector<FullPlayer> player;
	private ClientGameController cGC;

	//UPD
	byte[] daten =  new byte[4];
	byte btemp;
	DatagramSocket socket;
	DatagramPacket paket;
	String temp;
	
	private final static int move = 0, sound = 1, act = 2, item = 3;
	private final static int itemsound = 0, jumpsound = 1;
	private final static int dead = 0, kill = 1, reIt  =2, frame = 3;
	private final static int addItem = 0, removeItem = 1;
	private int option1, x, y, playerNumber, frameNumber, ability;
	
	public ClientDataReceiver(BufferedReader sockIn, Vector<FullPlayer> player, InputStream iS, ClientGameController cGC){
		this.player = player;
		this.cGC = cGC;
		
		//UPD
		try {
			socket = new DatagramSocket(7777);
		} catch (SocketException e) {
			System.out.println("UDP geht nicht client");
		}
		paket = new DatagramPacket(daten, daten.length);
		this.start();
	}
	
	public void run(){
		String input = "";
		String[] inputParts;
		
		//????
		byte[] array = new byte[5];
		int playerNo;
		int aktion;
		int x,y;

		while(!this.isInterrupted()){
			
			try{
				//UDP
				socket.receive(paket);
				daten = paket.getData();
				//input = new String(paket.getData(), 0 , paket.getLength());
			}catch(Exception e){
				System.out.println(e.getMessage() );
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
				this.interrupt();
				
			}
			
			//inputParts = input.split(":");
			option1 = (daten[0] >> 6) & 3;
			
			if(option1 == move){
				x = ((daten[0] & 63) << 4);
				btemp = (byte) ((daten[1] >>4)& 15);
				x = ((btemp | x));
				
				y =  ((daten[1] & 15) << 6);
				btemp = (byte) ((daten[2] >> 2) & 63 );
				y =  ((y | btemp) & 1023);
				
				playerNumber = (daten[2] & 3);
				player.get(playerNumber).setX(x);
				player.get(playerNumber).setY(y);
			}
//			
//			if(inputParts[0].equals("Move")){//Move_P0_Event1
//				//System.out.println(input);
//				player.get(Integer.parseInt(inputParts[1])).setX(Integer.parseInt(inputParts[2]));
//				player.get(Integer.parseInt(inputParts[1])).setY(Integer.parseInt(inputParts[3]));
//				
//			
//			}
//			if(inputParts[0].equals("Sound")){//Move_P0_Event1
//				
//				if(inputParts[1].equals("item")){
//					SoundManager.getSoundManager().playSound("item.wav");
//				}
//				if(inputParts[1].equals("jump")){
//					SoundManager.getSoundManager().playSound("jump.wav");
//				}
//				
//				
//			
//			}
//			if(inputParts[0].equals("Act")){//Move_P0_Event1
//				//System.out.println(input);
//				if(inputParts[1].equals("dead")){ //wurde getötet
//					player.get(Integer.parseInt(inputParts[2])).getKilled();
//				}
//				if(inputParts[1].equals("kill")){//hat getötet
//					player.get(Integer.parseInt(inputParts[2])).addKill();
//				}
//				if(inputParts[1].equals("reIt")){//remove item
//					player.get(Integer.parseInt(inputParts[2])).looseItem();
//				}
//			}
//			
//			if(inputParts[0].equals("Item")){//add item on stage
//			
//				cGC.addItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));	
//			}
//			if(inputParts[0].equals("Itemr")){//remobe item from stage
//				
//				cGC.removeItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));
//				player.get(Integer.parseInt(inputParts[3])).setItem(new Item(1,1, Integer.parseInt(inputParts[4])));
//			}
//			if(inputParts[0].equals("Frame")){
//				
//				player.get(Integer.parseInt(inputParts[1])).setCurrentFrame(Integer.parseInt(inputParts[2]));				
//				
//			}	
		}
	}
}

///udp String

//package network;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.util.Vector;
//
//import manager.SoundManager;
//import map.Item;
//import menue.BaseFrame;
//import menue.MainMenue;
//
//public class ClientDataReceiver extends Thread{
//	
//	private BufferedReader sockIn;
//	private Vector<FullPlayer> player;
//	private int playerLifes;
//	private InputStream iS;
//	private ClientGameController cGC;
//	
//	
//	//UPD
//	byte[] daten =  new byte[64];
//	DatagramSocket socket;
//	DatagramPacket paket;
//	String temp;
//	
//	
//	public ClientDataReceiver(BufferedReader sockIn, Vector<FullPlayer> player, InputStream iS, ClientGameController cGC){
//		this.sockIn = sockIn;
//		this.player = player;
//		this.iS = iS;
//		this.cGC = cGC;
//		
//		System.out.println("ClientPosReceiver gestartet");
//		
//		//UPD
//		try {
//			socket = new DatagramSocket(7777);
//		} catch (SocketException e) {
//			System.out.println("UDP geht nicht client");
//		}
//		
//		paket = new DatagramPacket(daten, daten.length);
//		
//		
//		this.start();
//	}
//	
//	public void run(){
//		String input = "";
//		String[] inputParts;
//		byte[] array = new byte[5];
//		int playerNo;
//		int aktion;
//		int x,y;
//		
//		//
//		
//		
//		while(!this.isInterrupted()){
//			
//			try{
//				//TCP
//				//input = sockIn.readLine();
//				//iS.read(array);
//				
//				//UDP
//				
//				socket.receive(paket);
//				daten = paket.getData();
//				input = new String(paket.getData(), 0 , paket.getLength());
//				//input = daten.toString();
//				///System.out.println("beim Client angekommen: " + input);
//			}catch(Exception e){
//				System.out.println(e.getMessage() );
//				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
//				this.interrupt();
//				
//			}
//			
//			inputParts = input.split(":");
//			
//			
//			if(inputParts[0].equals("Move")){//Move_P0_Event1
//				//System.out.println(input);
//				player.get(Integer.parseInt(inputParts[1])).setX(Integer.parseInt(inputParts[2]));
//				player.get(Integer.parseInt(inputParts[1])).setY(Integer.parseInt(inputParts[3]));
//				
//			
//			}
//			if(inputParts[0].equals("Sound")){//Move_P0_Event1
//				
//				if(inputParts[1].equals("item")){
//					SoundManager.getSoundManager().playSound("item.wav");
//				}
//				if(inputParts[1].equals("jump")){
//					SoundManager.getSoundManager().playSound("jump.wav");
//				}
//				
//				
//			
//			}
//			if(inputParts[0].equals("Act")){//Move_P0_Event1
//				//System.out.println(input);
//				if(inputParts[1].equals("dead")){ //wurde getötet
//					player.get(Integer.parseInt(inputParts[2])).getKilled();
//				}
//				if(inputParts[1].equals("kill")){//hat getötet
//					player.get(Integer.parseInt(inputParts[2])).addKill();
//				}
//				if(inputParts[1].equals("reIt")){//remove item
//					player.get(Integer.parseInt(inputParts[2])).looseItem();
//				}
//			}
//			
//			if(inputParts[0].equals("Item")){//add item on stage
//			
//				cGC.addItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));	
//			}
//			if(inputParts[0].equals("Itemr")){//remobe item from stage
//				
//				cGC.removeItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));
//				player.get(Integer.parseInt(inputParts[3])).setItem(new Item(1,1, Integer.parseInt(inputParts[4])));
//			}
//			if(inputParts[0].equals("Frame")){
//				
//				player.get(Integer.parseInt(inputParts[1])).setCurrentFrame(Integer.parseInt(inputParts[2]));				
//				
//			}	
//		}
//	}
//}

/////////////////////tcpIP

//package network;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.util.Vector;
//
//import manager.SoundManager;
//import map.Item;
//import menue.BaseFrame;
//import menue.MainMenue;
//
//public class ClientDataReceiver extends Thread{
//	
//	private BufferedReader sockIn;
//	private Vector<FullPlayer> player;
//	private int playerLifes;
//	private InputStream iS;
//	private ClientGameController cGC;
//	
//	
//	//UPD
//	byte[] daten =  new byte[64];
//	DatagramSocket socket;
//	DatagramPacket paket;
//	String temp;
//	
//	
//	public ClientDataReceiver(BufferedReader sockIn, Vector<FullPlayer> player, InputStream iS, ClientGameController cGC){
//		this.sockIn = sockIn;
//		this.player = player;
//		this.iS = iS;
//		this.cGC = cGC;
//		
//		System.out.println("ClientPosReceiver gestartet");
//		
//		//UPD
//		try {
//			socket = new DatagramSocket(7777);
//		} catch (SocketException e) {
//			System.out.println("UDP geht nicht client");
//		}
//		
//		paket = new DatagramPacket(daten, daten.length);
//		
//		
//		this.start();
//	}
//	
//	public void run(){
//		String input = "";
//		String[] inputParts;
//		byte[] array = new byte[5];
//		int playerNo;
//		int aktion;
//		int x,y;
//		
//		//
//		
//		
//		while(!this.isInterrupted()){
//			
//			try{
//				//TCP
//				//input = sockIn.readLine();
//				//iS.read(array);
//				
//				//UDP
//				
//				socket.receive(paket);
//				daten = paket.getData();
//				input = new String(paket.getData(), 0 , paket.getLength());
//				//input = daten.toString();
//				///System.out.println("beim Client angekommen: " + input);
//			}catch(Exception e){
//				System.out.println(e.getMessage() );
//				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
//				this.interrupt();
//				
//			}
//			
//			inputParts = input.split(":");
//			
//			
//			if(inputParts[0].equals("Move")){//Move_P0_Event1
//				//System.out.println(input);
//				player.get(Integer.parseInt(inputParts[1])).setX(Integer.parseInt(inputParts[2]));
//				player.get(Integer.parseInt(inputParts[1])).setY(Integer.parseInt(inputParts[3]));
//				
//			
//			}
//			if(inputParts[0].equals("Sound")){//Move_P0_Event1
//				
//				if(inputParts[1].equals("item")){
//					SoundManager.getSoundManager().playSound("item.wav");
//				}
//				if(inputParts[1].equals("jump")){
//					SoundManager.getSoundManager().playSound("jump.wav");
//				}
//				
//				
//			
//			}
//			if(inputParts[0].equals("Act")){//Move_P0_Event1
//				//System.out.println(input);
//				if(inputParts[1].equals("dead")){ //wurde getötet
//					player.get(Integer.parseInt(inputParts[2])).getKilled();
//				}
//				if(inputParts[1].equals("kill")){//hat getötet
//					player.get(Integer.parseInt(inputParts[2])).addKill();
//				}
//				if(inputParts[1].equals("reIt")){//remove item
//					player.get(Integer.parseInt(inputParts[2])).looseItem();
//				}
//			}
//			
//			if(inputParts[0].equals("Item")){//add item on stage
//			
//				cGC.addItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));	
//			}
//			if(inputParts[0].equals("Itemr")){//remobe item from stage
//				
//				cGC.removeItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));
//				player.get(Integer.parseInt(inputParts[3])).setItem(new Item(1,1, Integer.parseInt(inputParts[4])));
//			}
//			if(inputParts[0].equals("Frame")){
//				
//				player.get(Integer.parseInt(inputParts[1])).setCurrentFrame(Integer.parseInt(inputParts[2]));				
//				
//			}	
//		}
//	}
//}




