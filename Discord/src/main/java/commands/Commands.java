package commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

import commands.general.*;
import commands.management.*;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.Settings;

public class Commands extends ListenerAdapter {
	
	private static List<CommandGroup> commandGroups;
	
	public Commands() {
		commandGroups = new ArrayList<CommandGroup>();
		
		CommandGroup general = new CommandGroup("General", false);
		general.add(new Help());
		general.add(new Nick());
		commandGroups.add(general);
		
//		CommandGroup reddit = new CommandGroup("Reddit", false);
//		reddit.add(new DankMemes());
//		reddit.add(new Hentai());
//		commandGroups.add(reddit);
		
		CommandGroup management = new CommandGroup("Management", true);
		management.add(new Del());
		commandGroups.add(management);
		
//		CommandGroup music = new CommandGroup("Music (non functional)", false);
//		music.add(new Play());
//		commandGroups.add(music);
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if(!e.getAuthor().isBot()) {
			Message message = e.getMessage();
			String m = message.getContentDisplay();
			
			// Racial slurs check
			String lower = m.toLowerCase().trim();
			String shortened = lower.substring(0, 1);
			Character lastChar = lower.charAt(0);
			for(int i = 1; i < lower.length(); i++) {
				Character current = lower.charAt(i);
				if(current != lastChar) {
					shortened += current;
				}
				lastChar = current;
			}
			
			if(shortened.contains("niger") || shortened.contains("niga") || shortened.contains("kokujin")) {
				message.delete().queue();
			}
			
			if(m.startsWith(Settings.getPrefix())) {
				executeCommand(message);
			}
		}
	}
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		e.getChannel().sendMessage("isEmote: " + e.getReactionEmote().isEmote()).queue();
	}
	
	private boolean executeCommand(Message message) {
		String[] args = message.getContentDisplay().substring(Settings.getPrefix().length()).split("\\s");
		String command = args[0];
		for(CommandGroup group : commandGroups) {
			for(Command c : group.getCommands()) {
				if(c.getName().equalsIgnoreCase(command)) {
					String hour = ("00" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
					hour = hour.substring(hour.length() - 2);
					String min = ("00" + Calendar.getInstance().get(Calendar.MINUTE));
					min = min.substring(min.length() - 2);
					String second = ("00" + Calendar.getInstance().get(Calendar.SECOND));
					second = second.substring(second.length() - 2);
					System.out.println("[" + hour + ":" + min + ":" + second + "] " + message.getAuthor().getName() + " on " + message.getGuild().getName() + " - " + message.getContentDisplay());
					c.execute(message, args);
					return true;
				}
			}
		}
		return false;
	}
	
	public static List<CommandGroup> getCommandGroups() {
		return commandGroups;
	}

}
