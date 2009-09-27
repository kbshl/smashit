package mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import map.Map;
import map.Sprite;
import menue.BaseFrame;
import menue.MainMenue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MapEditorController implements ActionListener, MouseListener {

	private MapEditorView view;
	private Map map = new Map();
	private String selectedObj, selectedBg;

	public MapEditorController() {
		
		// Ordner Maps wird angelegt
		String dirName = "maps";
		File f = new File(dirName);
		if (!f.isDirectory()) {
			f.mkdir();
		}

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
		JFileChooser fc = new JFileChooser("maps");
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

			view.getMap().loadMap(dateiName);

			view.repaint();
		}
	}

	private void openSaveDialog() {
		JFileChooser fc = new JFileChooser("maps");
		fc.setDialogTitle("Map speichern");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".xml");
			}

			public String getDescription() {
				return "Map-Dateien(*.xml)";
			}
		});
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File datei = fc.getSelectedFile();
			if (!datei.getPath().toLowerCase().endsWith(".xml")){
				datei = new File(datei.getPath()+".xml");
			}
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
		if (e.getButton() == MouseEvent.BUTTON1) {
			selectedObj = view.showSelectedItem();
			int xpos = e.getX() - (e.getX() % 25);
			int ypos = e.getY() - (e.getY() % 25);

			if (ypos <= (550 - 25) && ypos >= 0) {

				try {
					Class i = Class.forName("map." + selectedObj);
					Constructor[] cons = i.getConstructors();
					for (Constructor constructor : cons) {
						Class[] params = constructor.getParameterTypes();
						if (params.length == 2
								&& params[0].getName().equals("int")
								&& params[1].getName().equals("int")) {
							Sprite o = (Sprite) constructor.newInstance(xpos,
									ypos);
							map.getSprites().add(o);
							break;
						}
					}
				} catch (ClassNotFoundException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				} catch (IllegalArgumentException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				} catch (InstantiationException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				} catch (IllegalAccessException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				} catch (InvocationTargetException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				}
				view.repaint();
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Vector<Sprite> alleElemente = view.getMap().getSprites();
			Sprite hasToBeDeleted = null;
			for (Iterator<Sprite> ae = alleElemente.iterator(); ae.hasNext();) {
				Sprite aktuellesElement = (Sprite) ae.next();
				if (aktuellesElement.containsPoint(e.getX(), e.getY())) {
					hasToBeDeleted = aktuellesElement;
				}
			}
			if (hasToBeDeleted != null) {
				view.getMap().getSprites().remove(hasToBeDeleted);
				view.repaint();
			}
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

	public void writeXML(File datei, String[][] textPuffer)
			throws FileNotFoundException, XMLStreamException {
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

			// XMLOutputter outputter = new XMLOutputter();
			// FileOutputStream output = new FileOutputStream("file.xml");
			// outputter.output(doc, output);

			StreamResult result = new StreamResult(datei);

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
