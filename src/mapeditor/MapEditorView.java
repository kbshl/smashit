package mapeditor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import manager.PictureManager;
import map.Map;
import menue.GameButton;
import menue.GameIconButton;
import menue.GamePanel;

public class MapEditorView extends GamePanel {

	private MapEditorController controller;

	private GameButton btn_laden = new GameButton(600, 550, "Laden");
	private GameButton btn_speichern = new GameButton(600, 575, "Speichern");

	private GameButton btn_loeschen = new GameButton(700, 550, "Löschen");
	private GameButton btn_zurueck = new GameButton(700, 575, "Zurück");

	private Icon icon;
	private JPanel gameArea;
	private Container cp;
	private Map map;
	private JComboBox fieldItemList;
	private JComboBox backgroundList;
	public String[] backgrounds = new String[6];
	public String[] backgroundFileNames = new String[6];
	public String[] fieldItems = new String[3];
	public String[][] elements = {
			{"BrickStone", "obj_brickstone.jpg"},
			{"Box","obj_box.jpg"},
			{"MayaStone","obj_mayastone.jpg"},
			{"Bush","obj_bush.gif"},
			{"Cloud","obj_cloud.gif"},
			{"DesertEarthLeft","obj_desert_earth_left.jpg"},
			{"DesertEarthMiddle","obj_desert_earth_middle.jpg"},
			{"DesertEarthRight","obj_desert_earth_right.jpg"},
			{"DesertLeft", "obj_desert_left.gif"},
			{"DesertMiddle", "obj_desert_middle.jpg"},
			{"DesertRight", "obj_desert_right.gif"},
			{"DesertStone1", "obj_desert_stone1.gif"},
			{"DesertStone2", "obj_desert_stone2.gif"},
			{"Fence", "obj_fence.gif"},
			{"Flower1", "obj_flower1.gif"},
			{"Flower2", "obj_flower2.gif"},
			{"GrassEarthLeft", "obj_grass_earth_left.gif"},
			{"GrassEarthMiddle", "obj_grass_earth_middle.jpg"},
			{"GrassEarthRight", "obj_grass_earth_right.gif"},
			{"GrassLeft", "obj_grass_left.gif"},
			{"GrassMiddle", "obj_grass_middle.jpg"},
			{"GrassRight", "obj_grass_right.gif"}
	};

	public MapEditorView(MapEditorController c, Map m) {
		super("mapeditor.jpg");
		map = m;
		// Muss gemacht werden, da sonst zu viele Elemente auf der Stage sind,
		// falls man speichert
		map.getSprites().removeAllElements();
		controller = c;
		// String[] feld = {"Feld1", "Feld2"};

		// ActionListener
		btn_loeschen.addActionListener(controller);
		btn_zurueck.addActionListener(controller);
		btn_speichern.addActionListener(controller);
		btn_laden.addActionListener(controller);

		// Actioncommands
		btn_loeschen.setActionCommand("loeschen");
		btn_zurueck.setActionCommand("zurueck");
		btn_speichern.setActionCommand("speichern");
		btn_laden.setActionCommand("oeffnen");

		// Combobox für Backgrounds
		backgrounds[0] = "sky";
		backgrounds[1] = "desert";
		backgrounds[2] = "forest";
		backgrounds[3] = "maya";
		backgrounds[4] = "pillars";
		backgrounds[5] = "dune";
		backgroundFileNames[0] = "bg_sky.jpg";
		backgroundFileNames[1] = "bg_desert.jpg";
		backgroundFileNames[2] = "bg_forest.jpg";
		backgroundFileNames[3] = "bg_maya.jpg";
		backgroundFileNames[4] = "bg_pillars.jpg";
		backgroundFileNames[5] = "bg_dune.jpg";
		backgroundList = ComboIconBoxFactory.buildComboBox(backgroundFileNames);
		((JComponent) backgroundList.getRenderer())
				.setPreferredSize(new Dimension(30, 30));
		backgroundList.setActionCommand("backgroundList");
		backgroundList.setSelectedIndex(0);
		backgroundList.setMaximumRowCount(4);
		backgroundList.setBounds(90, 570, 120, 30);
		backgroundList.addActionListener(controller);

		// ComboBox für Items
		fieldItemList = ComboIconBoxFactory.buildComboBox(elements);
		((JComponent) fieldItemList.getRenderer())
				.setPreferredSize(new Dimension(30, 30));
		fieldItemList.setActionCommand("fieldItemList");
		fieldItemList.setSelectedIndex(0);
		fieldItemList.setMaximumRowCount(4);
		fieldItemList.setBounds(0, 570, 60, 30);
		fieldItemList.addActionListener(controller);

		add(fieldItemList);
		add(backgroundList);
		add(btn_laden);
		add(btn_loeschen);
		add(btn_speichern);
		add(btn_zurueck);
		addMouseListener(controller);

	}

	public String showSelectedItem() {
		if (fieldItemList.getSelectedIndex() < 0) {
			return "";
		}
		return elements[fieldItemList.getSelectedIndex()][0];
	}

	public String showSelectedBackground() {
		if (backgroundList.getSelectedIndex() < 0) {
			return "";
		}
		return backgrounds[backgroundList.getSelectedIndex()];
	}

	public JPanel getGameArea() {
		return gameArea;
	}

	public Map getMap() {
		return map;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(map.getBackground(), 0, 0, null);
		g.drawImage(PictureManager.getPictureManager().getImage("hud.jpg"), 0, 550, null);
		// Level malen
		for (int i = 0; i < map.getSprites().size(); ++i) {
			map.getSprites().get(i).paint(g);
		}
		// Muss auskommentiert bleiben, da sonst die List nicht funktioniert!
		// requestFocus();

	}

}
