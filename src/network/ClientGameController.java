package network;

import map.Sprite;
import map.Map;
import map.Item;
import menue.BaseFrame;
import game.Finals;
import game.GameView;
import game.Player;

import java.util.Vector;
import java.awt.Rectangle;

public class ClientGameController implements Finals, Runnable {

	private GameView view;
	private Map map;
	// private Player player;
	private Vector<Player> players = new Vector<Player>();

	private long delta = 0, last = 0, fps = 0, startTime;
	private double itemTime = 10, pastItemTime = 0;
	private Thread t;
	private static boolean gameruns;

	public ClientGameController() {
		init();
	}

	private void init() {
		map = new Map();
		players.add(new Player("Rosa", PLAYER1, map, players));
		players.add(new Player("Blau", PLAYER2, map, players));
		players.add(new Player("Gelb", PLAYER3, map, players));
		players.add(new Player("Grün", PLAYER4, map, players));
		view = new GameView(this, map, players);
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

	}

	private void computeDelta() {
		delta = System.nanoTime() - last;
		pastItemTime += (delta / 1e9);
		last = System.nanoTime();
		fps = ((long) 1e9) / delta;
	}

	public void updateWorld() {
		
		for (Sprite s : map.getSprites()) {
			s.act(delta);
		}
		
		for (Player p : players) {
			if (!p.isDead()) {
				p.act(delta);
			}
		}
		
		if (pastItemTime >= itemTime) {
			pastItemTime -= itemTime;
			itemTime = 5 + (Math.random() * 5);
			addItem();
			System.out.println(itemTime);
		}
	}

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
			for (Player p : players) {
				if (me.intersects(p.getBounds())) {
					collision = true;
				}
			}
		} while (collision);
		map.getSprites().add(item);
	}

	public long getFPS() {
		return fps;
	}

}
