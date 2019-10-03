package commands.general;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

import commands.Command;
import commands.CommandGroup;
import commands.Commands;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import utils.Settings;

public class Help extends Command {

	public Help() {
		super("help", "Lists all the commands you can tell me to do");
	}
	
	public void execute(Message message, String[] args) {
		List<CommandGroup> commandGroups = Commands.getCommandGroups();
		
		final String publicMessage = "Accessible commands have been sent to your DM's.";
		
		message.getAuthor().openPrivateChannel().queue(new Consumer<PrivateChannel>() {

			@Override
			public void accept(PrivateChannel channel) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.red);
				eb.setTitle("Ayano commands");
				eb.setDescription("Prefix - " + Settings.getPrefix() + System.lineSeparator() +
								  "For information about a specific command type \"" + Settings.getPrefix() + "help <command>\"" + System.lineSeparator() +
								  "Example - \"" + Settings.getPrefix() + "help dankmemes\"" + System.lineSeparator()
								  );
				
				for(CommandGroup group : commandGroups) {
					if(group.getAdmin() && !message.getMember().hasPermission(Permission.ADMINISTRATOR)) {
						continue;
					}
					
					String commands = "";
					for(Command c : group.getCommands()) {
						commands += c.getName() + System.lineSeparator();
					}
					
					eb.addField(group.getName(), commands, true);
				}
				
				channel.sendMessage(eb.build()).queue();
			}
			
		});
		message.getChannel().sendMessage(publicMessage).queue();
	}

}
