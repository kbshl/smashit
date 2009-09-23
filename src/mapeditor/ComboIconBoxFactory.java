package mapeditor;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import manager.PictureManager;

public class ComboIconBoxFactory extends JPanel {

	private static ArrayList<JComboBox> builtBoxes = new ArrayList<JComboBox>();

	public static JComboBox buildComboBox(String[][] elements) {

		Integer[] intArray = new Integer[elements.length];
		ImageIcon[] icons = new ImageIcon[elements.length];
		for (int i = 0; i < elements.length; i++) {
			intArray[i] = new Integer(i);
			icons[i] = new ImageIcon(PictureManager.getPictureManager()
					.getImage(elements[i][1]));
			if (icons[i] != null) {
			}
		}
		JComboBox cmb = new JComboBox(intArray);
		ComboBoxRenderer renderer = new ComboBoxRenderer(icons);
		renderer.setOpaque(true);
		cmb.setRenderer(renderer);

		return cmb;
	}

	public static JComboBox buildComboBox(String[] backgrounds) {

		Integer[] intArray = new Integer[backgrounds.length];
		ImageIcon[] icons = new ImageIcon[backgrounds.length];
		for (int i = 0; i < backgrounds.length; i++) {
			intArray[i] = new Integer(i);
			icons[i] = new ImageIcon(PictureManager.getPictureManager()
					.getImage(backgrounds[i]));
			if (icons[i] != null) {
			}
		}
		JComboBox cmb = new JComboBox(intArray);
		ComboBoxRenderer renderer = new ComboBoxRenderer(icons);
		renderer.setOpaque(true);
		cmb.setRenderer(renderer);

		return cmb;
	}
}
