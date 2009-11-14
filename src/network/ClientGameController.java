package network;

import map.Sprite;
import map.Map;
import map.Item;
import menue.BaseFrame;
import menue.GameStatsMenue;
import game.Finals;
import game.Player;


import java.util.Vector;
import java.awt.Rectangle;

public class ClientGameController implements Finals, Runnable {

	private NetworkGameView view;
	private Map map;
	// private Player player;
	private Vector<FullPlayer> players = new Vector<FullPlayer>();

	private long delta = 0, last = 0, fps = 0, startTime;
	private double itemTime = 10, pastItemTime = 0;
	private Thread t;
	private static boolean gameruns;
	private int playerNumber;

	public ClientGameController(Vector<FullPlayer> players, Map map, int playerNumber) {
		this.players = players;
		this.playerNumber = playerNumber;
		
		this.map = map;
		init();
	}

	private void init() {
//		map = new Map();
//		players.add(new Player("Rosa", PLAYER1, map, players));
//		players.add(new Player("Blau", PLAYER2, map, players));
//		players.add(new Player("Gelb", PLAYER3, map, players));
//		players.add(new Player("Grün", PLAYER4, map, players));
		view = new NetworkGameView(this, map, players, playerNumber);
		BaseFrame.getBaseFrame().setJPanel(view);

		last = System.nanoTime();
		startTime = last;
		gameruns = true;
		t = new Thread(this);
		t.start();

	}

	public void run() {

		while (gameruns) {
			//computeDelta();
			//updateWorld();
			view.repaint();
			playerAlive();
			try {
				t.sleep(GAME_PAUSE);
			} catch (InterruptedException e) {
			}

		}
		endGame();

	}
/*
	private void computeDelta() {
		delta = System.nanoTime() - last;
		pastItemTime += (delta / 1e9);
		last = System.nanoTime();
		fps = ((long) 1e9) / delta;
	}

	public void updateWorld() {
		
//		for (Sprite s : map.getSprites()) {
//			s.act(delta);
//		}
		
		for (FullPlayer p : players) {
			if (!p.isDead()) {
				p.actStupid(delta);
			}
		}
		
		if (pastItemTime >= itemTime) {
			pastItemTime -= itemTime;
			itemTime = 5 + (Math.random() * 5);
			addItem();
			System.out.println(itemTime);
		}
	}
	*/
	private void addItem() {
		boolean collision;
		Item item = new Item(0, 0);
		do {
			collision = false;
			item.setX((int) (800 * Math.random()));
			item.setY((int) (600 * Math.random()));
			Rectangle me = item.getBounds();
			for (Sprite s : map.getSprites()) {
				if (me.intersects(s.getBounds())) {
					collision = true;
				}
			}
			for (FullPlayer p : players) {
				if (me.intersects(p.getBounds())) {
					collision = true;
				}
			}
		} while (collision);
		map.getSprites().add(item);
	}
	
	public void addItem(int x, int y){
		Item item = new Item(x, y);
		map.getSprites().add(item);
	}

	public long getFPS() {
		return fps;
	}
	public void removeItem(int x, int y){
		
		System.out.println("Remove Item: x_" + x + " y_" + y);
		
		Vector<Sprite> temp = map.getSprites();
		int i = 0;
		Sprite s;
		System.out.println("suche nach element");
		while(i < temp.size()){
			s = temp.get(i);
			
			if(s instanceof Item){
				System.out.println("Instance of item");
				System.out.println(s.getX() +" vgl:"+  x +"_"+ s.getY()+" vlg: "+ y);
				if((s.getX() == x) && (s.getY() == y)){
					System.out.println("element gefunden");
					Item item = (Item)s;
					((Item)map.getSprites().get(i)).collected();
					map.getSprites().remove(i);
					i = temp.size();
					System.out.println("Element entfernt");
				}
			}
			i++;
		}
		
	}
	private void endGame() {
		BaseFrame.getBaseFrame().setJPanel(new NetGameStatsMenue(players));
	}
	private void playerAlive(){
		int alive = 0;
		for (FullPlayer p : players) {
			if (!p.isDead()) {
				alive++;
			}
		}
		if (alive <= 1) {
			gameruns = false;
		}
	}

}
