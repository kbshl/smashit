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

public class ClientPositionReceiver extends Thread{
	
	private BufferedReader sockIn;
	private Vector<FullPlayer> player;
	private int playerLifes;
	private InputStream iS;
	private ClientGameController cGC;
	
	
	//UPD
	byte[] daten =  new byte[64];
	DatagramSocket socket;
	DatagramPacket paket;
	String temp;
	
	
	public ClientPositionReceiver(BufferedReader sockIn, Vector<FullPlayer> player, InputStream iS, ClientGameController cGC){
		this.sockIn = sockIn;
		this.player = player;
		this.iS = iS;
		this.cGC = cGC;
		
		System.out.println("ClientPosReceiver gestartet");
		
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
		byte[] array = new byte[5];
		int playerNo;
		int aktion;
		int x,y;
		
		//
		
		
		while(!this.isInterrupted()){
			
			try{
				//TCP
				//input = sockIn.readLine();
				//iS.read(array);
				
				//UDP
				
				socket.receive(paket);
				daten = paket.getData();
				input = new String(paket.getData(), 0 , paket.getLength());
				//input = daten.toString();
				///System.out.println("beim Client angekommen: " + input);
			}catch(Exception e){
				System.out.println(e.getMessage() );
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
				this.interrupt();
				
			}
			/*
			aktion = array[0] & 7;
			playerNo = array[0] >> 3;
			x = array[1];
			x = x << 8;
			x = x | (int)array[2]; 
			//x = ((int)(7168 & (array[1] << 8))) | (1023 & array[2]);
			//y = ((int)(7168 & (array[3] << 8))) | (1023 & array[4]);
			y = array[3];
			y = y << 8;
			y = y | (int)array[4];
			
			System.out.println("Receive bytes :" + Byte.toString(array[0]) + " " + Byte.toString(array[1]) + " "+ Byte.toString(array[2]) + " " + Byte.toString(array[3]) + " " + Byte.toString(array[4]));
			System.out.println("RecUmgerechnet:" + playerNo + "; " + aktion +"; " + x + "; " + y );
			player.get(playerNo).setX(x);
			player.get(playerNo).setY(y);
			*/
			
			//player.get(Integer.parseInt(inputParts[1])).setY(Integer.parseInt(inputParts[3]));
			inputParts = input.split(":");
			
			
			if(inputParts[0].equals("Move")){//Move_P0_Event1
				//System.out.println(input);
				player.get(Integer.parseInt(inputParts[1])).setX(Integer.parseInt(inputParts[2]));
				player.get(Integer.parseInt(inputParts[1])).setY(Integer.parseInt(inputParts[3]));
				
			
			}
			if(inputParts[0].equals("Sound")){//Move_P0_Event1
				
				if(inputParts[1].equals("item")){
					SoundManager.getSoundManager().playSound("item.wav");
				}
				if(inputParts[1].equals("jump")){
					SoundManager.getSoundManager().playSound("jump.wav");
				}
				
				
			
			}
			if(inputParts[0].equals("Act")){//Move_P0_Event1
				//System.out.println(input);
				if(inputParts[1].equals("dead")){ //wurde getötet
					player.get(Integer.parseInt(inputParts[2])).getKilled();
				}
				if(inputParts[1].equals("kill")){//hat getötet
					player.get(Integer.parseInt(inputParts[2])).addKill();
				}
				if(inputParts[1].equals("reIt")){//remove item
					player.get(Integer.parseInt(inputParts[2])).looseItem();
				}
				
				/*
				if(inputParts[1].equals("addItem")){//hat getötet
					cGC.addItem(Integer.parseInt(inputParts[2]), Integer.parseInt(inputParts[3]));
				}*/	
			}
			
			if(inputParts[0].equals("Item")){//add item on stage
			
				cGC.addItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));	
			}
			if(inputParts[0].equals("Itemr")){//remobe item from stage
				
				cGC.removeItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));
				player.get(Integer.parseInt(inputParts[3])).setItem(new Item(1,1, Integer.parseInt(inputParts[4])));
			}
			if(inputParts[0].equals("Frame")){
				
				player.get(Integer.parseInt(inputParts[1])).setCurrentFrame(Integer.parseInt(inputParts[2]));				
				
			}
			
			
			
		}
	}

}
