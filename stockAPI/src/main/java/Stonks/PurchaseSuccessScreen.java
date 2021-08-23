package Stonks;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;

public class PurchaseSuccessScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtQuantity;
	private JLabel lblTimer;
	String user;
	private ArrayList<String> storeStock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PurchaseSuccessScreen frame = new PurchaseSuccessScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Get stocks from the database with selected country to put to array list
	private ArrayList<String> getStocks(int i) {
		ArrayList<String> stocks = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection com = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocks","root","root");
			Statement stmt = com.createStatement();
			//Select stocks with certain marketID
			String retrieveStock = "SELECT Name FROM stock WHERE MarketID='"+i+"'";
			ResultSet rs = stmt.executeQuery(retrieveStock);
			//Continue adding until there's non left
			while (rs.next()) {
				String name = rs.getString("Name");
				stocks.add(name);
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return stocks;
	}
	
	//Convert array list to array to put into combo box
	private String[] convertArray(ArrayList<String> a) {
		String[] array = a.toArray(new String[0]);
		return array;
	}
		
	private void timeuntilClosing() {
		Thread time = new Thread() {
			public void run() {
				try {
					for (;;) {
					//Get current time + Calculate time until purchase window close
					Date date = new Date();
					int second = 59-date.getSeconds();
					int minute = 59-date.getMinutes();
					int hour = 16-date.getHours();
					
				
					
					lblTimer.setText(hour+ " Hour "+ minute+ " Minute "+ second+ " Second ");
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
	public PurchaseSuccessScreen() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox cmbStocks = new JComboBox();
		cmbStocks.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbStocks.setBounds(228, 108, 138, 34);
		contentPane.add(cmbStocks);
		
		JLabel lblNewLabel = new JLabel("Purchasing");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 35));
		lblNewLabel.setBounds(25, 11, 234, 49);
		contentPane.add(lblNewLabel);
		
		final JComboBox cmbMarket = new JComboBox();
		cmbMarket.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbMarket.setModel(new DefaultComboBoxModel(new String[] {"", "USA", "France", "Australia"}));
		cmbMarket.setBounds(25, 106, 138, 34);
		contentPane.add(cmbMarket);
		cmbMarket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If USA is picked in the cmbMarket
				if ("USA".equals(cmbMarket.getSelectedItem())) {
					cmbStocks.removeAllItems();
					storeStock = getStocks(1);
					String[] stockname = convertArray(storeStock);
					final DefaultComboBoxModel USModel = new DefaultComboBoxModel(stockname);
					cmbStocks.setModel(USModel);	
				} else {
					//If France is picked in the cmbMarket
					if ("France".equals(cmbMarket.getSelectedItem())) {
						cmbStocks.removeAllItems();
						storeStock = getStocks(2);
						String[] stockname = convertArray(storeStock);
						final DefaultComboBoxModel FRModel = new DefaultComboBoxModel(stockname);
						cmbStocks.setModel(FRModel);
					} else {
						//If Australia is picked in the cmbMarket
						storeStock = getStocks(3);
						cmbStocks.removeAllItems();
						String[] stockname = convertArray(storeStock);
						final DefaultComboBoxModel AXModel = new DefaultComboBoxModel(stockname);
						cmbStocks.setModel(AXModel);
					}
				}
					
			}
		});
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(310, 257, 98, 24);
		contentPane.add(lblNewLabel_1_1_1);
		
		txtQuantity = new JTextField();
		txtQuantity.setBounds(419, 259, 138, 24);
		contentPane.add(txtQuantity);
		txtQuantity.setColumns(10);
		
		JLabel lbl = new JLabel("Total:");
		lbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl.setBounds(35, 257, 75, 24);
		contentPane.add(lbl);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setForeground(Color.RED);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(87, 252, 111, 34);
		contentPane.add(lblPrice);
		
		JLabel lblNewLabel_2 = new JLabel("Window close in:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(0, 321, 189, 34);
		contentPane.add(lblNewLabel_2);
		
		lblTimer = new JLabel("New label");
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer.setForeground(Color.RED);
		timeuntilClosing();
		lblTimer.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTimer.setBounds(185, 318, 392, 41);
		contentPane.add(lblTimer);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnMenu.setBounds(35, 417, 130, 34);
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
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setForeground(Color.RED);
		btnConfirm.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnConfirm.setBounds(478, 417, 130, 34);
		contentPane.add(btnConfirm);
		
		JComboBox cmbCurrency = new JComboBox();
		cmbCurrency.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbCurrency.setModel(new DefaultComboBoxModel(new String[] {"", "USD", "CAD", "AUD", "EUR", "VND"}));
		cmbCurrency.setBounds(439, 106, 138, 34);
		contentPane.add(cmbCurrency);
		
		JLabel lblNewLabel_1 = new JLabel("Country");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(25, 71, 125, 28);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Stock Name");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(241, 71, 125, 28);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Currency");
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_2.setBounds(439, 71, 125, 28);
		contentPane.add(lblNewLabel_1_1_2);
		
		JButton btnCheckPrice = new JButton("Check Price");
		btnCheckPrice.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnCheckPrice.setBounds(246, 417, 130, 34);
		contentPane.add(btnCheckPrice);
	}
}
