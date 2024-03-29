package network;

import game.Finals;
import game.LocalGameController;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import manager.PictureManager;
import map.Map;
import menue.GamePanel;

public class NetworkGameView extends GamePanel implements Finals {

	private LocalGameController game;
	private NetworkGameController nGame;
	private ClientGameController cGame;
	private Map map;
	private Vector<FullPlayer> players;
	private Font gameFont = new Font("Monospaced", Font.BOLD, 12);

	
	public NetworkGameView(NetworkGameController g, Map m, Vector<FullPlayer> pl) {
		super("");
		nGame = g;
		map = m;
		players = pl;
		//nur f�r den ersten player
		addKeyListener(players.get(0));
		/*
		for (FullPlayer p : players) {
			//players.add((Player)p)
			addKeyListener(p);
		}*/
	}
	public NetworkGameView(ClientGameController g, Map m, Vector<FullPlayer> pl, int playerNumber) {
		super("");
		cGame = g;
		map = m;
		players = pl;
		//nur einer muss steuern k�nnen
		addKeyListener(players.get(playerNumber));
		/*
		for (FullPlayer p : players) {
			addKeyListener(p);
		}*/
	}
	
	
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.setFont(GAME_FONT);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(map.getBackground(), 0, 0, null);
		g.drawImage(PictureManager.getPictureManager().getImage("hud.jpg"), 0, 550, null);
		// Level malen
		for (int i = 0; i < map.getSprites().size(); ++i) {
			map.getSprites().get(i).paint(g);
		}
		for (FullPlayer p : players) {
			p.paint(g);
		}

		// FPS malen
		//g.drawString("FPS: " + Long.toString(game.getFPS()), 720, 20);

		requestFocus();
	}
	
	/* alt
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
	}*/

}
