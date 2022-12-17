package Deposite_Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import Deposits.Deposit;

public class TopUpDepositCommand implements DepositCommand {
	private Statement statement;
	private int UserID;
	private int currentID;
	private HashMap<Integer, Deposit> Deposits;
	
	TopUpDepositCommand(Statement statement, int UserID, HashMap<Integer, Deposit> Deposits){
		this.statement=statement;
		this.UserID = UserID;
		this.currentID = 0;
		this.Deposits = Deposits;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		int ID_in = 0;
		do {
			System.out.print("Enter the id of the deposit you want to top up: ");
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
		
		if(Deposits.get(ID_in).Deposited()) {
			System.out.print("You cannot top up this deposit\n");
			return;
		}
		
		int money;
		do {
			System.out.print("How much do you want to deposit: ");
			money = scan.nextInt();
			if(money<0)
				System.out.print("Please try again\n");
		}while(money < 0);
		
		Deposits.get(ID_in).toDeposite(money);
		
		try {
			String sqlCommand = "update User"+UserID+" set Deposited_money = "+money+" where DepositID = "+ID_in+";";
			statement.execute(sqlCommand);
		}catch (SQLException e) {
			System.out.println("An error occurred:");
        	throw new RuntimeException(e);
		}
		System.out.print("The deposit "+ID_in+" was replenished by "+money+"\n");
		currentID = ID_in;
	}

	@Override
	public void unexecute() {
		Deposits.get(currentID).toWithdraw();
		
		try {
			String sqlCommand = "update User\"+UserID+\" set Deposited_money = "+0+" where DepositID = "+currentID+";";
			statement.execute(sqlCommand);
		}catch (SQLException e) {
			System.out.println("An error occurred:");
        	throw new RuntimeException(e);
		}

	}

}
