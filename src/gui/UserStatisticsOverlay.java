package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import dataProcessing.Login;

import javax.swing.JLabel;
import javax.swing.JButton;

public class UserStatisticsOverlay extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HashMap<String, String> userData;
	private String userName;
	private JLabel avgScore;
	private JLabel winLossRatio;
	private JButton goBack;

	
	public UserStatisticsOverlay(String path, String userName) {
		this.userData = Login.getAUserData(path);
		this.userName = userName;
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 450, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 416, 430);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel Username = new JLabel(userName);
		Username.setBounds(10, 10, 396, 50);
		panel.add(Username);
		
		JLabel totalScore = new JLabel("Total score:" + userData.get("Total score"));
		totalScore.setBounds(10, 70, 396, 50);
		panel.add(totalScore);
		
		JLabel gameNumber = new JLabel("Num of games: " + userData.get("Num of games"));
		gameNumber.setBounds(10, 130, 396, 50);
		panel.add(gameNumber);
		
		JLabel wins = new JLabel("Num of wins: " + userData.get("Num of wins"));
		wins.setBounds(10, 190, 396, 50);
		panel.add(wins);
		
		JLabel loses = new JLabel("Num of loses: " + userData.get("Num of loses"));
		loses.setBounds(10, 250, 396, 50);
		panel.add(loses);
		
		//System.out.println(!userData.get("Num of wins").equals("0") && !userData.get("Num of loses").equals("0") && !userData.get("Num of games").equals(0));
		
		
		if (!userData.get("Num of wins").equals("0") && !userData.get("Num of loses").equals("0") && !userData.get("Num of games").equals(0)) {
			avgScore = new JLabel("Average score per game played: %" + 100*(double)((double)Integer.parseInt(userData.get("Num of wins")) / (double)Integer.parseInt(userData.get("Num of games"))));
			winLossRatio = new JLabel("The win/loss ratio: " + (double)((double)Integer.parseInt(userData.get("Num of wins")) / (double)Integer.parseInt(userData.get("Num of loses"))));
		}
		else {
			avgScore = new JLabel("Average score per game played: 0");
			winLossRatio = new JLabel("The win/loss ratio: 0");
		}

		avgScore.setBounds(10, 310, 396, 50);
		panel.add(avgScore);
		
		
		winLossRatio.setBounds(10, 370, 396, 50);
		panel.add(winLossRatio);
		
		goBack = new JButton("Go Back");
		goBack.setBounds(284, 370, 122, 50);
		goBack.addActionListener(this);
		panel.add(goBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 450, 416, 103);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		//this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == goBack) {
			dispose();
		}
		
	}
}
