package menue;

import game.Finals;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import manager.SoundManager;

public class BaseFrame extends JFrame implements Finals {

	private Container cp;
	private static BaseFrame bf;

	private BaseFrame() {
		// Um die Sounds initial zu laden
		SoundManager.getSoundManager();
		
		// setUndecorated(true);
		// DisplayMode displayMode = new DisplayMode(800, 600, 32, 60);
		// GraphicsEnvironment environment =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// GraphicsDevice device = environment.getDefaultScreenDevice();
		// device.setFullScreenWindow(this);
		// device.setDisplayMode(displayMode);

		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		this.setResizable(false);
		this.setTitle("Smash It'");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Größe des Bildschirms ermitteln
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Position des JFrames errechnen
		int top = (screenSize.height - this.getHeight()) / 2;
		int left = (screenSize.width - this.getWidth()) / 2;

		// Position zuordnen
		this.setLocation(left, top);

		cp = getContentPane();
		cp.setLayout(null);
		cp.add(new MainMenue());
		this.setUndecorated(true);
		this.setVisible(true);
		requestFocus();
		SoundManager.getSoundManager().loopSound(Finals.BACKGROUND_SOUND);
	}

	public void setJPanel(JPanel p) {
		cp.remove(0);
		cp.add(p);
		p.revalidate();
		this.repaint();
	}

	public static BaseFrame getBaseFrame() {
		if (bf == null) {
			bf = new BaseFrame();
		}
		return bf;
	}

	public static void main(String[] args) {
		BaseFrame.getBaseFrame();
	}

}
