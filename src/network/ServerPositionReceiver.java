package network;

import game.Player;

import java.io.BufferedReader;

public class ServerPositionReceiver extends Thread{
	
	public ServerPositionReceiver(HostPlayer p, BufferedReader sockIn){
		start();
	}

}
