package game;

import java.io.BufferedReader;

public class ClientPositionReceiver extends Thread{
	
	private BufferedReader sockIn;
	
	public void ClientPositionReveiver(BufferedReader sockIn ){
		this.sockIn = sockIn;
		
		this.start();
	}
	
	public void run(){
		while(true){
			
			try{
				sockIn.readLine();
			}catch(Exception e){
				
			}
			
		}
	}

}
