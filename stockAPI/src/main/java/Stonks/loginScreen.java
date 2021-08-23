package Stonks;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class loginScreen {

	private JFrame frame;
	private JTextField txtUser;
	private JPasswordField txtPass;
	String username;
	String password;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginScreen window = new loginScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	//Create application
	public loginScreen() {
		initialize();
	}
	
	//Connect SQL and return to query
	public void loginEnter() {
		UserInfo userinfo = new UserInfo(username,password);
		String user = txtUser.getText();
		String pass = txtPass.getText();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection com = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocks","root","root");
			Statement state = com.createStatement();
			
			@SuppressWarnings("deprecation")
			String sql = "Select * from user where Username ='"+user+"' and Password ='"+pass.toString()+"'";
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "Login successful");
				//Creating object to change frame
				userinfo.setUsername(user);
				frame.dispose();
				menuScreen menu = new menuScreen();
				menu.setVisible(true);
			
			}
			else {
					JOptionPane.showMessageDialog(null, "Login unsuccessful");
				}
			}
			catch(Exception exc) {
				exc.printStackTrace();
			}
	}


	//Initialize content of frame
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to Stonk Rush");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 36));
		lblNewLabel.setBounds(59, 47, 572, 83);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(69, 141, 150, 25);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(69, 220, 150, 25);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		txtUser = new JTextField();
		txtUser.setBounds(176, 139, 362, 36);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);
	
		
		txtPass = new JPasswordField();
		txtPass.setBounds(176, 218, 362, 37);
		frame.getContentPane().add(txtPass);
		
		JButton btnLogIn = new JButton("Log-in");
		btnLogIn.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLogIn.setBounds(507, 291, 137, 36);
		frame.getContentPane().add(btnLogIn);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Perform function
				loginEnter();

		};
	});
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSignUp.setBounds(69, 291, 137, 36);
		frame.getContentPane().add(btnSignUp);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Change to sign up screen
					frame.dispose();
					signupScreen signup = new signupScreen();
					signup.setVisible(true);
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
	}

}
