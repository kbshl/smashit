package game;

import map.Sprite;
import map.Map;
import map.Item;
import menue.BaseFrame;
import menue.MainMenue;
import menue.GameStatsMenue;

import java.util.Vector;
import java.awt.Rectangle;

public class LocalGameController implements Finals, Runnable {

	private GameView view;
	private Map map;
	private Vector<Player> players = new Vector<Player>();

	private long delta = 0, last = 0, fps = 0;
	private double itemTime = 10, pastItemTime = 0;
	private Thread t;
	private static boolean gameruns;

	public LocalGameController(String mapName, String name1, String name2,
			String name3, String name4, int leben) {
		map = new Map(mapName);
		if (!name1.equals("")) {
			players.add(new Player(name1, PLAYER1, map, players, leben));
		}
		if (!name2.equals("")) {
			players.add(new Player(name2, PLAYER2, map, players, leben));
		}
		if (!name3.equals("")) {
			players.add(new Player(name3, PLAYER3, map, players, leben));
		}
		if (!name4.equals("")) {
			players.add(new Player(name4, PLAYER4, map, players, leben));
		}

		view = new GameView(this, map, players);
		BaseFrame.getBaseFrame().setJPanel(view);

		last = System.nanoTime();
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
		for (Player p : players) {
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
			itemTime = 5 + (Math.random() * 5);
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

	private void addItem() {
		boolean collision;
		Item item = new Item(0, 0);
		do {
			collision = false;
			item.setX((int) (WINDOW_WIDTH * Math.random()));
			item.setY((int) (GAME_HEIGHT * Math.random()));
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

	private void endGame() {
		BaseFrame.getBaseFrame().setJPanel(new GameStatsMenue(players));
	}

	public long getFPS() {
		return fps;
	}

}
