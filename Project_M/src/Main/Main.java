package Main;

import java.sql.*;
import java.util.Scanner;

import Commands.Cabinet;
import Deposite_Commands.DepositCabinet;

public class Main {
	
	private static int ID = 0;
	
	public static void main(String[] args) throws SQLException{
	
		try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch (ClassNotFoundException e){
        	e.printStackTrace();
    	}
		String url = "jdbc:sqlserver://DESKTOP-Q1UB9GL:1433;authenticationScheme=NTLM;UserName=sa;Password=123;databaseName=bank_deposits;encrypt=true;trustServerCertificate=true;integratedSecurity=false;domain=MyDomain;";
    	try{
        	Connection connection;
        	connection = DriverManager.getConnection(url);
        	if (connection != null) {
        		System.out.println("Connected");
        	}
        	System.out.println("Start program");
        	Statement statement = connection.createStatement();
        	Scanner scan = new Scanner(System.in);
        	Cabinet cabinet = new Cabinet(statement);
        	int input = 0;
        	do {
        		System.out.println("Create account - 1\nLog in account - 2\nExit program - 3");
        		input = scan.nextInt();
        		switch (input) {
        		case 1 ->{
        			ID = cabinet.createAccount();
        		}
        		case 2 ->{
        			ID = cabinet.logInAccount();
        		}
        		default ->{
        			continue;
        		}
        		}	
        	}while(false);
        	
        	if(input == 3)
        		input = 0;
        	
        	DepositCabinet depositCabinet = new DepositCabinet(statement, ID);
        	
        	while(input != 0) {
        		System.out.println("Exit program - 0\nShow and sort all deposits - 1\nShow all my deposits - 2\nTake deposite - 3\n"
        				+ "Top up deposit - 4\nWait - 5\nWithdraw deposit - 6\nRemove deposit - 7\n");
        		input = scan.nextInt();
        		switch (input) {
        		case 0 ->{
        			continue;
        		}
        		case 1 ->{
        			depositCabinet.showDeposits();
        		}
        		case 2 ->{
        			depositCabinet.showMyDeposit();
        		}
        		case 3 ->{
        			depositCabinet.takeDeposit();
        		}
        		case 4 ->{
        			depositCabinet.topUpDeposit();
        		}
        		case 5 ->{
        			depositCabinet.waitDeposit();
        		}
        		case 6 ->{
        			depositCabinet.withdrawDeposit();
        		}
        		case 7->{
        			depositCabinet.removeDeposit();
        		}
        		}
        	}
        	scan.close();
        	connection.close();
    	} catch (SQLException e) {
        	System.out.println("An error occurred:");
        	throw new RuntimeException(e);
    	}
    	System.out.println("Program closed");
	}
}