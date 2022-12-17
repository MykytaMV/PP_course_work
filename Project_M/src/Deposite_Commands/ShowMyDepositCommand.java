package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import Deposits.Deposit;

public class ShowMyDepositCommand implements DepositCommand {
	private Statement statement;
	private int UserID;
	private HashMap<Integer, Deposit> Deposits;
	
	ShowMyDepositCommand(Statement statement, int UserID, HashMap<Integer, Deposit> Deposits){
		this.statement=statement;
		this.UserID = UserID;
		this.Deposits = Deposits;
	}

	@Override
	public void execute() {
		int ID_D;
		float money_D;
		int time_D;
		
		try {
			String sqlCommand = "select DepositID, Deposited_money, Months_left from User"+UserID+";";
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
            	ID_D = resultSet.getInt("DepositID");
            	money_D = resultSet.getFloat("Deposited_money");
            	time_D = resultSet.getInt("Months_left");
            	
            	System.out.print("Deposit " + ID_D + ", Bank - " + Deposits.get(ID_D).getBank() + 
            			", deposited money - " + money_D + ", months left - " + time_D +
            			", interest rate - " + Deposits.get(ID_D).getInterestRate() +
            			", number of interest periods in full year - " + Deposits.get(ID_D).numberOfInterestPeriodsInFullYear() +
            			", with capitalization - " + Deposits.get(ID_D).withCapitalization() +"\n");
            	
            }
		}catch (SQLException e) {
			System.out.println("An error occurred:");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

}
