package game;

import java.awt.event.KeyEvent;
import map.Sprite;
import map.Map;
import java.awt.event.*;
import java.awt.Rectangle;

public class Player extends Sprite implements KeyListener, Finals {

	private String name;
	private Map map;
	private boolean jumpLock = false, left = false, right = false,
			collision = false;
	private double moveTime = 0.005, pastMoveTime = 0, jumpTime = 0.005,
			pastJumpTime = 0, gravity = 0.1, jumpSpeed = 0, jumpStart = -5;
	private int jumpCount = 0, jumpSkill = 2;
	private final int leftKey, rightKey, jumpKey;

	public Player(Map m, int keys) {
		super(50, 0, new String[] { "kirby1.gif" }, false);
		map = m;

		switch (keys) {
		case KEYS1:
			leftKey = KeyEvent.VK_A;
			rightKey = KeyEvent.VK_D;
			jumpKey = KeyEvent.VK_W;
			break;
		case KEYS2:
			leftKey = KeyEvent.VK_J;
			rightKey = KeyEvent.VK_L;
			jumpKey = KeyEvent.VK_I;
			break;
		case KEYS3:
			leftKey = KeyEvent.VK_LEFT;
			rightKey = KeyEvent.VK_RIGHT;
			jumpKey = KeyEvent.VK_UP;
			break;
		case KEYS4:
			leftKey = KeyEvent.VK_NUMPAD1;
			rightKey = KeyEvent.VK_NUMPAD3;
			jumpKey = KeyEvent.VK_NUMPAD5;
			break;
		default:
			leftKey = KeyEvent.VK_LEFT;
			rightKey = KeyEvent.VK_RIGHT;
			jumpKey = KeyEvent.VK_UP;
			break;
		}

	}

	public void act(long delay) {

		if (left || right) {
			pastMoveTime += (delay / 1e9);
			collision = false;

			if (pastMoveTime >= moveTime) {
				int i = (int) (pastMoveTime / moveTime);
				pastMoveTime = 0;

				if (left) {
					Rectangle me = getNewBounds(-i, 0);
					for (Sprite s : map.getSprites()) {
						if (me.intersects(s.getBounds())) {
							collision = true;
						}
					}
					if (!collision) {
						x -= i;
					} else {

					}
				}

				if (right) {
					Rectangle me = getNewBounds(i, 0);
					for (Sprite s : map.getSprites()) {
						if (me.intersects(s.getBounds())) {
							collision = true;
						}
					}
					if (!collision) {
						x += i;
					} else {

					}
				}
			}
		}

		pastJumpTime += (delay / 1e9);
		if (pastJumpTime >= jumpTime) {
			int i = (int) (pastJumpTime / jumpTime);
			pastJumpTime = 0;
			collision = false;

			Rectangle me = getNewBounds(0, i * (int) jumpSpeed);
			int newy = 0;

			if (jumpSpeed < 0) {
				for (Sprite s : map.getSprites()) {
					if (me.intersects(s.getBounds())) {
						collision = true;
						newy = s.getY() + s.getHeight();
					}
				}
			}

			if (jumpSpeed >= 0) {
				for (Sprite s : map.getSprites()) {
					if (me.intersects(s.getBounds())) {
						collision = true;
						jumpCount = 0;
						newy = s.getY() - width;
					}
				}
			}

			if (!collision) {
				y += i * (int) jumpSpeed;
				jumpSpeed += gravity;

			} else {
				jumpSpeed = 0;
				y = newy;
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

	public void keyTyped(KeyEvent e) {
	}

	private Rectangle getNewBounds(int nx, int ny) {
		return new Rectangle(x + nx, y + ny, width, height);
	}

}
