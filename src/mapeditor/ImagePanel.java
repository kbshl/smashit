package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import manager.PictureManager;

public class ImagePanel extends JPanel {
	private String pfadOfImage = null;
	private Image image = null;

	public ImagePanel(String imagePfad) {
		super();
		pfadOfImage = imagePfad;
		image = PictureManager.getImage(pfadOfImage);
		this.setBorder(null);
		this.setLayout(null);
	}

	public int getImageHeight() {
		return image.getHeight(this);
	}

	public int getImageWidth() {
		return image.getWidth(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (pfadOfImage != null) {
			g.drawImage(image,0,0,25,25,this);
		}
	}
	
	public Dimension getPreferredSize() {
		if (image != null) {
			return new Dimension(image.getWidth(null),
					image.getHeight(null));
		} else {
			return super.getPreferredSize();
		}
	}
}
