package menue;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public  class BaseFrame extends JFrame {

	private Container cp;
	private static BaseFrame bF;

	public BaseFrame() {
		this.setSize(800, 600);
		this.setTitle("SmashIt");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		cp = getContentPane();
		cp.setLayout(null);
		cp.add(new MainMenue());
	}

	public void setJPanel(JPanel p) {
		cp.remove(0);
		cp.add(p);
		p.revalidate();
	}
	
	public static BaseFrame getBaseFrame(){
		if(bF == null){
			bF = new BaseFrame();
		}
		return bF;
	}

	public static void main(String[] args) {
		BaseFrame.getBaseFrame();
	}

}
