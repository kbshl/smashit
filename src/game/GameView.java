package game;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.*;
import map.Map;
import map.Sprite;
import java.util.Vector;



public class GameView extends JPanel implements Finals{
	
	private LocalGameController game;
	private Map map;
	private Vector<Player> player;
	private Font gameFont = new Font("Monospaced", Font.BOLD, 12);
	
	public GameView(LocalGameController g, Map m, Vector<Player> pl){
		super(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(null);
		game = g;
		map = m;
		player = pl;
		
		for (Player p : player) {
			addKeyListener(p);
		}
	}
	
	protected void paintComponent( Graphics g ) { 
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(map.getBackground(), 0, 0, null);

		// Level malen
		for (Sprite s : map.getSprites()) {
			s.paint(g);
		}
		for (Player p : player) {
			p.paint(g);
		}
		

		// FPS malen
		g.setColor(Color.black);
		g.setFont(gameFont);
		g.drawString("FPS: " + Long.toString(game.getFPS()), 20, WINDOW_HEIGHT - 50);
		requestFocus();
	}

}
