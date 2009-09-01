package network;

import game.Finals;
import game.Player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import map.Item;
import map.Map;
import map.Sprite;

public class FullPlayer extends Sprite implements KeyListener, Finals {

	private String name;
	private final int leftKey, rightKey, jumpKey;
	private Map map;
	private Vector<Sprite> players;

	private boolean jumpLock = false, left = false, right = false,
			isDead = false;
	private double moveTime = 0.007, pastMoveTime = 0, jumpTime = 0.005,
			pastJumpTime = 0, gravity = 0.1, jumpSpeed = 0, jumpStart = -4;
	private int jumpCount = 0, jumpSkill = 2, lifes = 10, kills = 0,
			lostLifes = 0;
	private Sprite collisionObject;

	public FullPlayer(String n, int playerNumber, Map m, Vector<Sprite> p) {
		super(0, 0, new String[] { "kirby1.gif" }, false);
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
						if (collisionObject instanceof Player) {
							((Player) collisionObject).getKilled();
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
			jumpLock = false;
		}
		if (i == leftKey) {
			left = false;
		}
		if (i == rightKey) {
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
		//K�nnte nicht funktionieren
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