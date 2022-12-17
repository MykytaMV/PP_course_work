package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Deposits.Demand_Deposit;
import Deposits.Deposit;
import Deposits.Term_Deposit;

public class TakeDepositeCommand implements DepositCommand {
	private Statement statement;
	private int UserID;
	private int lastID;
	private boolean capiralization;
	private HashMap<Integer, Deposit> Deposits;
	
	TakeDepositeCommand(Statement statement, int UserID, HashMap<Integer, Deposit> Deposits){
		this.statement=statement;
		this.UserID = UserID;
		this.lastID = 0;
		capiralization = false;
		this.Deposits = Deposits;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		int ID_in = 0;
		do {
			System.out.print("Enter the id of the deposit you want to take: ");
			ID_in = scan.nextInt();
			String sqlCommand = "select DepositID from Deposits where DepositID = "+ID_in+";";
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
		
		String bank_D = null;
		String type_D = null;
		float interest_rate_D = 0;
		int noipify_D = 0;
		
		String capitalization_check = "";
		int term = 0;
		
		try {
			String sqlCommand = "select Bank, Type, Nnormalized_annual_interest_rate, Number_of_interest_periodsIn_full_year, "
					+ "Capitalization,Term from Deposits where DepositID = "+ID_in+";";
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
            	bank_D = resultSet.getString("Bank");
            	type_D = resultSet.getString("Type");
            	interest_rate_D = resultSet.getFloat("Nnormalized_annual_interest_rate");
            	noipify_D = resultSet.getInt("Number_of_interest_periodsIn_full_year");
            	capitalization_check = resultSet.getString("Capitalization");
            	term = resultSet.getInt("Term");
            }
		}catch (SQLException e) {
			System.out.println("An error occurred:");
			throw new RuntimeException(e);
		}
		
		if(capitalization_check.equals("yes")) {
			try {
				String sqlCommand = "insert into User"+UserID+" (DepositID, Deposited_money, Months_left) values ("+ID_in+","+0+","+term+");";
				statement.execute(sqlCommand);
				this.capiralization = true;
			}catch (SQLException e) {
				System.out.println("An error occurred:");
	        	throw new RuntimeException(e);
			}
		}else {
			try {
				String sqlCommand = "insert into User"+UserID+" (DepositID, Deposited_money) values ("+ID_in+","+0+");";
				statement.execute(sqlCommand);
				this.capiralization = false;
			}catch (SQLException e) {
				System.out.println("An error occurred:");
	        	throw new RuntimeException(e);
			}
		}
		
		if(type_D.equals("Term")) {
			 Deposits.put(ID_in ,new Term_Deposit(bank_D, interest_rate_D, term, this.capiralization, noipify_D));
		}
		if(type_D.equals("Demand")) {
			Deposits.put(ID_in ,new Demand_Deposit(bank_D, interest_rate_D, this.capiralization, noipify_D));
		}
		
		lastID = ID_in;
	}

	@Override
	public void unexecute() {
		System.out.print("Are you sure you want to delete last taken deposit? if yes - 0");
		Scanner scan = new Scanner(System.in);
		int inp = scan.nextInt();
		if(inp!=0)
			return;
		else {
			try {
				String sqlCommand = "delete from User"+UserID+" where DepositID = "+lastID+";";
				statement.execute(sqlCommand);
			}catch (SQLException e) {
	            System.out.println("An error occurred:");
	            throw new RuntimeException(e);
	        }
			Deposits.remove(lastID);
		}

	}
	
	public int getId() {
		return lastID;
	}
	
	public boolean withCapitalization() {
		return capiralization;
	}

}
