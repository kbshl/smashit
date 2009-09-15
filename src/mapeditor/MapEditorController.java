package mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import map.Box;
import map.Map;
import map.Sprite;
import menue.BaseFrame;

public class MapEditorController implements ActionListener,MouseListener{
	
	private MapEditorView view;
	private Map map = new Map();
	private String selectedObj;
	private Class so;
	
	
	
	public MapEditorController(){
		view = new MapEditorView(this, map);
		BaseFrame.getBaseFrame().setJPanel(view);
	}

	public void actionPerformed(ActionEvent e) {
			selectedObj = "box";
	}

	public void mouseClicked(MouseEvent e) {

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

		map.getSprites().add(new Box(e.getX(), e.getY()));
		view.repaint();
	}

}
