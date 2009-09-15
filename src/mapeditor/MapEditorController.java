package mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import map.Box;
import map.BrickStone;
import map.Map;
import menue.BaseFrame;
import menue.MainMenue;

public class MapEditorController implements ActionListener, MouseListener {

	private MapEditorView view;
	private Map map = new Map();
	private String selectedObj,selectedBg;
	private Class so;

	public MapEditorController() {
		view = new MapEditorView(this, map);
		BaseFrame.getBaseFrame().setJPanel(view);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("loeschen")){
			map.getSprites().removeAllElements();
			view.repaint();
		}
		if (e.getActionCommand().equals("zurueck")){
			BaseFrame.getBaseFrame().setJPanel(new MainMenue());
		}
		if (e.getActionCommand().equals("backgroundList")){
			selectedBg = view.showSelectedBackground();
			//if (selectedBg.equals("desert")){
				view.map.setBackground(selectedBg + ".jpg");
				view.repaint();
			//}
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
		selectedObj = view.showSelectedItem();
		int xpos = e.getX() - (e.getX() % 25);
		int ypos = e.getY() - (e.getY() % 25);

		if (ypos <= (550 - 25) && ypos >= 0) {
			if (selectedObj.equals("box")) {
				map.getSprites().add(new Box(xpos, ypos));
			}
			if (selectedObj.equals("brickstone")) {
				map.getSprites().add(new BrickStone(xpos, ypos));
			}
			view.repaint();
		}
	}

}
