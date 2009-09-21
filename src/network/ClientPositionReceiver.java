package network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Vector;

import manager.SoundManager;

public class ClientPositionReceiver extends Thread{
	
	private BufferedReader sockIn;
	private Vector<FullPlayer> player;
	private int playerLifes;
	private InputStream iS;
	private ClientGameController cGC;
	
	public ClientPositionReceiver(BufferedReader sockIn, Vector<FullPlayer> player, InputStream iS, ClientGameController cGC){
		this.sockIn = sockIn;
		this.player = player;
		this.iS = iS;
		this.cGC = cGC;
		this.start();
		System.out.println("ClientPosReceiver gestartet");
	}
	
	public void run(){
		String input = "";
		String[] inputParts;
		byte[] array = new byte[5];
		int playerNo;
		int aktion;
		int x,y;
		
		//
		
		
		while(true){
			
			try{
				input = sockIn.readLine();
				//iS.read(array);
				
			}catch(Exception e){
				System.out.println(e.getMessage() + "Fehler ist aufgetreten");
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
				/*
				if(inputParts[1].equals("addItem")){//hat getötet
					cGC.addItem(Integer.parseInt(inputParts[2]), Integer.parseInt(inputParts[3]));
				}*/	
			}
			
			if(inputParts[0].equals("Item")){
				System.out.println("am client empfangen:" + input);
				cGC.addItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));	
			}
			if(inputParts[0].equals("Itemr")){
				System.out.println("am client empfangen:" + input);
				cGC.removeItem(Integer.parseInt(inputParts[1]), Integer.parseInt(inputParts[2]));	
			}
			
			
			
		}
	}

}
