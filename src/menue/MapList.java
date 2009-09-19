package menue;

import game.Finals;

import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;

public class MapList extends JList implements Finals {
	private Vector<String> maps = new Vector<String>();
	
	public MapList(int x, int y){
		setBounds(x, y, 200, 400);
		String[] folderList;
//		try{
//			File mapFolder = new File(getClass().getClassLoader().getResource(MAP_PATH).toURI());
//			
//			folderList = mapFolder.list();
//			for (int i = 0; i < folderList.length; i++) {
//				if (folderList[i].endsWith(".xml")) {
//					String neu = folderList[i].substring(0,
//							folderList[i].length() - 4);
//					maps.addElement(neu);
//				}
//			}
//		}catch(Exception e){
//			
//		}
		
		
		maps.add("Standartmap1");
		maps.add("Standartmap2");
		maps.add("Standartmap3");
		this.setListData(maps);
		setSelectedIndex(0);
		
	}
	
	public String getSelectedMap(){
		return maps.get(this.getSelectedIndex()) + ".xml";
	}
	
}
