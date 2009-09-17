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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import map.Box;
import map.BrickStone;
import map.Map;
import map.MayaStone;
import map.Sprite;
import menue.BaseFrame;
import menue.MainMenue;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class MapEditorController implements ActionListener, MouseListener {

	private MapEditorView view;
	private Map map = new Map();
	private String selectedObj, selectedBg;

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
				writeXML(datei, inhalte);
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
				datenArray[counter][0] = itemArt.toLowerCase();
				datenArray[counter][1] = itemX.toString();
				datenArray[counter][2] = itemY.toString();
				// System.out.println(datenArray[counter][0]);
				counter++;
			}
		}
		return datenArray;
	}

	public void writeXML(File datei, String[][] textPuffer) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			DOMSource source = new DOMSource(doc);

			Element element = doc.createElement("map");
			element.setAttribute("background", "bg_"
					+ view.showSelectedBackground().toLowerCase() + ".jpg");
			doc.appendChild(element);

			for (int i = 0; i < textPuffer.length; i++) {
				Element elem = doc.createElement(textPuffer[i][0]);
				element.appendChild(elem);
				elem.setAttribute("x", textPuffer[i][1]);
				elem.setAttribute("y", textPuffer[i][2]);
			}

			StreamResult result = new StreamResult(datei);

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();

			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
