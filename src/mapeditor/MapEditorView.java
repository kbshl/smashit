package mapeditor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

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

	private GameButton btn_background1 = new GameButton(300, 550, "Background1");
	private GameButton btn_background2 = new GameButton(300, 575, "Background2");

	private GameButton btn_laden = new GameButton(600, 550, "Laden");
	private GameButton btn_speichern = new GameButton(600, 575, "Speichern");

	private GameButton btn_loeschen = new GameButton(700, 550, "Löschen");
	private GameButton btn_zurueck = new GameButton(700, 575, "Zurück");

	private Icon icon;
	private JPanel gameArea;
	private Container cp;
	private Map map;

	public MapEditorView(MapEditorController c, Map m) {
		super("mapeditor.jpg");
		map = m;
		controller = c;

		// ActionListener
		btn_box.addActionListener(controller);
		btn_stone.addActionListener(controller);
		btn_loeschen.addActionListener(controller);

		// Actioncommands
		btn_box.setActionCommand("box");
		btn_stone.setActionCommand("stone");
		btn_loeschen.setActionCommand("loeschen");

		// Hinzufügen
		// add(gameArea);
		add(btn_stone);
		add(btn_box);
		add(btn_background1);
		add(btn_background2);
		add(btn_laden);
		add(btn_loeschen);
		add(btn_speichern);
		add(btn_zurueck);

	}

	public JPanel getGameArea() {
		return gameArea;
	}

}
