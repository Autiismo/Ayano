package commands.general;

import java.util.function.Consumer;

import commands.Command;
import net.dv8tion.jda.core.entities.Message;
import utils.Settings;

public class Nick extends Command {

	public Nick() {
		super("nick", "Sends a request for your nickname to be changed. An admin will review the nickname and accept/deny it. This command does not need to be used if your role has the change nickname permission.");
	}

	@Override
	public void execute(Message message, String[] args) {
		if(args.length > 1) {
			String nickname = message.getContentRaw().substring(Settings.getPrefix().length() + this.getName().length() + 1);
			
			String adminMessage = message.getMember().getEffectiveName() + " would like to change their name to \"" + nickname + "\". React to this message to confirm or deny the request.";
			message.getGuild().getTextChannelsByName("admin", true).get(0).sendMessage(adminMessage).queue(new Consumer<Message>() {

				@Override
				public void accept(Message m) {
					m.addReaction("\uD83D\uDE02").queue();
					//m.addReaction("U+1F602").queue();
				}
				
			});
			
			String userMessage = "Your request to change your nickname has been sent and will be reviewed by an admin.";
		}
	}
	
}
