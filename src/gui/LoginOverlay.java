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

import dataProcessing.Login;
import java.awt.Color;

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

		this.setResizable(false);
		this.setBounds(100, 100, 1000, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		
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
		this.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		loginButton = new JButton("Log In");
		loginButton.setBounds(132, 327, 129, 46);
		loginButton.addActionListener(this);
		panel_1.add(loginButton);
		
		JLabel username_txt = new JLabel("Username");
		username_txt.setBounds(47, 103, 294, 33);
		panel_1.add(username_txt);
		
		username_input = new JTextField();
		username_input.setBounds(47, 146, 294, 40);
		panel_1.add(username_input);
		username_input.setColumns(10);
		
		JLabel password_txt = new JLabel("Password");
		password_txt.setBounds(47, 203, 294, 33);
		panel_1.add(password_txt);
		
		password_input = new JPasswordField();
		password_input.setBounds(47, 246, 294, 40);
		panel_1.add(password_input);
		
		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(132, 472, 129, 46);
		signUpButton.addActionListener(this);
		panel_1.add(signUpButton);
		
		JLabel constantText1 = new JLabel("Don't You Have An Accound. Click and Sign Up!");
		constantText1.setBounds(47, 422, 294, 40);
		panel_1.add(constantText1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Log In / Sign Up");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(111, 38, 140, 55);
		panel_1.add(lblNewLabel_1_2);
		
		dispMessageTxt = new JLabel("");
		dispMessageTxt.setForeground(Color.RED);
		dispMessageTxt.setFont(new Font("Tahoma", Font.BOLD, 10));
		dispMessageTxt.setBounds(47, 383, 294, 40);
		panel_1.add(dispMessageTxt);
		
		
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
