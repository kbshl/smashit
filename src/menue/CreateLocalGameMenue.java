package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import game.LocalGameController;
import javax.swing.JTextField;
import java.io.File;
import game.Finals;
import java.util.Vector;

public class CreateLocalGameMenue extends JPanel implements Finals {

	private JButton btn_Back = new JButton("Zurück");
	private JButton btn_StartGame = new JButton("Start");
	private JLabel message = new JLabel();
	private JTextField txt_NamePlayer1 = new JTextField();;
	private JTextField txt_NamePlayer2 = new JTextField();;
	private JTextField txt_NamePlayer3 = new JTextField();;
	private JTextField txt_NamePlayer4 = new JTextField();;
	private JList mapList;
	private Vector<String> maps = new Vector<String>();

	private ButtonListener buttonListener = new ButtonListener();

	public CreateLocalGameMenue() {
		this.setSize(800, 600);
		this.setLayout(null);

		// TextFelder erstellen und platzieren
		txt_NamePlayer1.setBounds(150, 40, 100, 30);
		txt_NamePlayer2.setBounds(150, 80, 100, 30);
		txt_NamePlayer3.setBounds(150, 120, 100, 30);
		txt_NamePlayer4.setBounds(150, 160, 100, 30);

		// Buttons erstellen und platzieren
		btn_Back.setBounds(10, 500, 90, 30);
		btn_StartGame.setBounds(650, 500, 90, 30);
		btn_Back.setActionCommand("btn_Back");
		btn_StartGame.setActionCommand("btn_StartGame");
		btn_Back.addActionListener(buttonListener);
		btn_StartGame.addActionListener(buttonListener);

		// Mapliste erstellen
		File mapFolder = new File(MAP_PATH);
		String[] folderList = mapFolder.list();
		for (int i = 0; i < folderList.length; i++) {
			if (folderList[i].endsWith(".xml")) {
				String neu = folderList[i].substring(0,
						folderList[i].length() - 4);
				maps.addElement(neu);
			}
		}
		mapList = new JList(maps);
		mapList.setBounds(600, 10, 190, 400);
		mapList.setSelectedIndex(0);
		
		// Messagefeld
		message.setBounds(150, 500, 400, 30);

		// Hinzufügen aller Elemente
		add(btn_Back);
		add(btn_StartGame);
		add(txt_NamePlayer1);
		add(txt_NamePlayer2);
		add(txt_NamePlayer3);
		add(txt_NamePlayer4);
		add(mapList);
		add(message);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("btn_Back")) {
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
			}
			if (e.getActionCommand().equals("btn_StartGame")) {
				String[] name = new String[4];
				name[0] = txt_NamePlayer1.getText().trim();
				name[1] = txt_NamePlayer2.getText().trim();
				name[2] = txt_NamePlayer3.getText().trim();
				name[3] = txt_NamePlayer4.getText().trim();
				int anzahl = 0;
				for(int i = 0; i< name.length; ++i){
					if(!name[i].equals("")){
						anzahl++;
					}
				}
				
				if(anzahl > 1){
					new LocalGameController(maps.get(mapList.getSelectedIndex()),name[0],name[1],name[2],name[3]);
				}
				else{
					message.setText("Es müssen mindestens 2 Spielernamen eingetragen werden.");
				}
				
			}
		}
	}
}
