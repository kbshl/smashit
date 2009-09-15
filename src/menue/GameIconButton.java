package menue;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import manager.PictureManager;

public class GameIconButton extends JButton {
	
	private Icon bild;
	
	public GameIconButton(int x, int y, String s, String i) {
		
		bild = new ImageIcon(PictureManager.getImage(i));
		
		JLabel l = new JLabel(s);
		l.setBounds(0, 0, 100, 25);
		l.setAlignmentX(LEFT_ALIGNMENT);
		l.setIcon(bild);
		add(l);

		this.setText(s);
		this.setIcon(bild);
		this.setBounds(x, y, 100, 25);
		this.setBorder(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(PictureManager.getImage("button1.jpg"), 0, 0, this);
		
		if (getModel().isRollover()) {
			g.drawImage(PictureManager.getImage("button2.jpg"), 0, 0, this);
		}

	}

}
