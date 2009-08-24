package game;

import java.awt.event.KeyEvent;
import map.Sprite;
import map.Map;
import java.awt.event.*;
import java.awt.Rectangle;

public class Player extends Sprite implements KeyListener {

	private String name;
	private Map map;
	private boolean jump = false, fall = false, left = false, right = false,
			isMoving = false, collision = false;
	private double moveTime = 0.005, pastMoveTime = 0, jumpTime = 0.005,
			pastJumpTime = 0, gravity = 0.1, jumpSpeed = 0, jumpStart = -5;

	public Player(Map m) {
		super(20, 10, new String[] { "kirby1.gif" }, false);
		map = m;
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
						// isMoving=false;
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
						// isMoving=false;
					}
				}
			}
		}

		pastJumpTime += (delay / 1e9);
		if (pastJumpTime >= jumpTime) {
			int i = (int) (pastJumpTime / jumpTime);
			pastJumpTime = 0;
			collision = false;

			if (jumpSpeed < 0) {
				if (!collision) {
					y += i*(int)jumpSpeed;
					jumpSpeed += gravity;
					
					
				} else {
					jumpSpeed = 0;

				}

			}
			
			if (jumpSpeed >= 0) {
				Rectangle me = getNewBounds(0, i*(int)jumpSpeed);
				for (Sprite s : map.getSprites()) {
					if (me.intersects(s.getBounds())) {
						collision = true;
					}
				}
				if (!collision) {
					y += i*(int)jumpSpeed;
					jumpSpeed += gravity;
					
					
				} else {
					jumpSpeed = 0;

				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		switch (i) {
		case KeyEvent.VK_UP:
			jumpSpeed=jumpStart;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			right = false;
			isMoving = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			left = false;
			isMoving = true;
			break;
		default:
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();

		switch (i) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		default:
			break;
		}

	}

	public void keyTyped(KeyEvent e) {
	}

	private Rectangle getNewBounds(int nx, int ny) {
		return new Rectangle(x + nx, y + ny, width, height);
	}

}
