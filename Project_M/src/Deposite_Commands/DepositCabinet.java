package Deposite_Commands;

import java.sql.Statement;
import java.util.HashMap;

import Deposits.Deposit;

public class DepositCabinet {
	private Statement statement;
	private HashMap<Integer, Deposit> Deposits;
	private int UserID;
	private TakeDepositeCommand takeDepositeCommand; 
	private RemoveDepositCommand removeDepositCommand;
	private TopUpDepositCommand topUpDepositCommand;
	private WithdrawDepositCommand withdrawDepositCommand;
	private WaitDepositCommand waitDepositCommand;
	private ShowMyDepositCommand showMyDepositCommand;
	private ShowAndSortAllDeposirCommand showAndSortAllDeposirCommand;
	
	public DepositCabinet(Statement statement, int UserID){
		this.statement=statement;
		this.UserID = UserID;
		this.Deposits = new HashMap<>();
		takeDepositeCommand = new TakeDepositeCommand(statement, UserID, Deposits);
		removeDepositCommand = new RemoveDepositCommand(statement, UserID, Deposits);
		topUpDepositCommand = new TopUpDepositCommand(statement, UserID, Deposits);
		withdrawDepositCommand = new WithdrawDepositCommand(statement, UserID, Deposits);
		waitDepositCommand = new WaitDepositCommand(statement, UserID, Deposits);
		showMyDepositCommand = new ShowMyDepositCommand(statement, UserID, Deposits);
		showAndSortAllDeposirCommand = new ShowAndSortAllDeposirCommand(statement);
	}
	
	public void showDeposits() {
		showAndSortAllDeposirCommand.execute();
	}
	
	public void takeDeposit() {
		takeDepositeCommand.execute();
	}
	
	public void removeDeposit() {
		removeDepositCommand.execute();
	}
	
	public void topUpDeposit() {
		topUpDepositCommand.execute();
	}
	
	public void withdrawDeposit() {
		withdrawDepositCommand.execute();
	}
	
	public void waitDeposit() {
		waitDepositCommand.execute();
	}
	
	public void showMyDeposit() {
		showMyDepositCommand.execute();
	}

}
