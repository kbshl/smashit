package menue;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import manager.PictureManager;

public class GameButton extends JButton {

	public GameButton(int x, int y, String s) {
		JLabel l = new JLabel(s);
		l.setBounds(0, 0, 100, 25);
		l.setAlignmentX(CENTER_ALIGNMENT);
		add(l);
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
