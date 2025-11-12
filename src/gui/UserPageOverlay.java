package gui;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import dataProcessing.Login;

public class UserPageOverlay extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String path;
    private int numOfRows;
    private int labelHeight = 40;
    private List<Entry<String, Integer>> leaderboardList;
    private List<JRadioButton> radioButtonList = new ArrayList<>();
    private JButton detailedInfoButton;
    private JButton createGameButton;
    private JButton loadGameButton;
    private JButton signOutButton;
    private JButton delAccButton;

    /**
     * Create the frame.
     */
    public UserPageOverlay(String username, String password) {
        this.username = username;
        this.password = password;
        this.path = "data/users/" + username;
        this.leaderboardList = Login.getLeaderboardData();
        this.numOfRows = leaderboardList.size();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 51, 51));
        topPanel.setBounds(0, 0, 1920, 100);
        getContentPane().add(topPanel);
        topPanel.setLayout(null);

        JLabel userInfo = new JLabel(username);
        userInfo.setFont(new Font("Tahoma", Font.BOLD, 30));
        userInfo.setForeground(Color.WHITE);
        userInfo.setBounds(10, 10, 560, 80);
        topPanel.add(userInfo);

        // Leaderboard Outline
        JPanel leaderboardOutline = new JPanel();
        leaderboardOutline.setBackground(new Color(51, 153, 255));
        leaderboardOutline.setBounds(10, 110, 630, 680);
        leaderboardOutline.setLayout(null);
        getContentPane().add(leaderboardOutline);

        JLabel leaderboardTxt = new JLabel("LeaderBoard");
        leaderboardTxt.setFont(new Font("Tahoma", Font.BOLD, 30));
        leaderboardTxt.setForeground(Color.WHITE);
        leaderboardTxt.setBounds(10, 10, 610, 50);
        leaderboardTxt.setHorizontalAlignment(JLabel.CENTER);
        leaderboardOutline.add(leaderboardTxt);

        // Leaderboard Grid
        JPanel leaderboardGrid = new JPanel();
        leaderboardGrid.setLayout(null);
        leaderboardGrid.setBackground(new Color(204, 229, 255));

        // Calculate the preferred size of the leaderboardGrid based on the content
        int gridWidth = 620; // Width of the scrollable area
        int gridHeight = numOfRows * labelHeight; // Height based on the number of rows
        leaderboardGrid.setPreferredSize(new Dimension(gridWidth, gridHeight));

        ButtonGroup group = new ButtonGroup();

        int yOffset = 0; // Initial height offset
        int componentWidth = 100; // Component width

        for (int i = 0; i < numOfRows; i++) {
            JLabel nameLabel = new JLabel(leaderboardList.get(numOfRows - i - 1).getKey());
            nameLabel.setBounds(10, yOffset, componentWidth, labelHeight);
            nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
            nameLabel.setForeground(new Color(51, 51, 51));
            leaderboardGrid.add(nameLabel);

            JLabel pointLabel = new JLabel(leaderboardList.get(numOfRows - i - 1).getValue().toString());
            pointLabel.setBounds(componentWidth, yOffset, componentWidth, labelHeight);
            pointLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
            pointLabel.setForeground(new Color(51, 51, 51));
            leaderboardGrid.add(pointLabel);

            JRadioButton radioButton = new JRadioButton("");
            radioButton.setBounds(550, yOffset, 50, labelHeight);
            radioButton.setBackground(new Color(204, 229, 255));
            group.add(radioButton);
            leaderboardGrid.add(radioButton);

            radioButtonList.add(radioButton);

            yOffset += labelHeight;
        }

        JScrollPane scrollPane = new JScrollPane(leaderboardGrid);
        scrollPane.setBounds(10, 200, 620, 450); // Set bounds for the scroll pane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leaderboardOutline.add(scrollPane);

        detailedInfoButton = new JButton("See Statistic Detailed");
        detailedInfoButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        detailedInfoButton.setBounds(10, 70, 610, 60);
        detailedInfoButton.setBackground(new Color(0, 153, 76));
        detailedInfoButton.setForeground(Color.WHITE);
        leaderboardOutline.add(detailedInfoButton);
        detailedInfoButton.addActionListener(this);

        // Control Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 204, 0));
        panel.setBounds(650, 110, 880, 154);
        getContentPane().add(panel);
        panel.setLayout(null);

        createGameButton = new JButton("Create New Game");
        createGameButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        createGameButton.setBounds(25, 30, 155, 95);
        createGameButton.setBackground(new Color(0, 153, 76));
        createGameButton.setForeground(Color.WHITE);
        createGameButton.addActionListener(this);
        panel.add(createGameButton);

        loadGameButton = new JButton("Load Game");
        loadGameButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        loadGameButton.setBounds(210, 30, 155, 95);
        loadGameButton.setBackground(new Color(0, 153, 76));
        loadGameButton.setForeground(Color.WHITE);
        loadGameButton.addActionListener(this);
        panel.add(loadGameButton);

        signOutButton = new JButton("Sign Out");
        signOutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        signOutButton.setBounds(507, 30, 155, 95);
        signOutButton.setBackground(new Color(0, 153, 76));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.addActionListener(this);
        panel.add(signOutButton);

        delAccButton = new JButton("Delete User");
        delAccButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        delAccButton.setBounds(697, 30, 155, 95);
        delAccButton.setBackground(new Color(255, 51, 51));
        delAccButton.setForeground(Color.WHITE);
        delAccButton.addActionListener(this);
        panel.add(delAccButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == detailedInfoButton) {
            int radioButtonId = -1;
            for (int i = 0; i < radioButtonList.size(); i++) {
                if (radioButtonList.get(i).isSelected()) radioButtonId = i;
            }
            if (radioButtonId != -1) {
                String userName = leaderboardList.get(leaderboardList.size() - radioButtonId - 1).toString().split("=")[0];
                UserStatisticsOverlay userStats = new UserStatisticsOverlay(String.format("data/users/%s.txt", userName), userName);
                userStats.setVisible(true);
            }
        }
        if (e.getSource() == createGameButton) {
            SetNewGameSettings setting = new SetNewGameSettings(username, this, password);
        }
        if (e.getSource() == loadGameButton) {
        	LoadGamePage setting = new LoadGamePage(username, this, password);
        }
        if (e.getSource() == signOutButton) {
            this.dispose();
            new LoginOverlay();
        }
        if (e.getSource() == delAccButton) {
            // Delete user functionality
        }
    }
}