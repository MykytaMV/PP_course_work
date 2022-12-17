package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import Deposits.Deposit;

public class RemoveDepositCommand implements DepositCommand {
	private Statement statement;
	private int UserID;
	private HashMap<Integer, Deposit> Deposits;
	
	RemoveDepositCommand(Statement statement, int UserID, HashMap<Integer, Deposit> Deposits){
		this.statement=statement;
		this.UserID = UserID;
		this.Deposits = Deposits;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		int input;
		do {
			System.out.print("Remove all deposits - 1\nRemove the selected deposit - 2\n");
			input = scan.nextInt();
    		switch (input) {
    		case 1 ->{
    			try {
    				String sqlCommand = "truncate table User"+UserID+";";
    				statement.execute(sqlCommand);
    			}catch (SQLException e) {
    	            System.out.println("An error occurred:");
    	            throw new RuntimeException(e);
    	        }
    			
    			Deposits.clear();
    		}
    		case 2 ->{
    			
    			int ID_in = 0;
    			do {
    				System.out.print("Enter the id of the deposit you want to remove: ");
    				ID_in = scan.nextInt();
    				String sqlCommand = "select DepositID from User"+UserID+" where DepositID = "+ID_in+";";
    				int ID_check = 0;
    				try {
    	                ResultSet resultSet = statement.executeQuery(sqlCommand);
    	                while (resultSet.next()) {
    	                	ID_check = resultSet.getInt("DepositID");
    	                }
    				}catch (SQLException e) {
    					System.out.println("An error occurred:");
    					throw new RuntimeException(e);
    				}
    				if(ID_in != ID_check || ID_check == 0) {
    					System.out.print("This deposit does not exist\n");
    					ID_in = 0;
    				}
    			}while(ID_in == 0);
    			
    			try {
    				String sqlCommand = "delete from User"+UserID+" where DepositID = "+ID_in+";";
    				statement.execute(sqlCommand);
    			}catch (SQLException e) {
    	            System.out.println("An error occurred:");
    	            throw new RuntimeException(e);
    	        }
    			
    			Deposits.remove(ID_in);
    		}
    		case 3 ->{
    			try {
    				int ID_check;
    				String sqlCommand = "select from User"+UserID+" where Deposited_money = "+0+";";
    				ResultSet resultSet = statement.executeQuery(sqlCommand);
	                while (resultSet.next()) {
	                	ID_check = resultSet.getInt("DepositID");
	                	
	                	Deposits.remove(ID_check);
	                }
    			}catch (SQLException e) {
    	            System.out.println("An error occurred:");
    	            throw new RuntimeException(e);
    	        }
    			
    			try {
    				String sqlCommand = "delete from User"+UserID+" where Deposited_money = "+0+";";
    				statement.execute(sqlCommand);
    			}catch (SQLException e) {
    	            System.out.println("An error occurred:");
    	            throw new RuntimeException(e);
    	        }
    			
    			
    		}
    		default ->{
    			System.out.println("incorrect value");
    			input = 4;
    		}
    		}
		}while(input==4);
		
		System.out.print("Deposits removed\n");

	}

	@Override
	public void unexecute() {
		
	}

}
