package network;

import game.Finals;
import game.Player;

import java.awt.Rectangle;
import java.util.Vector;

import map.Item;
import map.Map;
import map.Sprite;
import menue.BaseFrame;
import menue.GameStatsMenue;

public class NetworkGameController implements Finals, Runnable {

	private NetworkGameView view;
	private Map map;
	// private Player player;
	private Vector<FullPlayer> players = new Vector<FullPlayer>();

	private long delta = 0, last = 0, fps = 0, startTime;
	private double itemTime = 10, pastItemTime = 0;
	private Thread t;
	private static boolean gameruns;
	private ServerDataSender sPS;
	
	public NetworkGameController(Vector<FullPlayer> players, Map map, ServerDataSender sPS) {
		
		this.sPS = sPS;
		this.players = players;
		
		this.map = map;
		System.out.println("NetworkgameController initialisiert");
		init();
	}

	private void init() {
//		map = new Map();
//		players.add(new Player("Rosa", PLAYER1, map, players));
//		players.add(new Player("Blau", PLAYER2, map, players));
//		players.add(new Player("Gelb", PLAYER3, map, players));
//		players.add(new Player("Grün", PLAYER4, map, players));
		view = new NetworkGameView(this, map, players);
		BaseFrame.getBaseFrame().setJPanel(view);

		last = System.nanoTime();
		startTime = last;
		gameruns = true;
		t = new Thread(this);
		t.start();

	}

	public void run() {

		while (gameruns) {
			computeDelta();
			updateWorld();
			view.repaint();

			try {
				t.sleep(GAME_PAUSE);
			} catch (InterruptedException e) {
			}

		}
		endGame();

	}

	private void computeDelta() {
		delta = System.nanoTime() - last;
		pastItemTime += (delta / 1e9);
		last = System.nanoTime();
		fps = ((long) 1e9) / delta;
	}
	
	public void updateWorld() {
		int alive = 0;
		for (Sprite s : map.getSprites()) {
			s.act(delta);
		}
		for (FullPlayer p : players) {
			if (!p.isDead()) {
				p.act(delta);
				alive++;
			}
		}
		if (alive <= 1) {
			gameruns = false;
		}
		if (pastItemTime >= itemTime) {
			pastItemTime -= itemTime;
			itemTime = 15 + (Math.random() * 15);
			removeItems();
			addItem();
		}
	}
	private void removeItems() {
		int i = 0;
		while (i < map.getSprites().size()) {
			if (map.getSprites().get(i) instanceof Item) {
				if (((Item) map.getSprites().get(i)).isRemoveable()) {
					map.getSprites().remove(map.getSprites().get(i));
				} else {
					++i;
				}
			} else {
				++i;
			}
		}
	}
	/*
	public void updateWorld() {
		
		for (Sprite s : map.getSprites()) {
			s.act(delta);
		}
		
		for (FullPlayer p : players) {
			if (!p.isDead()) {
				p.act(delta);
			}
		}
		
		if (pastItemTime >= itemTime) {
			pastItemTime -= itemTime;
			itemTime = 5 + (Math.random() * 5);
			addItem();
			//System.out.println(itemTime);
		}
	}*/

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
		System.out.println("Geschickt:Item:" + item.getX()+":"+item.getY());
		sPS.sendItem("Item:", item.getX(), item.getY(), 0, 0);
	}

	public long getFPS() {
		return fps;
	}
	
	private void endGame() {
		BaseFrame.getBaseFrame().setJPanel(new NetGameStatsMenue(players));
	}

}
