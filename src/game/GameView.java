package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import manager.PictureManager;
import map.Map;
import menue.GamePanel;

public class GameView extends GamePanel implements Finals {

	private LocalGameController game;
	private Map map;
	private Vector<Player> players;

	public GameView(LocalGameController g, Map m, Vector<Player> pl) {
		super("");

		game = g;
		map = m;
		players = pl;

		for (Player p : players) {
			addKeyListener(p);
		}

	}

	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.setFont(GAME_FONT);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(map.getBackground(), 0, 0, null);
		g.drawImage(PictureManager.getImage("hud.jpg"), 0, 550, null);
		// Level malen
		for (int i = 0; i < map.getSprites().size(); ++i) {
			map.getSprites().get(i).paint(g);
		}
		for (Player p : players) {
			p.paint(g);
		}

		// FPS malen
		g.drawString("FPS: " + Long.toString(game.getFPS()), 740, 20);

		requestFocus();
	}

}
