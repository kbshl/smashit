package game;

import java.net.Socket;

public class ServerPositionReceiver extends Thread{
	
	public ServerPositionReceiver(Player p, Socket sockIn){
		start();
	}

}
