package menue;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import game.Finals;
import manager.PictureManager;

public class GamePanel extends JPanel implements Finals, KeyListener {

	String image;

	public GamePanel(String image) {
		this.image = image;
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(null);
		this.addKeyListener(this);
		requestFocus();
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

	public void keyPressed(KeyEvent e) {
		System.out.println("sdfsdf");
		if (e.getKeyCode() == 27) {
			BaseFrame.getBaseFrame().setVisible(false);
			BaseFrame.getBaseFrame().dispose();
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
