package com.java.namga.stockAPI;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Hello world!
 *
 */
public class YahooStockAPI 
{
	//Set arrays + arraylist
	static ArrayList<BigDecimal> USStocks = new ArrayList<BigDecimal>();
	static ArrayList<BigDecimal> FRStocks = new ArrayList<BigDecimal>();
	static ArrayList<BigDecimal> AXStocks = new ArrayList<BigDecimal>();
	static String[] USA = new String[] {"NKE","GOOG","TSLA"};
	static String[] France = new String[] {"AIR.PA","SAF.PA"};
	static String[] Australia = new String[] {"CBA.AX","ANZ.AX"};
	
	//Get + store stock prices for each country
	public static ArrayList<BigDecimal> getMultiple(String[] symbols) throws IOException {
	ArrayList<BigDecimal> StocksPrice = new ArrayList<BigDecimal>();
	for (int i = 0; i < symbols.length; i++) {
		Stock stock = YahooFinance.get(symbols[i]);
		BigDecimal price = stock.getQuote().getPrice();
		StocksPrice.add(price);
	}

	return StocksPrice;
	}
	

	
	public static void addtoDatabase(String[] symbols, ArrayList<BigDecimal> stocks, int ID) {
		try {
			//Connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection com = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocks","root","root");
			//Check to see whether there's already stocks of that name
			Statement stmt = com.createStatement();
			for (int i = 0; i < symbols.length; i++) {
			String checkStock = "SELECT * FROM stock WHERE Name='"+symbols[i]+"'";
			ResultSet StockExist = stmt.executeQuery(checkStock);
			if(StockExist.next()) {
				//Update price for the stock
				String queryUpdate = "UPDATE stock SET Value='"+stocks.get(i)+"' WHERE Name='"+symbols[i]+"'";
				PreparedStatement pat = com.prepareStatement(queryUpdate);
				pat.execute();
				System.out.println("Prices updated");
			}
			else { 
				//Add the stocks onto database
				String queryAdd = "INSERT INTO stock(Name,MarketID,Value) values('"+symbols[i]+"','"+ID+"','"+stocks.get(i)+"')";
				Statement sta = com.createStatement();
				int x = sta.executeUpdate(queryAdd);
				if (x == 0) {
					System.out.println("Addition failed");
				} else {
					//Confirmation
					System.out.println("Stock has been added");
					}
				}
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	
    public static void main( String[] args ) throws IOException 
    {
    	//Add+Update the US stocks to the database
		USStocks = getMultiple(USA);
    	addtoDatabase(USA,USStocks,1);
    	//Add+Update the French stocks to the database
    	FRStocks = getMultiple(France);
    	addtoDatabase(France,FRStocks,2);
    	//Add+Update the Australian stocks to the database
    	AXStocks = getMultiple(Australia);
    	addtoDatabase(Australia,AXStocks,3);
    
    }	
}

