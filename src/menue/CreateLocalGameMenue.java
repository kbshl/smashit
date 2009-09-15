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

public class CreateLocalGameMenue extends GamePanel implements Finals {

	private GameButton btn_Back = new GameButton(20,565,"Zurück");
	private GameButton btn_StartGame = new GameButton(680,565,"Start");
	private JLabel message = new JLabel();
	private GameTextField txt_NamePlayer1 = new GameTextField( 160, 30);
	private GameTextField txt_NamePlayer2 = new GameTextField( 160, 95);
	private GameTextField txt_NamePlayer3 = new GameTextField( 160, 160);
	private GameTextField txt_NamePlayer4 = new GameTextField( 160, 225);
	private GameTextField txt_lifes = new GameTextField( 160, 290);
	private MapList mapList = new MapList(570, 30);

	private ButtonListener buttonListener = new ButtonListener();

	public CreateLocalGameMenue() {
		super("gamepanel_localgame.jpg");

		// Buttons erstellen und platzieren
		btn_Back.setActionCommand("btn_Back");
		btn_StartGame.setActionCommand("btn_StartGame");
		btn_Back.addActionListener(buttonListener);
		btn_StartGame.addActionListener(buttonListener);

		message.setBounds(200, 565, 400, 25);
		txt_lifes.setText("10");

		// Hinzufügen aller Elemente
		add(btn_Back);
		add(btn_StartGame);
		add(txt_NamePlayer1);
		add(txt_NamePlayer2);
		add(txt_NamePlayer3);
		add(txt_NamePlayer4);
		add(txt_lifes);
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
				String l = txt_lifes.getText().trim();
				int leben = 10;
				try{
					leben = Integer.valueOf( l ).intValue();	
				}catch(Exception E){
					leben = 10;
				}
				
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
					new LocalGameController(mapList.getSelectedMap(),name[0],name[1],name[2],name[3],leben);
				}
				else{
					message.setText("Es müssen mindestens 2 Spielernamen eingetragen werden.");
				}
				
			}
		}
	}
}
