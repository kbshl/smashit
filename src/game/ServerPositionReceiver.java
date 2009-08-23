package game;

import java.io.BufferedReader;

public class ServerPositionReceiver extends Thread{
	
	public ServerPositionReceiver(Player p, BufferedReader sockIn){
		start();
	}

}
