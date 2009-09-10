package menue;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

import manager.SoundManager;
import game.Finals;
import game.LocalGameController;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class BaseFrame extends JFrame implements Finals {

	private Container cp;
	private static BaseFrame bf;

	private BaseFrame() {
		
		setUndecorated(true);
		DisplayMode displayMode = new DisplayMode(800, 600, 32, 60);
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		device.setFullScreenWindow(this);
		device.setDisplayMode(displayMode);
		
		
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		this.setResizable(false);
		this.setTitle("Smash It'");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.add(new MainMenue());
		this.setVisible(true);
		requestFocus();
		SoundManager.getSoundManager().loopSound("smwovr1.mid");
	}

	public void setJPanel(JPanel p) {
		cp.remove(0);
		cp.add(p);
		p.revalidate();
		this.repaint();
	}
	
	public static BaseFrame getBaseFrame(){
		if(bf == null){
			bf = new BaseFrame();
		}
		return bf;
	}

	public static void main(String[] args) {
		BaseFrame.getBaseFrame();
	}

}
