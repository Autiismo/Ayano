package commands.music;

import commands.Command;
import net.dv8tion.jda.core.entities.Message;

public class Play extends Command {

	public Play() {
		super("play", "Searches for a song on youtube then plays it in the current channel");
	}

	@Override
	public void execute(Message message, String[] args) {
		
	}
	
}
