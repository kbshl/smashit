package map;

import java.awt.Graphics;
import java.awt.Rectangle;
import manager.PictureManager;


public abstract class Sprite {

	protected int x, y;
	protected int width, height;
	protected String[] images;
	protected int currentFrame = 0;
	protected boolean walkable;

	protected Sprite(int x, int y, String[] s, boolean wa) {
		this.x = x;
		this.y = y;
		images = s;
		width = PictureManager.getImage(s[0]).getWidth(null);
		height = PictureManager.getImage(s[0]).getHeight(null);
		walkable = wa;
	}

	public void act(long delay) {

	}

	public void paint(Graphics g) {
		g.drawImage(PictureManager.getImage(images[currentFrame]), x, y, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean b) {
		walkable = b;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, height, width);
	}

}