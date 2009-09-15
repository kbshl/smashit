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
	
	private MapEditorController eEC;
	private GameIconButton btn_stone = new GameIconButton(0,550,"Backstein", "brickstone.jpg");
	private GameIconButton btn_box = new GameIconButton(0,575,"Box", "box.jpg");
	
	private GameButton btn_background1 = new GameButton(300,550,"Background1");
	private GameButton btn_background2 = new GameButton(300,575,"Background2");
	
	private GameButton btn_laden = new GameButton(600,550,"Laden");
	private GameButton btn_speichern = new GameButton(600,575,"Speichern");
	
	private GameButton btn_loeschen = new GameButton(700,550,"Löschen");
	private GameButton btn_zurueck = new GameButton(700,575,"Zurück");
	
	private Icon icon;
	private JPanel gameArea;
	private Container cp;
	private Map newMap;
	
	
	public MapEditorView(){
		super("mapeditor.jpg");

		

		//gameArea.add(grafik);
		
		
		//ActionListener
		btn_box.addActionListener(eEC);
		btn_stone.addActionListener(eEC);
		btn_loeschen.addActionListener(eEC);
		
		
		//Actioncommands
		btn_box.setActionCommand("box");
		btn_stone.setActionCommand("stone");
		btn_loeschen.setActionCommand("loeschen");
		
		//Hinzufügen
		//add(gameArea);
		add(btn_stone);
		add(btn_box);
		add(btn_background1);
		add(btn_background2);
		add(btn_laden);
		add(btn_loeschen);
		add(btn_speichern);
		add(btn_zurueck);
		
	}
	
	public JPanel getGameArea(){
		return gameArea;
	}
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("box")) {
				System.out.println("Box ausgewählt!");
			}
			if (e.getActionCommand().equals("oldstonefloor")) {
				System.out.println("Stein ausgewählt!");
			}
			if (e.getActionCommand().equals("grassfloor")) {
				System.out.println("Gras ausgewählt!");
			}
			if (e.getActionCommand().equals("item")) {
				System.out.println("Item ausgewählt!");
			}
		}

	}
	

}
