package game;

import java.awt.event.KeyEvent;

import map.Sprite;
import map.Map;

public class Player extends Sprite {
	
	private String name;
	private Map map;
	private boolean jump = false, fall = false, left = false, right = false,
			isMoving = false;
	private double moveSpeed = 0.005, pastMoveTime = 0;

	public Player(Map m) {
		super(200, 200, new String[] { "kirby1.gif" }, false);
		map = m;
	}

	public void act(long delay) {
		
		if (isMoving) {
			pastMoveTime += (delay / 1e9);

			if (left) {
				if (pastMoveTime >= moveSpeed) {
					int i = (int) (pastMoveTime / moveSpeed);
					pastMoveTime = 0;
					x -= i;
					
				}
			}

			if (right) {
				if (pastMoveTime >= moveSpeed) {
					int i = (int) (pastMoveTime / moveSpeed);
					pastMoveTime = 0;
					x += i;
				}
			}
		}

		if (jump) {
			--y;
		}

		if (fall) {
			++y;
		}

	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		switch (i) {
		case KeyEvent.VK_UP:
			jump = true;
			fall = false;
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
		case KeyEvent.VK_UP:
			jump = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			isMoving = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			isMoving = false;
			break;
		default:
			break;
		}

	}

}
