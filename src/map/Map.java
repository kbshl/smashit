package map;

import java.awt.Image;
import java.util.Vector;
import manager.PictureManager;



public class Map {
	private String name = "Level1";
	private Vector<Sprite> sprites;
	private String background = "desert.jpg";

	public Map() {
		sprites = new Vector<Sprite>();
		sprites.add(new WandLinks());
		sprites.add(new WandRechts());
		sprites.add(new Boden());
		
		sprites.add(new Box(50, 63));
		sprites.add(new Box(50, 100));
		sprites.add(new Box(87, 100));
		sprites.add(new Box(87, 137));
		sprites.add(new Box(124, 137));
	
		sprites.add(new Box(600, 300));
		sprites.add(new Box(637, 300));
		sprites.add(new Box(674, 300));
		
		sprites.add(new Box(370, 443));
		sprites.add(new Box(370, 406));
		sprites.add(new Box(563, 226));
		sprites.add(new Box(526, 226));
		
		
		sprites.add(new Stump1(450, 369));
		sprites.add(new Stump2(20, 296));
		sprites.add(new OldStoneFloor(0, 480));
		

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
