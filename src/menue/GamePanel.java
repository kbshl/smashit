package menue;

import game.Finals;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		requestFocus();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			int i = JOptionPane.showConfirmDialog(BaseFrame.getBaseFrame(), "Wollen Sie das Spiel beenden?");
			if(i == JOptionPane.YES_OPTION){
				BaseFrame.getBaseFrame().setVisible(false);
				BaseFrame.getBaseFrame().dispose();
				System.exit(0);	
			}

		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
