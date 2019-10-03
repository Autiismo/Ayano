package commands;

import java.util.ArrayList;
import java.util.List;

public class CommandGroup {
	
	private String name;
	private List<Command> commands;
	private boolean admin;
	
	public CommandGroup(String name, boolean admin) {
		this.name = name;
		commands = new ArrayList<Command>();
		this.admin = admin;
	}
	
	public void add(Command command) {
		commands.add(command);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
}
