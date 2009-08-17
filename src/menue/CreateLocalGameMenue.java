package menue;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateLocalGameMenue extends JPanel {

	private JButton btn_Back;
	private JButton btn_StartGame;
	private TextField txt_NamePlayer1;
	private TextField txt_NamePlayer2;
	private TextField txt_NamePlayer3;
	private TextField txt_NamePlayer4;
	private JLabel lbl_LabelNamePlayer1;
	private JLabel lbl_LabelNamePlayer2;
	private JLabel lbl_LabelNamePlayer3;
	private JLabel lbl_LabelNamePlayer4;
	
	private ButtonListener lis_BtnListener = new ButtonListener();

	public CreateLocalGameMenue() {
		this.setSize(800, 600);
		this.setLayout(null);
		
		// Buttons erstellen und platzieren
		btn_Back = new JButton("Zurück");
		btn_StartGame = new JButton("Starte Spiel");
		btn_Back.setBounds(10, 500, 90, 30);
		btn_StartGame.setBounds(650, 500, 90, 30);

		// TextFelder erstellen und platzieren
		txt_NamePlayer1 = new TextField();
		txt_NamePlayer2 = new TextField();
		txt_NamePlayer3 = new TextField();
		txt_NamePlayer4 = new TextField();

		txt_NamePlayer1.setBounds(150, 40, 100, 30);
		txt_NamePlayer2.setBounds(150, 80, 100, 30);
		txt_NamePlayer3.setBounds(150, 120, 100, 30);
		txt_NamePlayer4.setBounds(150, 160, 100, 30);

		// TextLabels erstellen und platzieren
		lbl_LabelNamePlayer1 = new JLabel("Name :");
		lbl_LabelNamePlayer2 = new JLabel("Name :");
		lbl_LabelNamePlayer3 = new JLabel("Name :");
		lbl_LabelNamePlayer4 = new JLabel("Name :");
		
		lbl_LabelNamePlayer1.setBounds(10, 40, 90, 30);
		lbl_LabelNamePlayer2.setBounds(10, 80, 90, 30);
		lbl_LabelNamePlayer3.setBounds(10, 120, 90, 30);
		lbl_LabelNamePlayer4.setBounds(10, 160, 90, 30);

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
		add(lbl_LabelNamePlayer1);
		add(lbl_LabelNamePlayer2);
		add(lbl_LabelNamePlayer3);
		add(lbl_LabelNamePlayer4);

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
