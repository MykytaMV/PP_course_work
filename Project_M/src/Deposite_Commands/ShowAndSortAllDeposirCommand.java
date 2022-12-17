package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ShowAndSortAllDeposirCommand implements DepositCommand {
	private Statement statement;
	
	ShowAndSortAllDeposirCommand(Statement statement){
		this.statement=statement;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		int input;
		String sqlCommand = null;
		do {
			System.out.print("Sort by ID - 1\nSort by bank name - 2\n"
					+ "Sort by interest rate - 3\nSort by type of deposit - 4\n");
			input = scan.nextInt();
    		switch (input) {
    		case 1 ->{
    			sqlCommand = "select * from Deposits order by DepositID;";
    		}
    		case 2 ->{
    			sqlCommand = "select * from Deposits order by Bank";
    		}
    		case 3 ->{
    			sqlCommand = "select * from Deposits order by Nnormalized_annual_interest_rate";
    		}
    		case 4 ->{
    			sqlCommand = "select * from Deposits order by Type";
    		}
    		default ->{
    			System.out.println("incorrect value\n");
    			input = 5;
    		}
    		}
		}while(input==5);
		
		int ID;
		String Bank;
		String Type;
		float Nnormalized_annual_interest_rate;
		String Capitalization;
		int Number_of_interest_periodsIn_full_year;
		int Term;
		
		try {
			ResultSet resultSet = statement.executeQuery(sqlCommand);
			while (resultSet.next()) {
				ID = resultSet.getInt("DepositID");
				Bank = resultSet.getString("Bank");
				Type = resultSet.getString("Type");
				Nnormalized_annual_interest_rate = resultSet.getFloat("Nnormalized_annual_interest_rate");
				Capitalization = resultSet.getString("Capitalization");
				Number_of_interest_periodsIn_full_year = resultSet.getInt("Number_of_interest_periodsIn_full_year");
				Term = resultSet.getInt("Term");
            	
            	System.out.print("Deposit " + ID + ", Bank - " + Bank + ", Type - " + Type +
            			"\n\t normalized annual interest rate - " + Nnormalized_annual_interest_rate +
            			", number of interest periods in full year - " + Number_of_interest_periodsIn_full_year +
            			", with capitalization - " + Capitalization +
            			", term - " + Term + "\n");
            	
            }
		}catch (SQLException e) {
			System.out.println("An error occurred:");
			throw new RuntimeException(e);
		}
		System.out.print("-----------------------------------------------\n");

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

}
