package menue;

import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class CreateLocalGameMenue extends JPanel {

	private Button btn_Back;
	private Button btn_StartGame;
	private TextField txt_NamePlayer1;
	private TextField txt_NamePlayer2;
	private TextField txt_NamePlayer3;
	private TextField txt_NamePlayer4;
	private Label lbl_LabelNamePlayer1;
	private Label lbl_LabelNamePlayer2;
	private Label lbl_LabelNamePlayer3;
	private Label lbl_LabelNamePlayer4;

	private ButtonListener lis_BtnListener = new ButtonListener();

	public CreateLocalGameMenue() {
		this.setSize(800, 600);

		// Buttons erstellen und platzieren
		btn_Back = new Button("Zurück");
		btn_StartGame = new Button("Starte Spiel");
		btn_Back.setLocation(10, 550);
		btn_StartGame.setLocation(700, 550);

		// TextFelder erstellen und platzieren
		txt_NamePlayer1 = new TextField();
		txt_NamePlayer2 = new TextField();
		txt_NamePlayer3 = new TextField();
		txt_NamePlayer4 = new TextField();
		txt_NamePlayer1.setLocation(60, 50);
		txt_NamePlayer2.setLocation(60, 100);
		txt_NamePlayer3.setLocation(60, 150);
		txt_NamePlayer4.setLocation(60, 200);

		// TextLabels erstellen und platzieren
		lbl_LabelNamePlayer1 = new Label("Name :");
		lbl_LabelNamePlayer2 = new Label("Name :");
		lbl_LabelNamePlayer3 = new Label("Name :");
		lbl_LabelNamePlayer4 = new Label("Name :");
		lbl_LabelNamePlayer1.setLocation(0, 50);
		lbl_LabelNamePlayer2.setLocation(0, 100);
		lbl_LabelNamePlayer3.setLocation(0, 150);
		lbl_LabelNamePlayer4.setLocation(0, 200);

		// Actioncommand
		btn_Back.setActionCommand("btn_Back");
		btn_StartGame.setActionCommand("btn_StartGame");

		// Actionlistener hinzufügen
		btn_Back.addActionListener(lis_BtnListener);
		btn_StartGame.addActionListener(lis_BtnListener);

		// Hinzufügen der Buttons,Textfelder, etc.
		add(btn_Back);
		add(btn_StartGame);
		add(txt_NamePlayer1);
		add(txt_NamePlayer2);
		add(txt_NamePlayer3);
		add(txt_NamePlayer4);

	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("btn_Back")) {
				BaseFrame.getBaseFrame().setJPanel(new MainMenue());
			}
			if (e.getActionCommand().equals("btn_StartGame")) {

			}

		}

	}
}
