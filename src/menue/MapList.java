package menue;

import game.Finals;

import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;

public class MapList extends JList implements Finals {
	private Vector<String> maps = new Vector<String>();
	
	public MapList(int x, int y, boolean net){
		setBounds(x, y, 200, 400);
		String[] folderList;
		
		maps.add("Winners and Loosers");
		maps.add("Up to the Sky");
		maps.add("Smashland");
		maps.add("Western Railroads");
		maps.add("The Underground");
		
		if(!net){
			try{
				File mapFolder = new File(MAP_PATH_EXTERN);
				
				folderList = mapFolder.list();
				for (int i = 0; i < folderList.length; i++) {
					if (folderList[i].endsWith(".xml")) {
						String neu = folderList[i].substring(0,
								folderList[i].length() - 4);
						maps.addElement(neu);
					}
				}
			}catch(Exception e){
				
			}
		}

		this.setListData(maps);
		setSelectedIndex(0);
		
	}
	
	public String getSelectedMap(){
		return maps.get(this.getSelectedIndex()) + ".xml";
	}
	
}
