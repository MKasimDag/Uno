package gui;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
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

        setTitle("UNO - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);
        setIconImage(new javax.swing.ImageIcon("images/design_images/table.png").getImage());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setBounds(0, 0, 1920, 100);
        getContentPane().add(topPanel);
        topPanel.setLayout(null);

        JLabel userInfo = new JLabel(username);
        userInfo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        userInfo.setForeground(Color.WHITE);
        userInfo.setBounds(10, 10, 560, 80);
        topPanel.add(userInfo);

        // Leaderboard Outline
        JPanel leaderboardOutline = new JPanel();
        leaderboardOutline.setBackground(Color.WHITE);
        leaderboardOutline.setBounds(10, 110, 630, 680);
        leaderboardOutline.setLayout(null);
        leaderboardOutline.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        getContentPane().add(leaderboardOutline);

        JLabel leaderboardTxt = new JLabel("Leaderboard");
        leaderboardTxt.setFont(new Font("Segoe UI", Font.BOLD, 22));
        leaderboardTxt.setForeground(new Color(33, 150, 243));
        leaderboardTxt.setBounds(10, 10, 610, 50);
        leaderboardTxt.setHorizontalAlignment(JLabel.CENTER);
        leaderboardOutline.add(leaderboardTxt);

        // Leaderboard Grid
        JPanel leaderboardGrid = new JPanel();
        leaderboardGrid.setLayout(null);
        leaderboardGrid.setBackground(new Color(248, 250, 252));

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
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            nameLabel.setForeground(new Color(66, 66, 66));
            leaderboardGrid.add(nameLabel);

            JLabel pointLabel = new JLabel(leaderboardList.get(numOfRows - i - 1).getValue().toString());
            pointLabel.setBounds(componentWidth, yOffset, componentWidth, labelHeight);
            pointLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            pointLabel.setForeground(new Color(66, 66, 66));
            leaderboardGrid.add(pointLabel);

            JRadioButton radioButton = new JRadioButton("");
            radioButton.setBounds(550, yOffset, 50, labelHeight);
            radioButton.setBackground(new Color(248, 250, 252));
            group.add(radioButton);
            leaderboardGrid.add(radioButton);

            radioButtonList.add(radioButton);

            yOffset += labelHeight;
        }

        JScrollPane scrollPane = new JScrollPane(leaderboardGrid);
        scrollPane.setBounds(10, 200, 620, 450); // Set bounds for the scroll pane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leaderboardOutline.add(scrollPane);

        detailedInfoButton = new JButton("View Statistics");
        detailedInfoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailedInfoButton.setBounds(10, 70, 610, 60);
        detailedInfoButton.setBackground(new Color(25, 118, 210));
        detailedInfoButton.setForeground(Color.WHITE);
        detailedInfoButton.setFocusPainted(false);
        detailedInfoButton.setMargin(new Insets(8, 12, 8, 12));
        leaderboardOutline.add(detailedInfoButton);
        detailedInfoButton.addActionListener(this);

        // Control Panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(650, 110, 880, 200);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        panel.setLayout(new GridLayout(2, 2, 20, 20));
        getContentPane().add(panel);

        createGameButton = new JButton("Create New Game");
        createGameButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createGameButton.setBackground(new Color(25, 118, 210));
        createGameButton.setForeground(Color.WHITE);
        createGameButton.setFocusPainted(false);
        createGameButton.setMargin(new Insets(8, 12, 8, 12));
        createGameButton.addActionListener(this);
        panel.add(createGameButton);

        loadGameButton = new JButton("Load Game");
        loadGameButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loadGameButton.setBackground(new Color(46, 125, 50));
        loadGameButton.setForeground(Color.WHITE);
        loadGameButton.setFocusPainted(false);
        loadGameButton.setMargin(new Insets(8, 12, 8, 12));
        loadGameButton.addActionListener(this);
        panel.add(loadGameButton);

        signOutButton = new JButton("Sign Out");
        signOutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signOutButton.setBackground(new Color(69, 90, 100));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setFocusPainted(false);
        signOutButton.setMargin(new Insets(8, 12, 8, 12));
        signOutButton.addActionListener(this);
        panel.add(signOutButton);

        delAccButton = new JButton("Delete User");
        delAccButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        delAccButton.setBackground(new Color(211, 47, 47));
        delAccButton.setForeground(Color.WHITE);
        delAccButton.setFocusPainted(false);
        delAccButton.setMargin(new Insets(8, 12, 8, 12));
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
