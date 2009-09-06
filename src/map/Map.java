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
		
		sprites.add(new Box(50, 75));
		sprites.add(new Box(50, 100));
		sprites.add(new Box(75, 100));
		sprites.add(new Box(75, 125));
		sprites.add(new Box(100, 125));
		
		sprites.add(new Box(500, 400));
		sprites.add(new Box(525, 400));
		sprites.add(new Box(550, 400));
		
		sprites.add(new Box(575, 325));
		sprites.add(new Box(600, 300));
		sprites.add(new Box(625, 300));
		sprites.add(new Box(650, 300));
		
		sprites.add(new Box(370, 450));
		sprites.add(new Box(370, 425));
		sprites.add(new Box(550, 226));
		sprites.add(new Box(525, 226));
		
		
		sprites.add(new Box(250, 500));
		sprites.add(new Box(275, 500));
		sprites.add(new Box(300, 500));
		sprites.add(new Box(300, 475));
		sprites.add(new Box(300, 450));
		
		sprites.add(new Box(250, 425));
		sprites.add(new Box(225, 425));
		sprites.add(new Box(200, 425));
		sprites.add(new Box(175, 425));
		

		sprites.add(new Box(100, 500));
		sprites.add(new Box(100, 475));
		sprites.add(new Box(125, 500));
		sprites.add(new Box(125, 425));

		sprites.add(new Box(100, 350));
		sprites.add(new Box(150, 300));
		sprites.add(new Box(75, 250));
		sprites.add(new Box(200, 250));
		
		sprites.add(new Box(400, 250));
		sprites.add(new Box(425, 250));
		sprites.add(new Box(450, 225));
		sprites.add(new Box(475, 200));
		sprites.add(new Box(500, 175));
		
		
		
		sprites.add(new OldStoneFloor(0, 525));
		

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
