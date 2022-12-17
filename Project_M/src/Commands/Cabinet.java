package Commands;

import java.sql.Statement;

public class Cabinet{
	private Statement statement;
	private int currentID;
	private RegistrationCommand registrationCommand;
	private LogInCommand logInCommand;
	
	
	public Cabinet(Statement statement) {
		this.statement = statement;
		registrationCommand = new RegistrationCommand(statement);
		logInCommand = new LogInCommand(statement);
	}
	
	public int createAccount() {
		registrationCommand.execute();
		currentID = registrationCommand.getID();
		return currentID;
	}
	
	public int logInAccount() {
		logInCommand.execute();
		currentID = logInCommand.getID();
		return currentID;
	}
	
	

}
