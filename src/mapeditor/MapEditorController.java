package mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import map.Box;
import map.BrickStone;
import map.Map;
import map.MayaStone;
import map.Sprite;
import menue.BaseFrame;
import menue.MainMenue;

public class MapEditorController implements ActionListener, MouseListener {

	private MapEditorView view;
	private Map map = new Map();
	private String selectedObj, selectedBg;
	private Class so;

	public MapEditorController() {
		view = new MapEditorView(this, map);
		BaseFrame.getBaseFrame().setJPanel(view);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("loeschen")) {
			map.getSprites().removeAllElements();
			view.repaint();
		}
		if (e.getActionCommand().equals("zurueck")) {
			BaseFrame.getBaseFrame().setJPanel(new MainMenue());
		}
		if (e.getActionCommand().equals("backgroundList")) {
			selectedBg = view.showSelectedBackground();
			// if (selectedBg.equals("desert")){
			view.getMap().setBackground("bg_" + selectedBg + ".jpg");
			view.repaint();
			// }
		}
		if (e.getActionCommand().equals("oeffnen")) {
			openLoadDialog();
		}

		if (e.getActionCommand().equals("speichern")) {
			openSaveDialog();
		}
	}

	private void openLoadDialog() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Map laden");
		fc.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".xml")
						|| f.isDirectory();
			}

			public String getDescription() {
				return "Map-Dateien(*.xml)";
			}
		});
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// Datei wird geladen
			File file = fc.getSelectedFile();
			String dateiPfad = file.getPath();
			String dateiName = file.getName();
			dateiPfad = dateiPfad.substring(0, (dateiPfad.length() - dateiName
					.length()));
			// Map wird gepaintet
			view.getMap().paintMap(dateiName, dateiPfad);
			view.repaint();
		}
	}

	private void openSaveDialog() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Map speichern");
		fc.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".xml")
						|| f.isDirectory();
			}

			public String getDescription() {
				return "Map-Dateien(*.xml)";
			}
		});
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File datei = fc.getSelectedFile();
			// System.out.println(datei.getName());
			String[][] inhalte = elementeAuslesen();
			try {
				writeFile(datei, inhalte);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		selectedObj = view.showSelectedItem();
		int xpos = e.getX() - (e.getX() % 25);
		int ypos = e.getY() - (e.getY() % 25);

		if (ypos <= (550 - 25) && ypos >= 0) {
			if (selectedObj.equals("box")) {
				map.getSprites().add(new Box(xpos, ypos));
			}
			if (selectedObj.equals("brickstone")) {
				map.getSprites().add(new BrickStone(xpos, ypos));
			}
			if (selectedObj.equals("mayastone")) {
				map.getSprites().add(new MayaStone(xpos, ypos));
			}
			view.repaint();
		}
	}

	private String[][] elementeAuslesen() {
		// Auslesen aller Elemente
		String[][] datenArray = new String[map.getSprites().size()][3];
		int counter = 0;
		for (Enumeration<Sprite> seb = map.getSprites().elements(); seb
				.hasMoreElements();) {
			Sprite item = seb.nextElement();
			String itemArt = item.getClass().getSimpleName();
			if (!itemArt.equals("WandLinks") && !itemArt.equals("WandRechts")
					&& !itemArt.equals("Boden")) {
				Integer itemX = item.getX();
				Integer itemY = item.getY();
				datenArray[counter][0] = itemArt;
				datenArray[counter][1] = itemX.toString();
				datenArray[counter][2] = itemY.toString();
				// System.out.println(datenArray[counter][0]);
				counter++;
			}
		}
		return datenArray;
	}

	public void writeFile(File datei, String[][] textPuffer)
			throws IOException, FileNotFoundException {
		BufferedWriter stream1;
		try {
			stream1 = new BufferedWriter(new FileWriter(datei));
			// System.out.println(textPuffer.length);
			String ausgabe = "<?xml version='1.0' encoding='utf-8'?>" + "\n";
			ausgabe += "<map background=\"" + view.showSelectedBackground()
					+ ".jpg\">" + "\n";
			for (int i = 0; i < textPuffer.length; i++) {
				ausgabe += "<" + textPuffer[i][0].toLowerCase();
				ausgabe += " x=\"" + textPuffer[i][1] + "\"";
				ausgabe += " y=\"" + textPuffer[i][2] + "\"";
				ausgabe += " /> " + "\n";
			}
			ausgabe += "</map>";
			stream1.write(ausgabe);
			stream1.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht vorhanden");
		} catch (IOException e) {
			System.out.println("Fehler beim schreiben");
		}
	}

	public void writeXML(File datei, String textPuffer) {

	}

}
