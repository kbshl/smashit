package manager;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import game.Finals;

public class PictureManager implements Finals {
	private static PictureManager pm;

	private static HashMap<String, Image> images = new HashMap<String, Image>();

	private PictureManager() {

	}

	public static PictureManager getPictureManager() {
		if (pm == null) {
			pm = new PictureManager();
		}
		return pm;
	}

	private static Image loadImage(String name) {
		java.net.URL imgURL;
		imgURL = PictureManager.class.getResource(PIC_PATH + name);
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		} else {
			return null;
		}
	}

	public static Image getImage(String name) {
		Image img = (Image) images.get(name);
		if (img == null) {
			img = loadImage(name);
			images.put(name, img);
		}
		return img;
	}
}
