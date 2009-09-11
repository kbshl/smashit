package menue;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import game.Finals;

import manager.PictureManager;

public class GamePanel extends JPanel implements Finals {

	String image;

	public GamePanel(String image) {
		this.image = image;
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.image != null) {
			g.drawImage(PictureManager.getImage(image), 0, 0, this);
		}
	}

	public Dimension getPreferredSize() {
		if (image != null) {
			return new Dimension(PictureManager.getImage(image).getWidth(null),
					PictureManager.getImage(image).getHeight(null));
		} else {
			return super.getPreferredSize();
		}
	}
}
