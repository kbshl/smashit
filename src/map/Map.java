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
		
		sprites.add(new Box(50, 63));
		sprites.add(new Box(50, 100));
		sprites.add(new Box(87, 100));
		sprites.add(new Box(87, 137));
		sprites.add(new Box(124, 137));
	
		sprites.add(new Box(600, 300));
		sprites.add(new Box(637, 300));
		sprites.add(new Box(674, 300));
		
//		sprites.add(new Box(0, 450));
//		sprites.add(new Box(37, 450));
//		sprites.add(new Box(74, 450));
//		sprites.add(new Box(111, 450));
//		sprites.add(new Box(148, 450));
//		sprites.add(new Box(185, 450));
//		sprites.add(new Box(222, 450));
//		sprites.add(new Box(259, 450));
//		sprites.add(new Box(296, 450));
//		sprites.add(new Box(333, 450));
//		sprites.add(new Box(370, 450));
//		sprites.add(new Box(407, 450));
//		sprites.add(new Box(444, 450));
//		sprites.add(new Box(481, 450));
//		sprites.add(new Box(518, 450));
//		sprites.add(new Box(555, 450));
//		sprites.add(new Box(592, 450));
//		sprites.add(new Box(629, 450));
//		sprites.add(new Box(666, 450));
//		sprites.add(new Box(703, 450));
//		sprites.add(new Box(740, 450));
//		sprites.add(new Box(777, 450));
		
		sprites.add(new Box(37, 413));
		sprites.add(new Box(703, 413));
		sprites.add(new Box(370, 413));
		sprites.add(new Box(370, 376));
		
		//sprites.add(new DarkPillar(0, 0));
		sprites.add(new GrassFloor(0, 450));

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
