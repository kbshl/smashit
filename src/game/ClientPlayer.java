package game;

import java.awt.event.KeyEvent;
import map.Sprite;
import map.Map;
import map.Item;
import java.awt.event.*;
import java.awt.Rectangle;
import java.util.Vector;

public class ClientPlayer extends Sprite implements KeyListener, Finals {
	private int playerNumber;
	private String name;
	private final int leftKey, rightKey, jumpKey;
	private Map map;
	private Vector<ClientPlayer> players;
	private ClientPositionSender cPS;
	private boolean jumpLock = false, left = false, right = false,
			isDead = false;
	private double moveTime = 0.007, pastMoveTime = 0, jumpTime = 0.005,
			pastJumpTime = 0, gravity = 0.1, jumpSpeed = 0, jumpStart = -4;
	private int jumpCount = 0, jumpSkill = 2, lifes = 10, kills = 0,
			lostLifes = 0;
	private Sprite collisionObject;

	public ClientPlayer(String n, int playerNumber, Map m, Vector<ClientPlayer> p, ClientPositionSender cPS) {
		super(0, 0, new String[] { "kirby1.gif" }, false);
		this.cPS = cPS;
		this.playerNumber = playerNumber;
		name = n;
		map = m;
		players = p;

		switch (playerNumber) {
		case PLAYER1:
			leftKey = KeyEvent.VK_A;
			rightKey = KeyEvent.VK_D;
			jumpKey = KeyEvent.VK_W;
			images[0] = "kirby1.gif";
			break;
		case PLAYER2:
			leftKey = KeyEvent.VK_J;
			rightKey = KeyEvent.VK_L;
			jumpKey = KeyEvent.VK_I;
			images[0] = "kirby2.gif";
			break;
		case PLAYER3:
			leftKey = KeyEvent.VK_LEFT;
			rightKey = KeyEvent.VK_RIGHT;
			jumpKey = KeyEvent.VK_UP;
			images[0] = "kirby3.gif";
			break;
		case PLAYER4:
			leftKey = KeyEvent.VK_NUMPAD1;
			rightKey = KeyEvent.VK_NUMPAD3;
			jumpKey = KeyEvent.VK_NUMPAD5;
			images[0] = "kirby4.gif";
			break;
		default:
			leftKey = KeyEvent.VK_LEFT;
			rightKey = KeyEvent.VK_RIGHT;
			jumpKey = KeyEvent.VK_UP;
			images[0] = "kirby1.gif";
			break;
		}
		

	}

	public void act(long delay) {

	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == jumpKey) {
			if (!jumpLock) {
				jumpLock = true;
				if (jumpCount < jumpSkill) {
					jumpSpeed = jumpStart;
					jumpCount++;
				}
			}
		}
		if (i == leftKey) {
			left = true;
			right = false;
		}
		if (i == rightKey) {
			right = true;
			left = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == jumpKey) {
			//jumpLock = false;
			cPS.sendPosition("Move_P" + playerNumber + "_Event" + 3);
		}
		if (i == leftKey) {
			//left = false;
			cPS.sendPosition("Move_P" + playerNumber + "_Event" + 1);
		}
		if (i == rightKey) {
			//right = false;
			cPS.sendPosition("Move_P" + playerNumber + "_Event" + 2);
		}
	}

	

	

	//Kann auch weg?

	public int getLifes() {
		return lifes;
	}

	public int getKills() {
		return kills;
	}

	public String getName() {
		return name;
	}

	public boolean isDead() {
		return isDead;
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
