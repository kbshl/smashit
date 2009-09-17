package network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Vector;

public class ClientPositionReceiver extends Thread{
	
	private BufferedReader sockIn;
	private Vector<FullPlayer> player;
	private int playerLifes;
	private InputStream iS;
	
	public ClientPositionReceiver(BufferedReader sockIn, Vector<FullPlayer> player, InputStream iS){
		this.sockIn = sockIn;
		this.player = player;
		this.iS = iS;
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
			
			
			
		}
	}

}
