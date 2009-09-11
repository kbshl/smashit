package network;

import java.io.BufferedReader;
import java.util.Vector;

public class ClientPositionReceiver extends Thread{
	
	private BufferedReader sockIn;
	private Vector<FullPlayer> player;
	private int playerLifes;
	
	public ClientPositionReceiver(BufferedReader sockIn, Vector<FullPlayer> player){
		this.sockIn = sockIn;
		this.player = player;
		
		this.start();
		System.out.println("ClientPosReceiver gestartet");
	}
	
	public void run(){
		String input = "";
		String[] inputParts;
		
		//
		
		
		while(true){
			
			try{
				input = sockIn.readLine();
				//System.out.println("Empafangen_" + input);
			}catch(Exception e){
				System.out.println(e.getMessage() + "Fehler ist aufgetreten");
			}
			
			inputParts = input.split(":");
			//Move:0:34:203
			
			//int pNumber = Integer.parseInt(input.substring(6, 7));
			
			if(inputParts[0].equals("Move")){//Move_P0_Event1
				System.out.println(player.size() + " empfangen: " + Integer.parseInt(inputParts[1]));
				player.get(Integer.parseInt(inputParts[1])).setX(Integer.parseInt(inputParts[2]));
				player.get(Integer.parseInt(inputParts[1])).setY(Integer.parseInt(inputParts[3]));
				
				//System.out.println("Erhalten PLayer " + inputParts[1] + "PosX" + inputParts[2] + "PosY " + inputParts[3]);
			}
			
			
			/*
			else{
				if(inputParts[0].equals("Init")){//Init:0:5 ->0=Lifes, 5=5Lifes
					if(inputParts[1].equals("0")){
						playerLifes = Integer.parseInt(inputParts[2]);
					}
					if(inputParts[1].equals("0")){
						playerLifes = Integer.parseInt(inputParts[2]);
					}
				}
			}*/

			
		}
	}

}
