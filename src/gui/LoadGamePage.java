package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dataProcessing.Games;
import playingCards.Cards;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

public class LoadGamePage extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String[] games;
	private JList list;
	private JButton generateGameButton;
	private JLabel infoTxt;
	private String username;
	private UserPageOverlay userGui;
	private String password;

	public LoadGamePage(String username, UserPageOverlay userGui, String password) {
		this.username = username;
		this.userGui = userGui;
		this.games = Games.gameList(String.format("data/users/%s.txt", username));
		this.password = password;
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 416, 543);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		list = new JList(games);
		list.setBounds(216, 23, 154, 173);
		panel.add(list);
		
		JLabel plyrNumTxt = new JLabel("Please Select Game");
		plyrNumTxt.setBounds(22, 10, 190, 40);
		panel.add(plyrNumTxt);
		
		JLabel infoTxt = new JLabel("");
		infoTxt.setBounds(26, 305, 358, 40);
		panel.add(infoTxt);
		
		generateGameButton = new JButton("Load Game");
		generateGameButton.setBounds(126, 355, 154, 40);
		generateGameButton.addActionListener(this);
		panel.add(generateGameButton);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == generateGameButton) {
			dispose();
			userGui.dispose();
			TreeMap<String, LinkedList<Cards>> decks = Games.loadGame("game_" + Games.getGameId());
			new GameArea(decks, username, "game_" + Games.getGameId(), password);
		}
		
	}
	
	
}
