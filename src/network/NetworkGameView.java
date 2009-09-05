package network;

import game.Finals;
import game.LocalGameController;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import map.Map;
import map.Sprite;

public class NetworkGameView extends JPanel implements Finals {

	private LocalGameController game;
	private NetworkGameController nGame;
	private ClientGameController cGame;
	private Map map;
	private Vector<FullPlayer> players;
	private Font gameFont = new Font("Monospaced", Font.BOLD, 12);

	
	public NetworkGameView(NetworkGameController g, Map m, Vector<FullPlayer> pl) {
		super(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(null);
		nGame = g;
		map = m;
		players = pl;
		//nur für den ersten player
		addKeyListener(players.get(0));
		/*
		for (FullPlayer p : players) {
			//players.add((Player)p)
			addKeyListener(p);
		}*/
	}
	public NetworkGameView(ClientGameController g, Map m, Vector<FullPlayer> pl) {
		super(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(null);
		cGame = g;
		map = m;
		players = pl;
		//nur einer muss steuern können
		addKeyListener(players.get(1));
		/*
		for (FullPlayer p : players) {
			addKeyListener(p);
		}*/
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(map.getBackground(), 0, 0, null);

		// Level malen
		for (Sprite s : map.getSprites()) {
			s.paint(g);
		}
		for (FullPlayer p : players) {
			p.paint(g);
		}

		// FPS malen
		g.setColor(Color.black);
		g.setFont(gameFont);
		//g.drawString("FPS: " + Long.toString(game.getFPS()), 740, 20);
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).isDead()) {
				g.drawString(players.get(i).getName() + ": DEAD",
						10 + (i * 200), 580);
			} else {
				g.drawString(players.get(i).getName() + " / L:"
						+ players.get(i).getLifes() + " / K:"
						+ players.get(i).getKills(), 10 + (i * 200), 580);
			}

		}
		requestFocus();
	}

}
