package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import Deposits.Deposit;

public class WaitDepositCommand implements DepositCommand {
	private Statement statement;
	private int UserID;
	private HashMap<Integer, Deposit> Deposits;
	
	WaitDepositCommand(Statement statement, int UserID, HashMap<Integer, Deposit> Deposits){
		this.statement=statement;
		this.UserID = UserID;
		this.Deposits = Deposits;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		int time;
		
		int ID_in = 0;
		do {
			System.out.print("Enter the id of the deposit you want to wait for: ");
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
			
		do {
			System.out.print("How many full months do you want to wait: ");
			time = scan.nextInt();
			if(time < 0)
				System.out.print("Please try again\n");
		}while(time < 0);
		
		int time_left = 0;
		
		try {
			
			String sqlCommand = "select Months_left from User"+UserID+" where DepositID = "+ID_in+";";
			ResultSet resultSet = statement.executeQuery(sqlCommand);
			while (resultSet.next()) {
            	time_left = resultSet.getInt("Months_left");
            	
            	Deposits.get(ID_in).toWait(time);
            }
		}catch (SQLException e) {
			System.out.println("An error occurred:");
        	throw new RuntimeException(e);
		}
		
		try {
    		time_left -= time;
    		if(time_left < 0)
    			time_left = 0;
    		String sqlCommand = "update User"+UserID+" set Months_left = "+time_left+" where DepositID = "+ID_in+";";
    		statement.execute(sqlCommand);
    	}catch (SQLException e) {
			System.out.println("An error occurred:");
        	throw new RuntimeException(e);
		}
		
		System.out.print("you waited "+time+" months for deposit"+ID_in+"\n");

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

}
