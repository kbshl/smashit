package mapeditor;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ComboIconBoxFactory extends JPanel {

	private static ArrayList<JComboBox> builtBoxes = new ArrayList<JComboBox>();

	public static JComboBox buildComboBox(String[] bezeichnung){
    	
		String[] dateinamen = new String[bezeichnung.length];
		for (int i = 0; i < bezeichnung.length; i++) {
			dateinamen[i] = bezeichnung[i] + ".jpg";
		}
		
    	return buildComboBox(bezeichnung,dateinamen);
    }
	
	public static JComboBox buildComboBox(String[] bezeichnung, String[] dateinamen){
		
		Integer[] intArray = new Integer[bezeichnung.length];
		ImageIcon[] icons = new ImageIcon[bezeichnung.length];
		for (int i = 0; i < bezeichnung.length; i++) {
			intArray[i] = new Integer(i);
			icons[i] = new ImageIcon("src/resource/pics/"
					+ dateinamen[i]);
			if (icons[i] != null) {
				icons[i].setDescription(bezeichnung[i]);
			}
		}
		JComboBox cmb = new JComboBox(intArray);
		ComboBoxRenderer renderer = new ComboBoxRenderer(icons, bezeichnung);
		renderer.setOpaque(true);
		cmb.setRenderer(renderer);
		
		
		return cmb;
	}
}
