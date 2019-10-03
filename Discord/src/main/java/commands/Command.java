package commands;

import net.dv8tion.jda.core.entities.Message;

public abstract class Command {
	
	private final String name;
	private final String description;

	public Command(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public abstract void execute(Message message, String[] args);

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}
