package commands.management;

import java.util.List;
import java.util.function.Consumer;

import commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;

public class Del extends Command {
	
	private int deleted = 0;

	public Del() {
		super("del", "Deletes the last post/last n number of posts from the bot. Requires MESSAGE_MANAGE permission.");
	}

	@Override
	public void execute(Message message, String[] args) {
		if(message.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
			if(args.length == 1) {
				deleteMessages(message, 1);
			} else {
				try {
					int num = Integer.parseInt(args[1]);
					deleteMessages(message, num);
				} catch(NumberFormatException e) {
					deleteMessages(message, 1);
				}
			}
		}
	}
	
	private boolean deleteMessages(Message message, int num) {
		deleted = 0;
		MessageHistory history = message.getChannel().getHistory();
		history.retrievePast(100).queue(new Consumer<List<Message>>() {

			@Override
			public void accept(List<Message> messages) {
				for (Message message : messages) {
					if (message.getAuthor().isBot() && message.getAuthor().getId().equals("384081557813592066")) {
						message.delete().queue();
						deleted++;
					}
					if (deleted >= num)
						break;
				}
			}

		});
		return (deleted >= num);
	}
	
}
