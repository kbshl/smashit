package mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import map.Map;
import map.Sprite;
import menue.BaseFrame;

public class MapEditorController implements ActionListener,MouseListener{
	
	private MapEditorView view;
	private Map map = new Map();
	private Sprite selectedObj;
	
	
	
	public MapEditorController(){
		view = new MapEditorView(this, map);
		BaseFrame.getBaseFrame().setJPanel(view);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("box")){

		}
		if (e.getActionCommand().equals("stone")){
	
		}
		if (e.getActionCommand().equals("loeschen")){
		
		}
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
		// TODO Auto-generated method stub
		
	}

}
