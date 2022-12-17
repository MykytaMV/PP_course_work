package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import Deposits.Deposit;

public class WithdrawDepositCommand implements DepositCommand {
	private Statement statement;
	private int UserID;
	private HashMap<Integer, Deposit> Deposits;
	private float amount_of_income;
	
	WithdrawDepositCommand(Statement statement, int UserID, HashMap<Integer, Deposit> Deposits){
		this.statement=statement;
		this.UserID = UserID;
		this.Deposits = Deposits;
		amount_of_income = 0;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		int input;
		float income;
		
    		input = scan.nextInt();
    		int ID_in = 0;
			do {
				System.out.print("Enter the id of the deposit you want to withdraw: ");
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
			
			income = Deposits.get(ID_in).toWithdraw();
			amount_of_income += income;
			System.out.print(ID_in + " income " + income + "\n");
			
			try {
				String sqlCommand_2 = "update User"+UserID+" set Deposited_money = "+0+" where DepositID = "+ID_in+";";
        		statement.execute(sqlCommand_2);
			}catch (SQLException e) {
    			System.out.println("An error occurred:");
            	throw new RuntimeException(e);
    		}
		
		System.out.print("Amount of all income " + amount_of_income + "\n");

	}

	@Override
	public void unexecute() {
		amount_of_income = 0;

	}

}
