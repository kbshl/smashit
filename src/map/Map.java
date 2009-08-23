package map;

import java.awt.Image;
import java.util.Vector;
import manager.PictureManager;



public class Map {
	private String name = "Level1";
	private Vector<Sprite> sprites;
	private String background = "bg.jpg";

	public Map() {
		sprites = new Vector<Sprite>();
		sprites.add(new Box(100, 100));
		sprites.add(new Box(300, 200));
		sprites.add(new Box(600, 300));

	}

	public Vector<Sprite> getSprites() {
		return sprites;
	}

	public Image getBackground() {
		return PictureManager.getImage(background);
	}

	public String getName() {
		return name;
	}
}
