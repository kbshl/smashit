package menue;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaseFrame extends JFrame{
	
	private Container cp;
	
	public BaseFrame(){
		this.setSize(800, 600);
		this.setTitle("SmashIt");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new MainMenue(),BorderLayout.CENTER);
		
	}
	
	public void setJPanel(JPanel p){
		cp.remove(0);
		cp.add(p, BorderLayout.CENTER);
	}
	
	public static void main(String[] args){
		new BaseFrame();
	}

}
