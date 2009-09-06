package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import map.Map;
import map.Sprite;
import network.ClientGameController;
import network.FullPlayer;
import network.NetworkGameController;

public class GameView extends JPanel implements Finals {

	private LocalGameController game;
	private NetworkGameController nGame;
	private ClientGameController cGame;
	private Map map;
	private Vector<Player> players;

	public GameView(LocalGameController g, Map m, Vector<Player> pl) {
		super(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(null);
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

		// Level malen
		for (Sprite s : map.getSprites()) {
			s.paint(g);
		}
		for (Player p : players) {
			p.paint(g);
		}

		// FPS malen		
		g.drawString("FPS: " + Long.toString(game.getFPS()), 740, 20);

		requestFocus();
	}

}
