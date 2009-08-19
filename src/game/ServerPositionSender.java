package game;

import java.net.Socket;

import map.Map;

public class ServerPositionSender extends Thread{
	
	public ServerPositionSender(Player p, Socket sockOut, Map map, int lives){
		
		start();
		
	}

}
