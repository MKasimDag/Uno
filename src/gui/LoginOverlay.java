package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import dataProcessing.Login;
import java.awt.Color;
import java.awt.Insets;

public class LoginOverlay extends JFrame implements ActionListener{

	private JTextField username_input;
	private JPasswordField password_input;
    private JLabel dispMessageTxt;
    private JButton loginButton;
    private JButton signUpButton;
    
    private String currentUser;
    
    public String getCurrentUser() {
		return currentUser;
	}

    public LoginOverlay() {

        this.setTitle("UNO - Login");
        this.setResizable(false);
        this.setBounds(100, 100, 1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setIconImage(new ImageIcon("images/design_images/table.png").getImage());
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 600);
		//panel.setVisible(true);
		this.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("images/design_images/image_1.jpg"));
		lblNewLabel.setBounds(10, 10, 580, 580);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(610, 303, 0, 2);
		this.getContentPane().add(separator);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(610, 0, 390, 600);
		//panel.setVisible(true);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		loginButton = new JButton("Log In");
		loginButton.setBounds(132, 327, 129, 46);
		loginButton.setBackground(new Color(0x1976D2));
		loginButton.setForeground(Color.WHITE);
		loginButton.setFocusPainted(false);
		loginButton.setMargin(new Insets(6,12,6,12));
		loginButton.addActionListener(this);
		panel_1.add(loginButton);
		
		JLabel username_txt = new JLabel("Username");
		username_txt.setBounds(47, 103, 294, 33);
		username_txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panel_1.add(username_txt);
		
		username_input = new JTextField();
		username_input.setBounds(47, 146, 294, 40);
		username_input.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel_1.add(username_input);
		username_input.setColumns(10);
		
		JLabel password_txt = new JLabel("Password");
		password_txt.setBounds(47, 203, 294, 33);
		password_txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panel_1.add(password_txt);
		
		password_input = new JPasswordField();
		password_input.setBounds(47, 246, 294, 40);
		password_input.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel_1.add(password_input);
		
		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(132, 472, 129, 46);
		signUpButton.setBackground(new Color(0x2E7D32));
		signUpButton.setForeground(Color.WHITE);
		signUpButton.setFocusPainted(false);
		signUpButton.setMargin(new Insets(6,12,6,12));
		signUpButton.addActionListener(this);
		panel_1.add(signUpButton);
		
		JLabel constantText1 = new JLabel("Don't have an account? Click Sign Up!");
		constantText1.setBounds(47, 422, 294, 40);
		constantText1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(constantText1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Log In / Sign Up");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1_2.setBounds(111, 38, 140, 55);
		panel_1.add(lblNewLabel_1_2);
		
		dispMessageTxt = new JLabel("");
		dispMessageTxt.setForeground(new Color(0xD32F2F));
		dispMessageTxt.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dispMessageTxt.setBounds(47, 383, 294, 40);
		panel_1.add(dispMessageTxt);
		
		
		getRootPane().setDefaultButton(loginButton);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginButton) {
			if (Login.logIn(username_input.getText(), password_input.getText())) {
				dispMessageTxt.setText("Login is successful. Please wait a few seconds!");
				currentUser = username_input.getText();

				UserPageOverlay user = new UserPageOverlay(username_input.getText(), password_input.getText());
				user.setVisible(true);
				dispose();
				
			}
			
			else {
				dispMessageTxt.setText("Invalid username or password. Please try again!");
			}
		}
		if(e.getSource() == signUpButton) {
			SignUpOverlay registerScreen = new SignUpOverlay();
			registerScreen.setVisible(true);
			dispose();
		}
	}
}
