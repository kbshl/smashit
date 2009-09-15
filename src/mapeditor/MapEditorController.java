package mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import menue.BaseFrame;

public class MapEditorController implements ActionListener,MouseListener{
	
	private MapEditorView mEV;
	private Boolean box_selected = false;
	private Boolean stone_selected = false;
	private Boolean maya_selected = false;
	private ImagePanel grafik2;
	
	
	public MapEditorController(){
		BaseFrame.getBaseFrame().setJPanel(new MapEditorView());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("box")){
			box_selected = !box_selected;
			stone_selected = false;
			maya_selected = false;
		}
		if (e.getActionCommand().equals("stone")){
			stone_selected = !stone_selected;
			box_selected = false;
			maya_selected = false;
		}
		if (e.getActionCommand().equals("loeschen")){
			mEV.getGameArea().removeAll();
			mEV.getGameArea().repaint();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (box_selected){
			grafik2 = new ImagePanel("box.jpg");
			grafik2.setBounds(e.getX(), e.getY(), 25, 25);
			mEV.getGameArea().add(grafik2);
			mEV.getGameArea().repaint();
		}
		if (stone_selected){
			grafik2 = new ImagePanel("brickstone.jpg");
			grafik2.setBounds(e.getX(), e.getY(), 25, 25);
			mEV.getGameArea().add(grafik2);
			mEV.getGameArea().repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
