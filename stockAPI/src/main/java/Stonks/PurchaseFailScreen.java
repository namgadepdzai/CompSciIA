package Stonks;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class PurchaseFailScreen extends JFrame {

	private JPanel contentPane;
	private JLabel lblTimer;
	String user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PurchaseFailScreen frame = new PurchaseFailScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	//Set amount of time left to open the purchasing window
	public void timeuntilOpening() {
			Thread time = new Thread() {
				public void run() {
					try {
						for (;;) {
						//Get current time
						Date date = new Date();
						int day = date.getDay();
						int second = 60-date.getSeconds();
						int minute = 60-date.getMinutes();
						int hour = date.getHours();
					
						
						//Determine value of days left
						if (day < 1) {
							day = day;
						} else {
							if (day == 1) {
								if (hour < 16) {
									day = 0;
								} else {
									day = 7-day;
								}
						} else {
							day = 7-day;
						}
							
						}
						
						//Determine value of hours left
						if (hour < 16) {
							hour = 15-hour;
						}
						if (hour > 17) {
							hour = (24-hour)+15;
						}
						
						lblTimer.setText(day+ " Day "+hour+ " Hour "+ minute+ " Minute "+ second+ " Second ");
						}
					}
					catch(Exception exc) {
						exc.printStackTrace();
					}
				}
			};
			//Make time continuous
			time.start();
	}

	/**
	 * Create the frame.
	 */
	public PurchaseFailScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Purchasing window open in");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(35, 21, 334, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Go back to menu?");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(35, 198, 245, 34);
		contentPane.add(lblNewLabel_1);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnMenu.setBounds(35, 243, 160, 41);
		contentPane.add(btnMenu);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Change to menu screen
					setVisible(false);
					menuScreen menu = new menuScreen();
					menu.setVisible(true);
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
		lblTimer = new JLabel("New label");
		timeuntilOpening();
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimer.setBounds(35, 73, 392, 41);
		contentPane.add(lblTimer);
		
		JLabel lblNewLabel_2 = new JLabel("Purchasing window open every Monday from 16:00 to 17:00");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(35, 150, 432, 34);
		contentPane.add(lblNewLabel_2);
	}
}
