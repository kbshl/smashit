package mapeditor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.PictureManager;
import map.Map;
import menue.GameButton;
import menue.GameIconButton;
import menue.GamePanel;

public class MapEditorView extends GamePanel {

	private MapEditorController controller;
	private GameIconButton btn_stone = new GameIconButton(0, 550, "Backstein",
			"brickstone.jpg");
	private GameIconButton btn_box = new GameIconButton(0, 575, "Box",
			"box.jpg");

	private GameButton btn_laden = new GameButton(600, 550, "Laden");
	private GameButton btn_speichern = new GameButton(600, 575, "Speichern");

	private GameButton btn_loeschen = new GameButton(700, 550, "Löschen");
	private GameButton btn_zurueck = new GameButton(700, 575, "Zurück");

	private Icon icon;
	private JPanel gameArea;
	private Container cp;
	public Map map;
	private JComboBox fieldItemList;
	private JComboBox backgroundList;
	public String[] backgrounds = new String[3];
	public String[] fieldItems = new String[3];
	public String[] fieldFileNames = new String[3];

	public MapEditorView(MapEditorController c, Map m) {
		super("mapeditor.jpg");
		map = m;
		controller = c;
		// String[] feld = {"Feld1", "Feld2"};

		// ActionListener
		btn_box.addActionListener(controller);
		btn_stone.addActionListener(controller);
		btn_loeschen.addActionListener(controller);
		btn_zurueck.addActionListener(controller);

		// Actioncommands
		btn_box.setActionCommand("box");
		btn_stone.setActionCommand("stone");
		btn_loeschen.setActionCommand("loeschen");
		btn_zurueck.setActionCommand("zurueck");

		//Combobox für Backgrounds
		backgrounds[0] = "sky";
		backgrounds[1] = "desert";
		backgrounds[2] = "forest";
		backgroundList = ComboIconBoxFactory.buildComboBox(backgrounds);
		((JComponent) backgroundList.getRenderer()).setPreferredSize(new Dimension(25,25));
		backgroundList.setActionCommand("backgroundList");
		backgroundList.setSelectedIndex(0);
		backgroundList.setMaximumRowCount(2);
		backgroundList.setBounds(200, 550, 150, 30);
		backgroundList.addActionListener(controller);
		
		
		//ComboBox für Items
		fieldItems[0] = "brickstone";
		fieldItems[1] = "box";
		fieldFileNames[0] = "brickstone.jpg";
		fieldFileNames[1] = "box.jpg";
		fieldItemList = ComboIconBoxFactory.buildComboBox(fieldItems, fieldFileNames);
		((JComponent) fieldItemList.getRenderer()).setPreferredSize(new Dimension(25,25));
		fieldItemList.setActionCommand("fieldItemList");
		fieldItemList.setSelectedIndex(0);
		fieldItemList.setMaximumRowCount(2);
		fieldItemList.setBounds(0, 550, 150, 30);
		fieldItemList.addActionListener(controller);

		add(fieldItemList);
		add(backgroundList);
		add(btn_laden);
		add(btn_loeschen);
		add(btn_speichern);
		add(btn_zurueck);
		addMouseListener(controller);
	}

	public String showSelectedItem(){
		if (fieldItemList.getSelectedIndex() < 0){
			return "";
		}
		return fieldItems[fieldItemList.getSelectedIndex()];
	}
	
	public String showSelectedBackground(){
		if (backgroundList.getSelectedIndex() < 0){
			return "";
		}
		return backgrounds[backgroundList.getSelectedIndex()];
	}
	
	public JPanel getGameArea() {
		return gameArea;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(map.getBackground(), 0, 0, null);
		g.drawImage(PictureManager.getImage("hud.jpg"), 0, 550, null);
		// Level malen
		for (int i = 0; i < map.getSprites().size(); ++i) {
			map.getSprites().get(i).paint(g);
		}
		// Muss auskommentiert bleiben, da sonst die List nicht funktioniert!
		// requestFocus();

	}

}
