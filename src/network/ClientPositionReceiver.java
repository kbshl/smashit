package network;

import java.io.BufferedReader;
import java.util.Vector;

public class ClientPositionReceiver extends Thread{
	
	private BufferedReader sockIn;
	private Vector<ClientPlayer> player;
	
	public void ClientPositionReveiver(BufferedReader sockIn ){
		this.sockIn = sockIn;
		
		this.start();
	}
	
	public void run(){
		String input = "";
		while(true){
			
			try{
				input = sockIn.readLine();
			}catch(Exception e){
				
			}
			//Move_P0_X_100_Y_200
			int pNumber = input.charAt(6);
			
			if(input.startsWith("Move")){//Move_P0_Event1
				player.get(Integer.parseInt(input.substring(6, 7))).setX(Integer.parseInt(input.substring(10, 13)));
			}
			
		}
	}

}
