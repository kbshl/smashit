package mapeditor;

import javax.swing.JPanel;
import java.util.List;

public class MapEditorView extends JPanel{
	
	private MapEditorController eEC;
	
	
	public MapEditorView(){
		this.setSize(800, 600);
		this.setLayout(null);	
		eEC = new MapEditorController(this);
		
		
		
		
	}
	
	

}
