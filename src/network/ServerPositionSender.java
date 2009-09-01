package network;

import game.Player;
import game.Position;

import java.io.PrintWriter;
import java.util.Vector;

import map.Map;

public class ServerPositionSender extends Thread{
	private Vector<Player> p;
	private Vector<Position> oldPosition;
	private PrintWriter sockOut;
	private int lives;
	private Map map;
	//braucht alle Player!!
	public ServerPositionSender(Vector<Player> p, PrintWriter sockOut, Map map, int lives){
		this.p = p;
		this.sockOut = sockOut;
		this.map = map;
		this.lives = lives;
		oldPosition = new Vector();
		start();
		
	}
	public void run(){
		int oldPosition = 0, newPosition = 0;
		
		sockOut.println(map);
		sockOut.println(lives);
		
		while(true){
			
			for(int i = 0; i< p.size(); i++){
				//newPosition = p.get(i).getPosition();
				if(newPosition != oldPosition){
					sockOut.println("");
				}
			}
			
			
			
			
			
		}
		
		
		
	}

}
