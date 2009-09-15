package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import mapeditor.MapEditorController;

public class MainMenue extends GamePanel {

	
	private GameButton btn_NewLocalGame = new GameButton(20,20,"Lokales Spiel");
	private GameButton btn_NewNetworkGame = new GameButton(20,55,"NetzwerkSpiel");
	private GameButton btn_MapEditor = new GameButton(20,90,"Mapeditor");
	private GameButton btn_Highscore = new GameButton(20,125,"Highscore");
	private GameButton btn_Help = new GameButton(20,160,"Hilfe");

	private ButtonListener lis_BtnListener = new ButtonListener();

	public MainMenue() {
		super("gamepanel_mainmenue.jpg");

		// Actioncommand
		btn_Help.setActionCommand("btn_Help");
		btn_NewLocalGame.setActionCommand("btn_NewLocalGame");
		btn_NewNetworkGame.setActionCommand("btn_NewNetworkGame");
		btn_Highscore.setActionCommand("btn_Highscore");
		btn_MapEditor.setActionCommand("btn_MapEditor");

		// Actionlistener hinzufügen
		btn_Help.addActionListener(lis_BtnListener);
		btn_NewLocalGame.addActionListener(lis_BtnListener);
		btn_NewNetworkGame.addActionListener(lis_BtnListener);
		btn_Highscore.addActionListener(lis_BtnListener);
		btn_MapEditor.addActionListener(lis_BtnListener);

		// Hinzufügen der Buttons
		add(btn_Help);
		add(btn_Highscore);
		add(btn_NewLocalGame);
		add(btn_NewNetworkGame);
		add(btn_MapEditor);
	}

	public void openWebsite(String link) {
		if (!java.awt.Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported (fatal)");
			System.exit(1);
		}

		if (link.length() == 0) {
			System.out.println("Usage: OpenURI [URI [URI ... ]]");
			System.exit(0);
		}

		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
			System.err
					.println("Desktop doesn't support the browse action (fatal)");
			System.exit(1);
		}

		try {
			java.net.URI uri = new java.net.URI(link);
			desktop.browse(uri);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Help")) {
				openWebsite("http://www.heise.de");
			}
			if (e.getActionCommand().equals("btn_NewLocalGame")) {
				BaseFrame.getBaseFrame().setJPanel(new CreateLocalGameMenue());
			}
			if (e.getActionCommand().equals("btn_NewNetworkGame")) {
				BaseFrame.getBaseFrame().setJPanel(new NetworkSubMenue());
			}
			if (e.getActionCommand().equals("btn_Highscore")) {
				// openWebsite("http://golem.de");
				// BaseFrame.getBaseFrame().setJPanel(new GameStatsMenue());
			}
			if (e.getActionCommand().equals("btn_MapEditor")) {
				new MapEditorController();
			}

		}

	}

}
