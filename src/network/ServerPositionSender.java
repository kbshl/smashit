package network;


import game.Position;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import map.Map;
import map.Sprite;

public class ServerPositionSender{
	private Vector<FullPlayer> p;
	private Vector<Position> oldPosition;
	private PrintWriter sockOut;
	private Object[][] playerData;
	private int lives;
	private Map map;
	private String playerNames;
	//braucht alle Player!! und alle printWriter
	public ServerPositionSender(String playerNames, Object[][] playerData, Map map, int lives){
		this.p = p;
		this.playerData = playerData;
		//this.sockOut = sockOut;
		this.map = map;
		this.lives = lives;
		oldPosition = new Vector();
		this.playerNames = playerNames;
		
	
		
		if(init()){
			System.out.println("ServerPositionReceiver hat init und wird gestartet");
			
		}
		
		
	}
	
	private boolean init(){
		int j = 0;
		while(playerData[j][1] != null){
			//Move_P0_X_100_Y_200
			sockOut = (PrintWriter)playerData[j][1];
			sockOut.println(lives);//leben werden gesendet
			sockOut.println(playerNames);
			sockOut.println(map.getMapName());
			
			sockOut.println(j+1);
			
			
			
			++j;
		}
		
		
		return true;
	}
	
	private String getPlayerNames(){
		String s = "";
		for(int i = 0; i<p.size(); i++){
			System.out.println(p.get(i).getName() + " wird zum String geadded");
			s = s + p.get(i).getName() + ":" ;
		}
		s = s + p.lastElement().getName();
		return s;
	}
	
	public void sendPosition(int x, int y, int playernumber){
		int j = 0;
		while(playerData[j][1] != null){
			//Move_P0_X_100_Y_200
			sockOut = (PrintWriter)playerData[j][1];
			sockOut.println("Move:" + playernumber + ":" + x + ":" + y);
			//System.out.println("Move:" + playernumber + ":" + x + ":" + y);
			
			
			++j;
			
		}
	}
	public void sendSound(String sound, int playernumber){
		int j = 0;
		
		if(sound.equals("item")){ //wird an alle geschickt
			while(playerData[j][1] != null){
				//SFX_item
				sockOut = (PrintWriter)playerData[j][1];
				sockOut.println("Sound:" + sound);
				++j;	
			}
		}
		if(sound.equals("jump")){
			while(playerData[j][1] != null){
				//SFX_item
				sockOut = (PrintWriter)playerData[j][1];
				sockOut.println("Sound:" + sound);
				++j;	
			}
		}
	}
	
	
	public void sendAct(String act, int playerNumber){
		int j = 0;
		while(playerData[j][1] != null){
		sockOut = (PrintWriter)playerData[j][1];
		sockOut.println("Act:" + act +":" + (playerNumber-1) );
		
		++j;
		
		}	
		
	}
	public void sendItem (String item){
		int j = 0;
		while(playerData[j][1] != null){
		sockOut = (PrintWriter)playerData[j][1];
		sockOut.println(item);
		
		++j;
		}
	}
	 /*
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
				//if(oldPosition.get(i).getX() != p.get(i).getX() || oldPosition.get(i).getY() != p.get(i).getY()){
					//posAktuell = p.get(i).getPosition();
					//pos an alle senden
					j = 0;
					while(playerData[j][1] != null){
						//Move_P0_X_100_Y_200
						sockOut = (PrintWriter)playerData[j][1];
						sockOut.println("Move:" + i + ":" + p.get(i).getX() + ":" + p.get(i).getY());
						
						
						
						++j;
						
					}
					
					
					//oldPosition.set(i, posAktuell);
					//sockOut.println("");
				//}
			}	
		}
	}
	
	*/

}
