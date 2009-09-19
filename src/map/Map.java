package map;

import game.Finals;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import manager.PictureManager;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Map implements Finals {
	private String name = "Level1";
	private Vector<Sprite> sprites;
	private String background = "bg_sky.jpg";
	//private String mapPath;

	public Map(String mapFile) {
		sprites = new Vector<Sprite>();
		sprites.add(new WandLinks());
		sprites.add(new WandRechts());
		sprites.add(new Boden());

		loadMap(mapFile,"");

	}

	public Map() {
		sprites = new Vector<Sprite>();
		sprites.add(new WandLinks());
		sprites.add(new WandRechts());
		sprites.add(new Boden());
	}

	public void paintMap(String mapFile) {
		loadMap(mapFile,"");
	}
	
	public void loadMap(String mapFile, String mapPath) {
		if (mapPath == ""){
			mapPath = MAP_PATH;
		}
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(mapPath + mapFile));

			Node rootNode = document.getDocumentElement();
			NamedNodeMap background_attr = rootNode.getAttributes();
			if (background_attr != null) {
				String background_str = background_attr.item(0).getNodeValue();
				if (!background_str.equals("")) {
					background = background_str;
				}
			}

			NodeList nodes = rootNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				String nodeName = nodes.item(i).getNodeName();
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				if (attributes != null) {
					String xv = attributes.item(0).getNodeValue();
					String yv = attributes.item(1).getNodeValue();

					if (!xv.equals("") && !yv.equals("")) {
						int x = Integer.valueOf(xv).intValue();
						int y = Integer.valueOf(yv).intValue();
						// Test
						try {
							Class obj = Class.forName("map." + nodeName);
							Constructor[] cons = obj.getConstructors();
							for (Constructor constructor : cons) {
								Class[] params = constructor.getParameterTypes();
								if (params.length == 2
										&& params[0].getName().equals("int")
										&& params[1].getName().equals("int")) {
									Sprite o = (Sprite) constructor.newInstance(x,
											y);
									sprites.add(o);
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
					}
					// Test zu Ende
					
//					if (nodeName.equals("box")) {
//						sprites.add(new Box(x, y));
//					}
//					if (nodeName.equals("brickstone")) {
//						sprites.add(new BrickStone(x, y));
//					}
//					if (nodeName.equals("mayastone")) {
//						sprites.add(new MayaStone(x, y));
//					}
//					if (nodeName.equals("oldstonefloor")) {
//						sprites.add(new OldStoneFloor(x, y));
//					}
//					if (nodeName.equals("cloud")) {
//						sprites.add(new Cloud(x, y));
//					}

				}
			}

			// ---- Error handling ----
		} catch (SAXParseException spe) {
			System.out.println("\n** Parsing error, line "
					+ spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println("   " + spe.getMessage());
			Exception e = (spe.getException() != null) ? spe.getException()
					: spe;
			e.printStackTrace();
		} catch (SAXException sxe) {
			Exception e = (sxe.getException() != null) ? sxe.getException()
					: sxe;
			e.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public Vector<Sprite> getSprites() {
		return sprites;
	}

	public void setBackground(String image) {
		background = image;
	}

	public Image getBackground() {
		return PictureManager.getPictureManager().getImage(background);
	}

	public String getName() {
		return name;
	}
}
