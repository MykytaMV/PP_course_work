package Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RegistrationCommand implements Command {
	private Statement statement;
	private int lastID;
	
	RegistrationCommand(Statement statement){
		this.statement = statement;
		this.lastID = 0;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		String username_in;
		String password_in;
		do {
			System.out.print("Enter your username: ");
			username_in = scan.nextLine();
			String sqlCommand = "select Username from Users where Username = '"+username_in+"';";
			String username_check = "";
			try {
                ResultSet resultSet = statement.executeQuery(sqlCommand);
                while (resultSet.next()) {
                	username_check = resultSet.getString("Username");
                }
			}catch (SQLException e) {
				System.out.println("An error occurred:");
				throw new RuntimeException(e);
			}
			if(username_in.equals(username_check)) {
				System.out.print("This Username already exist\n");
				username_in = "";
			}

			}while(username_in.equals(""));
		
		do {
			System.out.print("Enter your password: ");
			password_in = scan.nextLine();
			System.out.print("Confirm your password: ");
			String password_conf = scan.nextLine();
			if(!password_in.equals(password_conf)) {
				System.out.print("Password not confirmed, please try again\n");
				password_in = "";
			}
		}while(password_in.equals(""));
		
		try {
			String sqlCommand = "insert into Users (Username, Password) values ('"+username_in+"','"+password_in+"');";
			statement.execute(sqlCommand);
		}catch (SQLException e) {
			System.out.println("An error occurred:");
        	throw new RuntimeException(e);
		}
		
		try {
			String sqlCommand = "select UserID from Users where Username = '"+username_in+"';";
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
            	this.lastID = resultSet.getInt("UserID");
            }
		}catch (SQLException e) {
			System.out.println("An error occurred:");
			throw new RuntimeException(e);
		}
		
		try {
			String sqlCommand = "create table User"+lastID+" (DepositID INT not null, Deposited_money INT not null, Months_left int null);";
			statement.execute(sqlCommand);
		}catch (SQLException e) {
			System.out.println("An error occurred:");
			throw new RuntimeException(e);
		}
		System.out.print("\nAccount created successfully!\n");
		
	}
	@Override
	public void unexecute() {
		System.out.print("Are you sure you want to delete last created account? if yes - 0");
		Scanner scan = new Scanner(System.in);
		int inp = scan.nextInt();
		if(inp!=0)
			return;
		else {
			try {
				String sqlCommand = "delete from Users where UserID = "+lastID+";";
				statement.execute(sqlCommand);
			}catch (SQLException e) {
	            System.out.println("An error occurred:");
	            throw new RuntimeException(e);
	        }
			try {
				String sqlCommand = "drop table User"+lastID+";";
				statement.execute(sqlCommand);
			}catch (SQLException e) {
	            System.out.println("An error occurred:");
	            throw new RuntimeException(e);
	        }
		}

	}
	
	public int getID() {
		return lastID;
	}

}
