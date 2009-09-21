package network;


import game.PlayerStats;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JLabel;

import menue.BaseFrame;
import menue.GameButton;
import menue.GamePanel;
import menue.MainMenue;

public class NetGameStatsMenue extends GamePanel {

	private GameButton btn_Next = new GameButton(680, 565, "Weiter");
	private GameButton btn_Eintragen = new GameButton(570, 565, "Eintragen");
	private Vector<PlayerStats> players = new Vector<PlayerStats>();

	private ButtonListener lis_BtnListener = new ButtonListener();

	public NetGameStatsMenue(Vector<FullPlayer> p) {
		super("gamepanel_gamestatsmenue.jpg");
		for(FullPlayer player : p){
			players.add(player.getPlayerStats());
		}
		Collections.sort(players);
		
		for(int i = 0; i < players.size(); ++i){
			JLabel label1 = new JLabel(players.get(i).getName());
			JLabel label2 = new JLabel(players.get(i).getKills()+"");
			JLabel label3 = new JLabel(players.get(i).getLifes()+"");
			JLabel label4 = new JLabel(players.get(i).getPoints()+"");
			
			label1.setBounds(260, 200+(i*40), 150, 30);
			label2.setBounds(400, 200+(i*40), 150, 30);
			label3.setBounds(460, 200+(i*40), 150, 30);
			label4.setBounds(520, 200+(i*40), 150, 30);
			
			add(label1);
			add(label2);
			add(label3);
			add(label4);
		}
		
		JLabel name = new JLabel("Name");
		JLabel kills = new JLabel("Kills");
		JLabel leben = new JLabel("Leben");
		JLabel punkte = new JLabel("Punkte");
		
		name.setBounds(260, 160, 150, 30);
		kills.setBounds(400, 160, 150, 30);
		leben.setBounds(460, 160, 150, 30);
		punkte.setBounds(520, 160, 150, 30);

		

		
		btn_Next.setActionCommand("btn_Next");
		btn_Eintragen.setActionCommand("btn_Eintragen");
		btn_Next.addActionListener(lis_BtnListener);
		btn_Eintragen.addActionListener(lis_BtnListener);
		
			
		add(name);
		add(kills);
		add(leben);
		add(punkte);
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
