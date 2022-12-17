package Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LogInCommand implements Command {
	private Statement statement;
	private int currentID;
	
	LogInCommand(Statement statement) {
		this.statement=statement;
		currentID = 0;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		String username_in = "";
		String password_in = "";
		do {
			System.out.print("Enter your username: ");
			username_in = scan.nextLine();
			System.out.print("Enter your password: ");
			password_in = scan.nextLine();
			String sqlCommand = "select Password from Users where Username = '"+username_in+"'";
			String password_check = "";
			try {
				ResultSet resultSet = statement.executeQuery(sqlCommand);
                while (resultSet.next()) {
                	password_check = resultSet.getString("Password");
                }
			}catch (SQLException e) {
				System.out.println("An error occurred:");
				throw new RuntimeException(e);
			}
			if(!password_in.equals(password_check)) {
				System.out.print("Login attempt failed");
				username_in = "";
				password_in = "";
			}
			
		}while(username_in.equals("")||password_in.equals(""));
		try {
			String sqlCommand = "select UserID from Users where Username = '"+username_in+"'";
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
            	this.currentID = resultSet.getInt("UserID");
            }
		}catch (SQLException e) {
			System.out.println("An error occurred:");
			throw new RuntimeException(e);
		}
		System.out.print("\nAccount login completed");
		System.out.print("\nWelcome "+username_in+"\n");
	}

	@Override
	public void unexecute() {
		currentID = 0;

	}
	
	public int getID() {
		return currentID;
	}

}
