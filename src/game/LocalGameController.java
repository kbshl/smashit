package game;



import map.Sprite;
import map.Map;
import menue.BaseFrame;
import java.util.Vector;




public class LocalGameController implements Finals, Runnable {

	private GameView view;
	private Map map;
	//private Player player;
	private Vector<Player> player = new Vector<Player>();

	
	private long delta = 0, last = 0, fps = 0;
	private Thread t;
	private static boolean gameruns;
	
	public LocalGameController(){
		init();
	}
	
	private void init() {
		last = System.nanoTime();
		map = new Map();
		player.add(new Player(map, PLAYER1));
		player.add(new Player(map, PLAYER2));
		player.add(new Player(map, PLAYER3));
		player.add(new Player(map, PLAYER4));
		gameruns = true;
		view = new GameView(this, map, player);
		BaseFrame.getBaseFrame().setJPanel(view);
		
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
		last = System.nanoTime();
		fps = ((long) 1e9) / delta;
	}
	
	public void updateWorld() {
		for (Sprite s : map.getSprites()) {
			s.act(delta);
		}
		for (Player p : player) {
			p.act(delta);
		}
	}
	
	public long getFPS(){
		return fps;
	}
	
}
