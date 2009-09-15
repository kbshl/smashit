package menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.Player;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class GameStatsMenue extends GamePanel {

	private GameButton btn_Next = new GameButton(680, 565, "Weiter");
	private GameButton btn_Eintragen = new GameButton(570, 565, "Eintragen");
	private Vector<Player> players;

	private ButtonListener lis_BtnListener = new ButtonListener();

	public GameStatsMenue(Vector<Player> p) {
		super("gamepanel_mainmenue.jpg");
		players = p;

		btn_Next.setActionCommand("btn_Next");
		btn_Eintragen.setActionCommand("btn_Eintragen");
		btn_Next.addActionListener(lis_BtnListener);
		btn_Eintragen.addActionListener(lis_BtnListener);
		add(btn_Next);
		add(btn_Eintragen);

	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Next")) {
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
			}
			if (e.getActionCommand().equals("btn_Eintragen")) {

			}

		}

	}

}
