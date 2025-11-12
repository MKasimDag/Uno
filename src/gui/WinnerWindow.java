package gui;

import javax.swing.*;
import java.awt.*;

public class WinnerWindow extends JFrame {

    public WinnerWindow(String winner) {
        setTitle("Game Over");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel winnerLabel = new JLabel("Winner: " + winner, SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(winnerLabel);

        setVisible(true);
    }
}