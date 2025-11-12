package gui;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import playingCards.ActionCard;
import playingCards.Cards;
import playingCards.MakeDecision;
import playingCards.WildCard;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import dataProcessing.Games;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class GameArea extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private TreeMap<String, LinkedList<Cards>> decks;
    private String username;
    private JLabel midCardsImage;
    private JButton throwCardButton;
    private JPanel panel_5;
    private ButtonGroup group;
    private List<JRadioButton> radioButtonList = new ArrayList<>();
    private JButton drawCardButton;
    private JButton stopGameButton;
    private JButton continueButton;
    private JButton saveGameButton;
    private JPanel panel_10;

    private JPanel botsRemainders;
    private ArrayList<JLabel> pointLabelList = new ArrayList<>();
    private JPanel panel_7;
    private JLabel infos;
    private JLabel infos_1;
    private boolean gameWay = true;
    int count = 0;
    private ArrayList<Component> botPanels = new ArrayList<>();

    private JRadioButton rdbtn_1;
    private JRadioButton rdbtn_2;
    private JRadioButton rdbtn_3;
    private JRadioButton rdbtn;
    private ButtonGroup colorGroup;
    private JLabel gameway_arrow;
    private JPanel panel_8;
    private HashMap<String, Boolean> unoStatus = new HashMap<>();

    private boolean myTurn = false;
    private boolean ascendingOrder = true;
    private int botId;
    private boolean isPaused = false;
    private final int totalBotNum;
    private final int totalBotNum2;

    private JLabel remainedCards;
    private JLabel colorOfMidCard;

    private int drawNumForUser = 0;
	private String gameLog;

    public GameArea(TreeMap<String, LinkedList<Cards>> decks, String username, String gameLog, String password) {
        this.decks = decks;
        this.username = username;
        this.totalBotNum = decks.size() - 2;
        this.totalBotNum2 = totalBotNum - 1;
        this.botId = ascendingOrder ? 1 : totalBotNum2;
        this.gameLog = gameLog;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        //********** Bottom Panel **************************
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(10, 608, 1520, 227);
        getContentPane().add(panel_2);
        panel_2.setLayout(null);

        panel_5 = new JPanel();
        panel_5.setBounds(0, 0, 1249, 227);
        panel_2.add(panel_5);
        panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

        JPanel panel_6 = new JPanel();
        panel_6.setBounds(1248, 0, 272, 227);
        panel_2.add(panel_6);
        panel_6.setLayout(null);

        throwCardButton = new JButton("Throw Card");
        throwCardButton.setBounds(10, 10, 253, 53);
        throwCardButton.addActionListener(this);
        panel_6.add(throwCardButton);

        drawCardButton = new JButton("Draw Card");
        drawCardButton.setBounds(10, 73, 120, 53);
        drawCardButton.addActionListener(this);
        panel_6.add(drawCardButton);

        stopGameButton = new JButton("Pause / Continue");
        stopGameButton.setBounds(10, 136, 253, 53);
        stopGameButton.addActionListener(this);
        panel_6.add(stopGameButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(140, 73, 122, 53);
        continueButton.addActionListener(this);
        continueButton.setEnabled(false);
        panel_6.add(continueButton);

        group = new ButtonGroup();
        //***********************************************************
        System.out.println(decks.toString());
        //********** Player's Cards *********************************
        for (int i = 0; i < decks.get(username).size(); i++) {
            Cards aCard = decks.get(username).get(i);

            JPanel panel_3 = new JPanel();
            panel_3.setBounds(10 + 106 * i, 10, 106, 172);
            panel_5.add(panel_3);
            panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

            JLabel imageLabel = new JLabel("");
            imageLabel.setBounds(10, 10, 84, 129);
            panel_3.add(imageLabel);

            String imagePath = aCard.getPath();

            BufferedImage originalImage = loadImage(imagePath);
            BufferedImage resizedImage = resizeImage(originalImage, 84, 129);

            imageLabel.setIcon(new ImageIcon(resizedImage));

            JRadioButton rdbtnNewRadioButton = new JRadioButton();
            rdbtnNewRadioButton.setBounds(40, 145, 21, 21);
            group.add(rdbtnNewRadioButton);
            panel_3.add(rdbtnNewRadioButton);

            radioButtonList.add(rdbtnNewRadioButton);
        }
        //****************************************************************

        //********** Player's Status *********************************
        JPanel panel_4 = new JPanel();
        panel_4.setBounds(685, 540, 102, 65);
        panel_4.setOpaque(false);
        getContentPane().add(panel_4);
        panel_4.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel(username);
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3.setBounds(10, 31, 80, 39);
        panel_4.add(lblNewLabel_3);

        panel_10 = new JPanel();
        panel_10.setBounds(35, 10, 30, 30);
        panel_10.setBackground(Color.GREEN);
        panel_4.add(panel_10);
        //**********************************************************

        //********** Sitting Bots in a Circle **************************
        int n = totalBotNum;

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> baseArray = new ArrayList<Integer>();
            double alfa = -1 * Math.toRadians(360 / n);
            if (i == 0) {
                int xcord = 0;
                int ycord = 275;

                baseArray.add(xcord);
                baseArray.add(ycord);

                res.add(baseArray);
            } else {
                int xcord = res.get(res.size() - 1).get(0);
                int ycord = res.get(res.size() - 1).get(1);

                baseArray.add((int) (xcord * Math.cos(alfa) - ycord * Math.sin(alfa)));
                baseArray.add((int) (xcord * Math.sin(alfa) + ycord * Math.cos(alfa)));

                res.add(baseArray);
            }
        }
        Color color = new Color(255, 255, 255);
        int i = 0;
        for (String key : decks.keySet()) {
            if (i != 0 && i < n) {

                ArrayList<Integer> cords = res.get(i);

                int x = cords.get(0) + 770 - 80;
                int y = cords.get(1) + 350 - 80;

                CardPanel panel = new CardPanel();
                panel.setBounds(x, y, 160, 139);
                panel.setOpaque(false);
                getContentPane().add(panel);
                panel.setLayout(null);

                botPanels.add(panel);

                JLabel lblNewLabel = new JLabel(key);
                lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
                lblNewLabel.setBounds(73, 10, 60, 55);
                lblNewLabel.setForeground(color);

                panel.add(lblNewLabel);

                JPanel panel_1 = new JPanel();
                panel_1.setBounds(20, 20, 30, 30);
                panel_1.setBackground(Color.red);
                panel.add(panel_1);
            }

            i++;
        }
        //********************************************************************

        //************** Game Table ******************************************
        BufferedImage backgroundImg = loadImage("images/design_images/background.png");
        BufferedImage resizedBackground = resizeImage(backgroundImg, 84, 129);

        JLabel backgroundImage = new JLabel(new ImageIcon(resizedBackground));
        backgroundImage.setBounds(750, 240, 84, 129);
        getContentPane().add(backgroundImage);

        midCardsImage = new JLabel();
        midCardsImage.setBounds(636, 240, 84, 129);
        getContentPane().add(midCardsImage);

        gameway_arrow = new JLabel();
        gameway_arrow.setBounds(1026, 441, 84, 129);
        getContentPane().add(gameway_arrow);

        remainedCards = new JLabel();
        remainedCards.setForeground(Color.WHITE);
        remainedCards.setText("Remained");
        remainedCards.setBounds(750, 379, 84, 30);
        getContentPane().add(remainedCards);

        colorOfMidCard = new JLabel();
        colorOfMidCard.setForeground(Color.WHITE);
        colorOfMidCard.setText("Color of Mid");
        colorOfMidCard.setBounds(646, 379, 84, 30);
        getContentPane().add(colorOfMidCard);

        BufferedImage originalTable = loadImage("images/design_images/table.png");
        BufferedImage resizedTable = resizeImage(originalTable, 550, 550);

        JLabel background = new JLabel(new ImageIcon(resizedTable));
        background.setBounds(445, 30, 571, 540);
        getContentPane().add(background);

        botsRemainders = new JPanel();
        botsRemainders.setLayout(null);
        botsRemainders.setPreferredSize(new Dimension(620, 0));
        botsRemainders.setBounds(10, 10, 320, 420);
        getContentPane().add(botsRemainders);

        panel_7 = new JPanel();
        panel_7.setBounds(10, 440, 320, 159);
        getContentPane().add(panel_7);
        panel_7.setLayout(null);

        infos = new JLabel("");
        infos.setBounds(10, 10, 300, 59);
        panel_7.add(infos);

        infos_1 = new JLabel("");
        infos_1.setBounds(10, 90, 300, 59);
        panel_7.add(infos_1);

        displayMidCard();
        //********************************************************************

        //*********** Color Select Panel **************************
        JPanel colorSelectPanel = new JPanel();
        colorSelectPanel.setBounds(1260, 408, 270, 129);
        getContentPane().add(colorSelectPanel);
        colorSelectPanel.setLayout(null);

        rdbtn = new JRadioButton("");
        rdbtn.setVerticalAlignment(SwingConstants.CENTER);
        rdbtn.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtn.setBounds(10, 66, 60, 57);
        rdbtn.setBackground(Color.RED);
        rdbtn.setActionCommand("red");
        colorSelectPanel.add(rdbtn);

        rdbtn_1 = new JRadioButton("");
        rdbtn_1.setVerticalAlignment(SwingConstants.CENTER);
        rdbtn_1.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtn_1.setBounds(196, 66, 60, 57);
        rdbtn_1.setBackground(Color.YELLOW);
        rdbtn_1.setActionCommand("yellow");
        colorSelectPanel.add(rdbtn_1);

        rdbtn_2 = new JRadioButton("");
        rdbtn_2.setVerticalAlignment(SwingConstants.CENTER);
        rdbtn_2.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtn_2.setBounds(72, 66, 60, 57);
        rdbtn_2.setBackground(Color.GREEN);
        rdbtn_2.setActionCommand("green");
        colorSelectPanel.add(rdbtn_2);

        rdbtn_3 = new JRadioButton("");
        rdbtn_3.setVerticalAlignment(SwingConstants.CENTER);
        rdbtn_3.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtn_3.setBounds(134, 66, 60, 57);
        rdbtn_3.setBackground(Color.BLUE);
        rdbtn_3.setActionCommand("blue");
        colorSelectPanel.add(rdbtn_3);

        colorGroup = new ButtonGroup();
        colorGroup.add(rdbtn);
        colorGroup.add(rdbtn_1);
        colorGroup.add(rdbtn_2);
        colorGroup.add(rdbtn_3);

        JLabel selectColorTxt = new JLabel("Select Color For Wild Card");
        selectColorTxt.setFont(new Font("Tahoma", Font.BOLD, 16));
        selectColorTxt.setBounds(10, 10, 246, 50);
        selectColorTxt.setHorizontalAlignment(SwingConstants.CENTER);
        colorSelectPanel.add(selectColorTxt);

        panel_8 = new JPanel();
        panel_8.setBounds(1260, 540, 270, 58);
        getContentPane().add(panel_8);
        panel_8.setLayout(null);

        //********************************************************************

        JButton unoButton = new JButton("UNO!!");
        unoButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        unoButton.setBounds(10, 0, 253, 53);
        //unoButton.addActionListener(this);
        panel_8.add(unoButton);
        
        JPanel panel = new JPanel();
        panel.setBounds(1260, 335, 270, 65);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        saveGameButton = new JButton("Save Game");
        saveGameButton.setBounds(10, 10, 253, 53);
        saveGameButton.addActionListener(this);
        panel.add(saveGameButton);

        //**********  Bots Remaining Cards Lister ****************************
        int yOffset = 0; 
        int componentWidth = 100;
        int labelHeight = 40;
        int i1 = 0;
        for (String key : decks.keySet()) {
            if (i1 < totalBotNum) {
                JLabel nameLabel = new JLabel(key);
                nameLabel.setBounds(10, yOffset, componentWidth, labelHeight);
                botsRemainders.add(nameLabel);

                JLabel pointLabel = new JLabel(String.valueOf(decks.get(key).size()));
                pointLabel.setBounds(componentWidth, yOffset, componentWidth, labelHeight);
                botsRemainders.add(pointLabel);

                pointLabelList.add(pointLabel);

                yOffset += labelHeight;
                unoStatus.put(key, false);
            }
            i1++;
        }
        //********************************************************************

        this.setVisible(true);

    }

    private void simulateBotTurns() {
        throwCardButton.setEnabled(false);
        saveGameButton.setEnabled(false);
        drawCardButton.setEnabled(false);
        continueButton.setEnabled(false);
        myTurn = false;
        panel_10.setBackground(Color.RED);

        botId = ascendingOrder ? 1 : totalBotNum2;
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (!myTurn && botId >= 1 && botId <= totalBotNum2) {
                    int step = ascendingOrder ? 1 : -1;
                    publish(botId);

                    Cards midCard = decks.get("throwed").getLast();
                    String currentBot = String.format("bot%d", botId);

                    // Check if the bot needs to draw cards due to plus2 or wildplus4
                    if (drawNumForUser > 0) {
                        botDrawCard(drawNumForUser, currentBot);
                        logAction(currentBot + " draws " + drawNumForUser + " cards as a penalty");
                        drawNumForUser = 0;
                    } else {
                        Cards botsCard = MakeDecision.playCard(decks, currentBot, midCard);

                        if (botsCard != null) {
                            decks.get(currentBot).remove(botsCard);
                            decks.get("throwed").add(botsCard);
                            logAction(currentBot + " plays " + botsCard.getPath());

                            if (botsCard instanceof ActionCard) {
                                if (((ActionCard) botsCard).getType().equals("plus2")) {
                                    drawNumForUser = 2;
                                    logAction(currentBot + " plays +2 card");
                                } else if (((ActionCard) botsCard).getType().equals("skip")) {
                                    botId += step; // Skip next bot
                                    logAction(currentBot + " plays skip card");
                                    if (botId < 1 || botId > totalBotNum2) {
                                        myTurn = true; // Ensure we don't skip into invalid botId range
                                        break;
                                    }
                                    botId += step; // Move to the next bot
                                } else if (((ActionCard) botsCard).getType().equals("reverse")) {
                                    changeDirection();
                                    logAction(currentBot + " plays reverse card");
                                    step = -step; // Change direction
                                }
                            }
                            if (botsCard instanceof WildCard) {
                                if (((WildCard) botsCard).getType().equals("change_color_plus4")) {
                                    drawNumForUser = 4;
                                    logAction(currentBot + " plays wild +4 card");
                                }
                            }
                        }
                    }

                    botId += step;
                    checkForWinner();
                    if (isPaused) {
                        while (isPaused) {
                            Thread.sleep(100);
                        }
                    }
                    Thread.sleep(2000);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                String currentBot = String.format("bot%d", chunks.get(chunks.size() - 1));

                updateLeaderBoard();
                displayMidCard();
                changePanelColor(chunks.get(chunks.size() - 1) - 1);
                infos.setText(currentBot);
                infos_1.setText(decks.get("throwed").getLast().getPath());
            }

            @Override
            protected void done() {
                // Ensure buttons are re-enabled correctly
                if (drawNumForUser == 0) {
                    panel_10.setBackground(Color.GREEN);
                    drawCardButton.setEnabled(true);
                    throwCardButton.setEnabled(true);
                    saveGameButton.setEnabled(true);
                    continueButton.setEnabled(true);
                }

                if (!checkMidPlayable()) {
                    Cards midCard = decks.get("throwed").getLast();

                    if (midCard instanceof ActionCard) {
                        ((ActionCard) midCard).setDoesAffectedGame(true);
                        if (((ActionCard) midCard).getType().equals("plus2")) {
                            drawNumForUser = 2;
                        } else if (((ActionCard) midCard).getType().equals("skip")) {
                            simulateBotTurns();
                        } else if (((ActionCard) midCard).getType().equals("reverse")) {
                            changeDirection();
                        }
                    }

                    if (midCard instanceof WildCard) {
                        ((WildCard) midCard).setDoesAffectedGame(true);
                        if (((WildCard) midCard).getType().equals("change_color_plus4")) {
                            drawNumForUser = 4;
                        }
                    }
                } else {
                    panel_10.setBackground(Color.GREEN);
                    drawCardButton.setEnabled(true);
                    throwCardButton.setEnabled(true);
                    saveGameButton.setEnabled(true);
                    continueButton.setEnabled(true);
                }
                resetAllPanels();
                checkForWinner();
            }
        };
        worker.execute();
    }

    private void changeDirection() {
        ascendingOrder = !ascendingOrder;
        gameWay = !gameWay;

        String arrowPath = gameWay ? "images/design_images/cw.png" : "images/design_images/ccw.png";
        BufferedImage img2 = loadImage(arrowPath);
        BufferedImage resizedImg2 = resizeImage(img2, 84, 129);
        gameway_arrow.setIcon(new ImageIcon(resizedImg2));
    }

    private void checkForWinner() {
        String winner = null;

        for (String key : decks.keySet()) {
            if (decks.get(key).size() == 0) {
                winner = key;
                break;
            }
        }

        if (winner != null) {
            dispose();
            Games.setPoints(username, winner, decks);
            displayWinner(winner);
        }
    }

    private void displayWinner(String winner) {
        logAction("Winner: " + winner);
        new WinnerWindow(winner);
        dispose();
    }

    private int throwFromUserToMid() {
        // 0: Card cannot be played
        // 1: Card is played successfully
        // 2: Penalty card, next player needs to draw cards
        int res = 0;

        for (Component component : panel_5.getComponents()) {
            if (component instanceof JPanel) {
                JPanel cardPanel = (JPanel) component;
                for (Component innerComponent : cardPanel.getComponents()) {
                    if (innerComponent instanceof JRadioButton) {
                        JRadioButton radioButton = (JRadioButton) innerComponent;
                        if (radioButton.isSelected()) {
                            int id = getCardFromPanel();
                            Cards selectedCard = decks.get(username).get(id);
                            int myCase = selectedCard.isPlayable(decks.get("throwed").getLast());
                            if (selectedCard != null) {
                                if (myCase == 1) {
                                    if (selectedCard instanceof WildCard) {
                                        try {
                                            ((WildCard) selectedCard).setSettedColor(colorGroup.getSelection().getActionCommand());
                                        } catch (NullPointerException e1) {
                                            ((WildCard) selectedCard).setSettedColor("red");
                                        }
                                    }

                                    decks.get(username).remove(selectedCard);
                                    decks.get("throwed").add(selectedCard);
                                    logAction(username + " plays " + selectedCard.getPath());
                                    displayMidCard();
                                    radioButtonList.remove(id);

                                    panel_5.remove(cardPanel);
                                    panel_5.revalidate();
                                    panel_5.repaint();

                                    panel_10.setBackground(Color.RED);
                                    updateLeaderBoard();

                                    if (!(selectedCard instanceof WildCard && ((WildCard) selectedCard).getType().equals("change_color_plus4"))) {
                                        Cards midCard = decks.get("throwed").getLast();
                                        if (midCard instanceof WildCard && ((WildCard) midCard).getType().equals("change_color_plus4")) {
                                            ((WildCard) midCard).setDoesAffectedGame(false);
                                        }
                                    }

                                    if (selectedCard instanceof ActionCard) {
                                        if (((ActionCard) selectedCard).getType().equals("plus2")) {
                                            drawNumForUser = 2;
                                            logAction(username + " plays +2 card");
                                        } else if (((ActionCard) selectedCard).getType().equals("skip")) {
                                            int step = ascendingOrder ? 1 : -1;
                                            botId += step;
                                            logAction(username + " plays skip card");
                                            if (botId < 1 || botId > totalBotNum2) {
                                                myTurn = true;
                                            }
                                            simulateBotTurns();
                                            return 1;
                                        } else if (((ActionCard) selectedCard).getType().equals("reverse")) {
                                            changeDirection();
                                            logAction(username + " plays reverse card");
                                        }
                                    }
                                    if (selectedCard instanceof WildCard) {
                                        if (((WildCard) selectedCard).getType().equals("change_color_plus4")) {
                                            drawNumForUser = 4;
                                            ((WildCard) selectedCard).setDoesAffectedGame(true);
                                            logAction(username + " plays wild +4 card");
                                        }
                                    }

                                    res = 1;
                                    checkForWinner();
                                    return res;
                                } else if (myCase == 2) {
                                    res = 2;
                                    return res;
                                } else {
                                    System.out.println("myCase" + myCase);
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    private boolean checkMidPlayable() {
        boolean res = true;
        if (decks.get("throwed").getLast() instanceof ActionCard) {
            if (!((ActionCard) decks.get("throwed").getLast()).isDoesAffectedGame()) {
                res = false;
                ((ActionCard) decks.get("throwed").getLast()).setDoesAffectedGame(true);
            }
        }

        if (decks.get("throwed").getLast() instanceof WildCard) {
            if (!((WildCard) decks.get("throwed").getLast()).isDoesAffectedGame()) {
                res = false;
                ((WildCard) decks.get("throwed").getLast()).setDoesAffectedGame(true);

            }
        }

        return res;
    }

    private void drawACard(Cards aCard) {

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(10 + 106 * decks.get(username).size(), 10, 106, 172);
        panel_5.add(panel_3);
        panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

        JLabel imageLabel = new JLabel("");
        imageLabel.setBounds(10, 10, 84, 129);
        panel_3.add(imageLabel);

        String imagePath = aCard.getPath();

        BufferedImage originalImage = loadImage(imagePath);
        BufferedImage resizedImage = resizeImage(originalImage, 84, 129);

        imageLabel.setIcon(new ImageIcon(resizedImage));

        JRadioButton rdbtnNewRadioButton = new JRadioButton();
        rdbtnNewRadioButton.setBounds(40, 145, 21, 21);
        group.add(rdbtnNewRadioButton);
        panel_3.add(rdbtnNewRadioButton);

        radioButtonList.add(rdbtnNewRadioButton);

        panel_5.add(panel_3);

        panel_5.revalidate();
        panel_5.repaint();
    }

    private void displayMidCard() {
        String path = decks.get("throwed").getLast().getPath();

        BufferedImage img = loadImage(path);
        BufferedImage resizedImg = resizeImage(img, 84, 129);

        midCardsImage.setIcon(new ImageIcon(resizedImg));

        String arrowPath;
        if (gameWay) arrowPath = "images/design_images/ccw.png";
        else arrowPath = "images/design_images/cw.png";

        BufferedImage img2 = loadImage(arrowPath);
        BufferedImage resizedImg2 = resizeImage(img2, 84, 129);

        gameway_arrow.setIcon(new ImageIcon(resizedImg2));

        if (decks.get("throwed").getLast() instanceof WildCard) {
            colorOfMidCard.setText(((WildCard) decks.get("throwed").getLast()).getSettedColor());
        } else {
            colorOfMidCard.setText(decks.get("throwed").getLast().getColor());
        }
        remainedCards.setText(String.valueOf(decks.get("mid").size()));
    }

    private int getCardFromPanel() {
        int radioButtonId = -1;
        for (int i = 0; i < radioButtonList.size(); i++) {
            if (radioButtonList.get(i).isSelected()) radioButtonId = i;
        }

        return radioButtonId;
    }

    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();

        return outputImage;
    }

    private void updateLeaderBoard() {
        int i = 0;
        int[] points = new int[totalBotNum];
        for (String key : decks.keySet()) {
            if (i < totalBotNum) points[i] = decks.get(key).size();
            i++;
        }

        int j = 0;
        for (JLabel label : pointLabelList) {
            label.setText(String.valueOf(points[j]));

            j++;
        }

        botsRemainders.revalidate();
        botsRemainders.repaint();

    }

    private void changePanelColor(int index) {
        for (int i = 0; i < botPanels.size(); i++) {
            JPanel panel = (JPanel) botPanels.get(i);
            Component[] components = panel.getComponents();
            if (i == index) {
                for (Component comp : components) {
                    if (comp instanceof JPanel && comp.getBounds().equals(new Rectangle(20, 20, 30, 30))) {
                        comp.setBackground(Color.GREEN);
                        break;
                    }
                }
            } else {
                for (Component comp : components) {
                    if (comp instanceof JPanel && comp.getBounds().equals(new Rectangle(20, 20, 30, 30))) {
                        comp.setBackground(Color.RED);
                    }
                }
            }
        }
    }

    private void resetAllPanels() {
        Rectangle targetBounds = new Rectangle(20, 20, 30, 30);
        for (Component panel : botPanels) {
            Component[] components = ((Container) panel).getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel && comp.getBounds().equals(targetBounds)) {
                    comp.setBackground(Color.RED);
                }
            }
        }
    }

    private void botDrawCard(int n, String bot) {
        for (int i = 0; i < n; i++) {
            decks.get(bot).add(decks.get("mid").getFirst());
            decks.get("mid").removeFirst();
            logAction(bot + " draws a card from deck");
            if (bot.equals(username)) drawACard(decks.get(bot).getLast());
        }
    }

    private void logAction(String action) {
        try (FileWriter fw = new FileWriter(String.format("data/gamelogs/%s_events.txt", gameLog), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == throwCardButton) {
            if (decks.get(username).size() == 0) {
                dispose();
                System.out.println(username + "win!");
            }

            int status = throwFromUserToMid();
            if (status == 1) {
                simulateBotTurns();
            }
        }
        if (e.getSource() == drawCardButton) {
            if (drawNumForUser > 0) {
                for (int i = 0; i < drawNumForUser; i++) {
                    decks.get(username).add(decks.get("mid").removeFirst());
                    drawACard(decks.get(username).get(decks.get(username).size() - 1));
                    logAction(username + " draws a card as penalty");
                }
                drawNumForUser = 0;
            } else {
                decks.get(username).add(decks.get("mid").removeFirst());
                drawACard(decks.get(username).get(decks.get(username).size() - 1));
                logAction(username + " draws a card from deck");
            }

            updateLeaderBoard();
            panel_10.setBackground(Color.GREEN);
            continueButton.setEnabled(true);
            checkForWinner();
        }
        if (e.getSource() == stopGameButton) {
            isPaused = !isPaused;
        }
        if (e.getSource() == continueButton) {

            throwCardButton.setEnabled(true);
            saveGameButton.setEnabled(true);
            drawCardButton.setEnabled(true);
            continueButton.setEnabled(false);
            simulateBotTurns();
            checkForWinner();
        }
        if (e.getSource() == saveGameButton) {
        	Games.saveGame(gameLog, decks);
        	dispose();
        	//new UserPageOverlay(username, password);
        }
    }
}