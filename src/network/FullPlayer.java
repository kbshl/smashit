package network;

import game.Finals;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observer;
import java.util.Vector;

import map.Item;
import map.Map;
import map.Sprite;

public class FullPlayer extends Sprite implements KeyListener, Finals{
	private int playerNumber;
	private String name;
	private final int leftKey, rightKey, jumpKey;
	private Map map;
	private Vector<FullPlayer> players;
	private boolean realPlayer;
	private ClientPositionSender cPS;
	
	private boolean jumpLock = false, left = false, right = false,
			isDead = false;
	private double moveTime = 0.007, pastMoveTime = 0, jumpTime = 0.005,
			pastJumpTime = 0, gravity = 0.1, jumpSpeed = 0, jumpStart = -4;
	private int jumpCount = 0, jumpSkill = 2, lifes = 10, kills = 0,
			lostLifes = 0;
	private Sprite collisionObject;
	
	private ServerPositionSender sPS;

	public FullPlayer(String n, int playerNumber, Map m, Vector<FullPlayer> p, boolean realPlayer, ClientPositionSender cPS, int lifes, ServerPositionSender sPS) {
		super(0, 0, new String[] { "kirby1.gif" }, false);
		this.realPlayer = realPlayer;
		this.playerNumber = playerNumber;
		this.lifes = lifes;
		this.sPS = sPS;
		name = n;
		map = m;
		players = p;
		this.cPS = cPS;
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
		setNewPosition();

	}

	public void act(long delay) {

		if (left || right) {
			pastMoveTime += (delay / 1e9);

			if (pastMoveTime >= moveTime) {
				int i = (int) (pastMoveTime / moveTime);
				pastMoveTime -= i * moveTime;

				for (int k = 0; k < i; ++k) {
					if (left) {
						if (!checkCollision(-1, 0)) {
							x -= 1;
						} else {

						}
					}

					if (right) {
						if (!checkCollision(1, 0)) {
							x += 1;
						} else {
						}
					}
				}
			}
		}

		pastJumpTime += (delay / 1e9);
		if (pastJumpTime >= jumpTime) {
			int i = (int) (pastJumpTime / jumpTime);
			pastJumpTime -= i * jumpTime;

			for (int k = 0; k < i; ++k) {

				if (jumpSpeed >= 0) {
					if (!checkCollision(0, (int) jumpSpeed)) {
						y += (int) jumpSpeed;
						jumpSpeed += gravity;
					} else {
						y = collisionObject.getY() - height;
						jumpCount = 0;
						jumpSpeed = 0;
						if (collisionObject instanceof FullPlayer) {
							((FullPlayer) collisionObject).getKilled();
							kills++;

						}
					}
				}

				if (jumpSpeed < 0) {

					if (!checkCollision(0, (int) jumpSpeed)) {
						y += (int) jumpSpeed;
						jumpSpeed += gravity;
					} else {
						y = collisionObject.getY()
								+ collisionObject.getHeight();
						jumpSpeed = 0;
					}
				}
			}
		}
		if(sPS != null){
			sPS.sendPosition(this.x, this.y, (this.playerNumber-1));
		}
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		int i = e.getKeyCode();
		if(realPlayer){
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
		else{
			if (i == jumpKey) {
				cPS.sendPosition("Move:" + 3 + ":" + 1);
			}
			if (i == leftKey) {
				cPS.sendPosition("Move:" + 1 + ":" + 1);
			}
			if (i == rightKey) {
				cPS.sendPosition("Move:" + 2 + ":" + 1);
			}
		}
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if(realPlayer){
			if (i == jumpKey) {
				jumpLock = false;
			}
			if (i == leftKey) {
				left = false;
			}
			if (i == rightKey) {
				right = false;
			}
		}
		else{//Move:3:0
			if (i == jumpKey) {
				cPS.sendPosition("Move:" + 3 + ":" + 0);
			}
			if (i == leftKey) {
				cPS.sendPosition("Move:" + 1 + ":" + 0);
			}
			if (i == rightKey) {
				cPS.sendPosition("Move:" + 2 + ":" + 0);
			}
		}

	}
	
	//wird von ServerPositionReceiver aufgerufen
	//jump = 3 | left = 1 | right = 2;
	public void keyPressed(int i) {
		//int i = e.getKeyCode();
		if (i == 3) {
			if (!jumpLock) {
				jumpLock = true;
				if (jumpCount < jumpSkill) {
					jumpSpeed = jumpStart;
					jumpCount++;
				}
			}
		}
		if (i == 1) {
			left = true;
			right = false;
		}
		if (i == 2) {
			right = true;
			left = false;
		}
	}
	//jump = 3 | left = 1 | right = 2;
	public void keyReleased(int i) {
		//int i = e.getKeyCode();
		if (i == 3) {
			jumpLock = false;
		}
		if (i == 1) {
			left = false;
		}
		if (i == 2) {
			right = false;
		}
	}

	private boolean checkCollision(int nx, int ny) {
		boolean collision = false;
		Rectangle me = new Rectangle(x + nx, y + ny, width, height);
		for (Sprite s : map.getSprites()) {
			if (me.intersects(s.getBounds())) {
				if (!s.isWalkable()) {
					collision = true;
					collisionObject = s;
				} else {
					if (s instanceof Item) {
						getItem((Item) s);
					}
				}
			}
		}
		//Könnte nicht funktionieren
		for (Sprite p : players) {
			if (p != this) {
				if (me.intersects(p.getBounds())) {
					collision = true;
					collisionObject = p;
				}
			}
		}
		return collision;
	}

	public void getKilled() {
		if (lifes > 0) {
			lifes--;
			lostLifes++;
			setNewPosition();
		} else {
			isDead = true;
			x = -100;
			y = 0;
		}
	}

	private void getItem(Item item) {
		switch (item.getAbility()) {
		case JUMP_HIGH:
			jumpSkill = 3;
			break;
		case JUMP_LOW:
			jumpSkill = 1;
			break;
		default:
			break;
		}
		item.collected();
		
	}

	private void setNewPosition() {
		boolean collision;
		y = 0;
		do {
			x = (int) (800 * Math.random());
			collision = checkCollision(0, 0);
		} while (collision);
	}

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
	}

}