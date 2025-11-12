package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataProcessing.Games;
import playingCards.Cards;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SetNewGameSettings extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static String[] numList = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
	private JTextField textField;
	private JList list;
	private JButton generateGameButton;
	private JLabel infoTxt;
	private String username;
	private UserPageOverlay userGui;
	private String password;

	public SetNewGameSettings(String username, UserPageOverlay userGui, String password) {
		this.username = username;
		this.userGui = userGui;
		this.password = password;
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 416, 543);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		list = new JList(numList);
		list.setBounds(217, 102, 44, 173);
		panel.add(list);
		
		JLabel plyrNumTxt = new JLabel("Please Select Player Number");
		plyrNumTxt.setBounds(26, 89, 190, 40);
		panel.add(plyrNumTxt);
		
		JLabel gameNameTxt = new JLabel("Enter A Session Name");
		gameNameTxt.setBounds(26, 21, 154, 40);
		panel.add(gameNameTxt);
		
		textField = new JTextField();
		textField.setBounds(194, 22, 190, 40);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel infoTxt = new JLabel("");
		infoTxt.setBounds(26, 305, 358, 40);
		panel.add(infoTxt);
		
		generateGameButton = new JButton("Create New Game");
		generateGameButton.setBounds(126, 355, 154, 40);
		generateGameButton.addActionListener(this);
		panel.add(generateGameButton);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == generateGameButton) {
			int playerNumber = Integer.parseInt((String) list.getSelectedValue());
			String sessionName = textField.getText();
			
			if (sessionName.contains(":")) infoTxt.setText("Please do not use space or punctiation marks.");
			else {
				Games.createGame(username, playerNumber, sessionName);
				dispose();
				userGui.dispose();
				TreeMap<String, LinkedList<Cards>> decks = Games.loadGame("game_" + Games.getGameId());
				new GameArea(decks, username, "game_" + Games.getGameId(), password);
			}
			
		}
		
	}
	
	
}
