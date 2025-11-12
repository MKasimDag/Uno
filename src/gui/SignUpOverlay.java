package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dataProcessing.Login;

import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class SignUpOverlay extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton signupButton;
	private JButton goBackButton;
	private JLabel dispMessageTxt;

	/**
	 * Create the frame.
	 */
	public SignUpOverlay() {
		this.setResizable(false);
		this.setBounds(100, 100, 400, 600);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, -25, 390, 588);
		getContentPane().add(panel_1);
		
		signupButton = new JButton("Sign Up");
		signupButton.setBounds(47, 466, 129, 46);
		signupButton.addActionListener(this);
		panel_1.add(signupButton);
		
		JLabel username_txt = new JLabel("Username");
		username_txt.setBounds(47, 103, 294, 33);
		panel_1.add(username_txt);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(47, 146, 294, 40);
		panel_1.add(textField);
		
		JLabel password_txt = new JLabel("Password");
		password_txt.setBounds(47, 196, 294, 33);
		panel_1.add(password_txt);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(47, 239, 294, 40);
		panel_1.add(passwordField);
		
		JLabel lblNewLabel_1_2 = new JLabel("Create An Account");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(109, 44, 165, 55);
		panel_1.add(lblNewLabel_1_2);
		
		dispMessageTxt = new JLabel("");
		dispMessageTxt.setForeground(Color.RED);
		dispMessageTxt.setFont(new Font("Tahoma", Font.BOLD, 10));
		dispMessageTxt.setBounds(47, 400, 294, 40);
		panel_1.add(dispMessageTxt);
		
		JLabel password_txt_1 = new JLabel("Password Again");
		password_txt_1.setBounds(47, 289, 294, 33);
		panel_1.add(password_txt_1);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(47, 334, 294, 40);
		panel_1.add(passwordField_1);
		
		goBackButton = new JButton("Go Back");
		goBackButton.setBounds(212, 466, 129, 46);
		goBackButton.addActionListener(this);
		panel_1.add(goBackButton);
		
		//this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == signupButton) {
			
			if (passwordField.getText().equals(passwordField_1.getText())) {
				
				int result = Login.registrar(textField.getText(), passwordField.getText());
				
				if (result == 1) {
					dispMessageTxt.setText("Succesfully sign up. Please click Go Back!");
				}
				
				if (result == -1) {
					dispMessageTxt.setText("User already exists!");
				}
				
				if (result == -2) {
					dispMessageTxt.setText("Username's should begin with Uppercase charachters. Please try again!");
				}
			}
			else {
				dispMessageTxt.setText("Password validation is incorrect! Please try again.");
			}
		}
		if(e.getSource() == goBackButton) {
			LoginOverlay loginScreen = new LoginOverlay();
			loginScreen.setVisible(true);
			dispose();
		}
	}
}
