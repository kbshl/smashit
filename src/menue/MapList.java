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
		try{
			File mapFolder = new File(getClass().getClassLoader().getResource(MAP_PATH).toURI());
			
			System.out.println(mapFolder);
			JLabel o1 = new JLabel(mapFolder.toString());
			o1.setBounds(0, 150, 300, 50);
			JLabel o2 = new JLabel(mapFolder.toString());
			o2.setBounds(-180, 200, 500, 50);
			add(o1);
			add(o2);
			
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
		
		
	
		this.setListData(maps);
		setSelectedIndex(0);
		
	}
	
	public String getSelectedMap(){
		return maps.get(this.getSelectedIndex()) + ".xml";
	}
	
}
