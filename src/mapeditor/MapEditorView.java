package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import manager.PictureManager;
import map.Map;
import menue.GameButton;
import menue.GamePanel;

public class MapEditorView extends GamePanel {

	private MapEditorController controller;

	private GameButton btn_laden = new GameButton(340, 562, "Laden");
	private GameButton btn_speichern = new GameButton(455, 562, "Speichern");

	private GameButton btn_loeschen = new GameButton(570, 562, "Alles löschen");
	private GameButton btn_zurueck = new GameButton(685, 562, "Zurück");
	private JLabel lbl_background = new JLabel();
	private JLabel lbl_elements = new JLabel();

	private Map map;
	private JComboBox fieldElementList;
	private JComboBox backgroundList;
	public String[] backgrounds = { "sky", "desert", "forest", "maya",
			"pillars", "dune" };
	public String[] backgroundFileNames = { "bg_sky.jpg", "bg_desert.jpg",
			"bg_forest.jpg", "bg_maya.jpg", "bg_pillars.jpg", "bg_dune.jpg" };
	public String[][] elements = { { "BrickStone", "obj_brickstone.jpg" },
			{ "Box", "obj_box.jpg" }, { "MayaStone", "obj_mayastone.jpg" },
			{ "Bush", "obj_bush.gif" },
			{ "DesertEarthLeft", "obj_desert_earth_left.jpg" },
			{ "DesertEarthMiddle", "obj_desert_earth_middle.jpg" },
			{ "DesertEarthRight", "obj_desert_earth_right.jpg" },
			{ "DesertLeft", "obj_desert_left.gif" },
			{ "DesertMiddle", "obj_desert_middle.jpg" },
			{ "DesertRight", "obj_desert_right.gif" },
			{ "DesertStone1", "obj_desert_stone1.gif" },
			{ "DesertStone2", "obj_desert_stone2.gif" },
			{ "Fence", "obj_fence.gif" }, { "Flower1", "obj_flower1.gif" },
			{ "Flower2", "obj_flower2.gif" },
			{ "GrassEarthLeft", "obj_grass_earth_left.gif" },
			{ "GrassEarthMiddle", "obj_grass_earth_middle.jpg" },
			{ "GrassEarthRight", "obj_grass_earth_right.gif" },
			{ "GrassLeft", "obj_grass_left.gif" },
			{ "GrassMiddle", "obj_grass_middle.jpg" },
			{ "GrassRight", "obj_grass_right.gif" },
			{ "Mushrooms", "obj_mushrooms.gif" },
			{ "PillarEnd", "obj_pillar_end.gif" },
			{ "Pillar", "obj_pillar.gif" }, { "Stone1", "obj_stone1.gif" },
			{ "Stone2", "obj_stone2.gif" }, };

	public MapEditorView(MapEditorController c, Map m) {
		super("mapeditor.jpg");
		map = m;
		// Muss gemacht werden, da sonst zu viele Elemente auf der Stage sind,
		// falls man speichert
		map.getSprites().removeAllElements();
		controller = c;
		
		//Labels
		lbl_background.setText("Hintergrund:");
		lbl_background.setBounds(150, 562, 80, 25);
		lbl_elements.setText("Element:");
		lbl_elements.setBounds(15, 562, 60, 25);

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

		// ComboBox für Elements
		fieldElementList = ComboIconBoxFactory.buildComboBox(elements);
		((JComponent) fieldElementList.getRenderer())
				.setPreferredSize(new Dimension(30, 30));
		fieldElementList.setActionCommand("fieldItemList");
		fieldElementList.setSelectedIndex(0);
		fieldElementList.setMaximumRowCount(13);
		fieldElementList.setBounds(70, 562, 60, 25);
		fieldElementList.addActionListener(controller);
		fieldElementList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Combobox für Backgrounds
		backgroundList = ComboIconBoxFactory.buildComboBox(backgroundFileNames);
		((JComponent) backgroundList.getRenderer())
				.setPreferredSize(new Dimension(25, 25));
		backgroundList.setActionCommand("backgroundList");
		backgroundList.setSelectedIndex(0);
		backgroundList.setMaximumRowCount(10);
		backgroundList.setBounds(225, 562, 100, 25);
		backgroundList.addActionListener(controller);
		backgroundList.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(lbl_elements);
		add(lbl_background);
		add(fieldElementList);
		add(backgroundList);
		add(btn_laden);
		add(btn_loeschen);
		add(btn_speichern);
		add(btn_zurueck);
		addMouseListener(controller);

	}

	public String showSelectedItem() {
		if (fieldElementList.getSelectedIndex() < 0) {
			return "";
		}
		return elements[fieldElementList.getSelectedIndex()][0];
	}

	public String showSelectedBackground() {
		if (backgroundList.getSelectedIndex() < 0) {
			return "";
		}
		return backgrounds[backgroundList.getSelectedIndex()];
	}

	public Map getMap() {
		return map;
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(map.getBackground(), 0, 0, null);
		g.drawImage(PictureManager.getPictureManager().getImage("hud.jpg"), 0,
				550, null);
		// Level malen
		for (int i = 0; i < map.getSprites().size(); ++i) {
			map.getSprites().get(i).paint(g);
		}

	}

}
