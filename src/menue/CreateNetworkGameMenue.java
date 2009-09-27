package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.Map;

import network.FullPlayer;

import network.NetworkGameController;
import network.ServerPositionReceiver;
import network.ServerPositionSender;

public class CreateNetworkGameMenue extends GamePanel {

	private GameButton btn_Start;
	private GameButton btn_PlayerListenerStartStop;
	private GameButton btn_Back;


	private GameTextField txt_PlayerName;
	private GameTextField txt_MaxPlayer;
	private GameTextField txt_PlayerLife;
	private JList lst_ConnectedPlayer;
	private MapList lst_Maps;

	private Object[][] playerData;
	private Vector<FullPlayer> player = new Vector<FullPlayer>();
	private Vector<String> vtr_PlayerNames = new Vector<String>();
	private PlayerListenerController pLC;
	private CreateNetworkGameMenue cNGM;
	private ButtonListener lis_BtnListener = new ButtonListener();
	private int playerLives, maxPlayer;

	public CreateNetworkGameMenue() {
		super("gamepanel_createnetworkgame.jpg");
		this.setLayout(null);
		this.setSize(800, 600);

		cNGM = this;

		// Instanzen erstellen
		btn_Start = new GameButton(680, 565, "Start");
		btn_PlayerListenerStartStop = new GameButton(570, 565, "Horchen");
		// btn_PlayerListenerStartStop.setText("Horchen");
		btn_Back = new GameButton(20, 565, "Zurück");

		btn_Start.setEnabled(false);


		txt_PlayerName = new GameTextField(160, 30);
		txt_MaxPlayer = new GameTextField(160, 95);
		txt_PlayerLife = new GameTextField(160, 160);

		txt_PlayerName.setText("ServerSpieler");
		txt_MaxPlayer.setText("8");
		txt_PlayerLife.setText("10");

		lst_ConnectedPlayer = new JList();
		lst_Maps = new MapList(570, 30, true);


		// btn_Back.setBounds(100, 400, 100, 30);
		// btn_PlayerListenerStartStop.setBounds(250, 400, 100, 30);
		// btn_Start.setBounds(400, 400, 100, 30);

		lst_ConnectedPlayer.setBounds(160, 225, 100, 200);

		// ActionCommand
		btn_Start.setActionCommand("btn_Start");
		btn_PlayerListenerStartStop
				.setActionCommand("btn_PlayerListenerStartStop");
		btn_Back.setActionCommand("btn_Back");

		// Actionlistener hinzufügen
		btn_Start.addActionListener(lis_BtnListener);
		btn_PlayerListenerStartStop.addActionListener(lis_BtnListener);
		btn_Back.addActionListener(lis_BtnListener);

		// Hinzufügen der Objekte zum JPane
		add(btn_Start);
		add(btn_PlayerListenerStartStop);
		add(btn_Back);


		add(txt_PlayerName);
		add(txt_MaxPlayer);
		add(txt_PlayerLife);
		add(lst_ConnectedPlayer);
		add(lst_Maps);
	}

	public void setPlayerList(Vector<String> l) {
		lst_ConnectedPlayer.setListData(l);
		vtr_PlayerNames = l;
	}

	public void setPlayerData(Object[][] data) {
		this.playerData = data;
		// 0 = SockIn
		// 1 = SockOut
	}

	// erstellt die spieler/ map /sender receiver / und übergibt an
	// LocalGameController
	private void startGame() {
		Map map = new Map(lst_Maps.getSelectedMap());

		ServerPositionSender sPS = new ServerPositionSender(getPlayerNames(),
				playerData, map, playerLives);
		// map erstellen

		// Spieler erstellen + sender Receiver erstellen
		int i = 0;
		player.add((new FullPlayer(txt_PlayerName.getText(), 1, map, player,
				true, null, playerLives, sPS)));

		System.out.println("server spieler erstellt");
		++i;
		while (playerData[i - 1][0] != null) {
			// sPS = new ServerPositionSender(player, playerData, map, 4);

			player.add(new FullPlayer(vtr_PlayerNames.get(i - 1), i + 1, map,
					player, false, null, playerLives, sPS));// erstellt einen
															// Normalen Player
															// und den rest
															// HostPlayer
			new ServerPositionReceiver((FullPlayer) player.get(i),
					(BufferedReader) playerData[i - 1][0]);

			++i;
			System.out.println("andere spieler erstellt");

		}

		new NetworkGameController(player, map, sPS);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Start")) {
				startGame();
			}
			if (e.getActionCommand().equals("btn_PlayerListenerStartStop")) {

				if (!txt_PlayerName.getText().equals("")
						&& !txt_MaxPlayer.getText().equals("")
						&& !txt_PlayerLife.getText().equals("")) { //

					txt_PlayerName.setEditable(false);
					txt_MaxPlayer.setEditable(false);
					txt_PlayerLife.setEditable(false);

					// überprüfung ob werte Stimmen können
					if (Integer.parseInt(txt_MaxPlayer.getText()) > 0
							&& Integer.parseInt(txt_MaxPlayer.getText()) <= 8) {
						maxPlayer = Integer.parseInt(txt_MaxPlayer.getText());
					} else {
						maxPlayer = 8;
						txt_MaxPlayer.setText("8");
					}
					if (Integer.parseInt(txt_PlayerLife.getText()) > 0
							&& Integer.parseInt(txt_PlayerLife.getText()) <= 10) {
						playerLives = Integer
								.parseInt(txt_PlayerLife.getText());
					} else {
						playerLives = 8;
						txt_PlayerLife.setText("8");
					}

					if (btn_PlayerListenerStartStop.getLabelText().equals(
							"Horchen")) {// Start

						lst_ConnectedPlayer.setListData(new String[] {});
						pLC = new PlayerListenerController(cNGM, maxPlayer);
						btn_PlayerListenerStartStop
								.setLabelText("Genug gewartet");
						// btn_PlayerListenerStartStop.setEnabled(false);
					} else {// Stop

						// System.out.println(vtr_PlayerNames.size());//
						if (vtr_PlayerNames.size() >= 1) {// mindestens ein
															// player angemeldet
							pLC.interrupt();
							btn_PlayerListenerStartStop.setEnabled(false);
							btn_Start.setEnabled(true);
							pLC.close();
						} else {// kein spieler angemeldet
							// pLC.close();
							// ///////////////

							btn_PlayerListenerStartStop.setEnabled(true);
						}

						// btn_PlayerListenerStartStop.setText("Auf Spiler warten");

					}
				}
			}
			if (e.getActionCommand().equals("btn_Back")) {
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());

				if (pLC != null) {
					pLC.interrupt();
					pLC.close();
				}

			}

		}

	}

	private String getPlayerNames() {
		String s = txt_PlayerName.getText();// name aus spieler feld
		for (int i = 0; i < vtr_PlayerNames.size(); i++) {
			s = s + ":" + vtr_PlayerNames.get(i);
		}

		System.out.println(s);
		return s;
	}

}
