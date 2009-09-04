package network;

import game.Player;
import game.Position;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Vector;

import map.Map;
import map.Sprite;

public class ServerPositionSender extends Thread{
	private Vector<FullPlayer> p;
	private Vector<Position> oldPosition;
	private PrintWriter sockOut;
	private Object[][] playerData;
	private int lives;
	private Map map;
	//braucht alle Player!! und alle printWriter
	public ServerPositionSender(Vector<FullPlayer> p, Object[][] playerData, Map map, int lives){
		this.p = p;
		this.playerData = playerData;
		//this.sockOut = sockOut;
		this.map = map;
		this.lives = lives;
		oldPosition = new Vector();
		start();
		
	}
	public void run(){
		//int oldPosition = 0, newPosition = 0;
		int j = 0;
		Position posAktuell;
		for (Sprite s : p) {
			oldPosition.add(s.getPosition());
		}
		
		//sockOut.println(map);
		//sockOut.println(lives);
		
		while(true){
			
			for(int i = 0; i< p.size(); i++){
				//newPosition = p.get(i).getPosition();
				if(oldPosition.get(i).getX() != p.get(i).getX() || oldPosition.get(i).getY() != p.get(i).getY()){
					posAktuell = p.get(i).getPosition();
					//pos an alle senden
					j = 0;
					while(playerData[j][1] != null){
						//Move_P0_X_100_Y_200
						sockOut = (PrintWriter)playerData[j][1];
						sockOut.println("Move:" + i + ":" + p.get(i).getX() + ":" + p.get(i).getY());
						
						++j;
					}
					
					
					oldPosition.set(i, posAktuell);
					//sockOut.println("");
				}
			}
			
			
			
			
			
		}
		
		
		
	}

}
