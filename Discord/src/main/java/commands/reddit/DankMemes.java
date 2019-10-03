package commands.reddit;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import commands.Command;
import net.dv8tion.jda.core.entities.Message;
import utils.Settings;

public class DankMemes extends Command {

	public DankMemes() {
		super("dankmemes", "Sends a random/new/top posts from r/dankmemes");
	}

	@Override
	public void execute(Message message, String[] args) {
		if(args.length == 1) {
			message.getChannel().sendMessage(Settings.getPrefix() + getName() + " {random/new/top}").queue();
			return;
		}
		switch(args[1]) {
		case "random":
			getRandom(message);
			break;
		case "new":
			getNew(message);
			break;
		case "top":
			
			break;
		default:
			message.getChannel().sendMessage(Settings.getPrefix() + getName() + " {random/new/top}").queue();
			break;
		}
	}
	
	private boolean getRandom(Message message) {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.reddit.com/r/dankmemes/random.json").ignoreContentType(true).followRedirects(true).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String html = doc.outerHtml();
		html = html.replaceAll("\\s", "");
		String[] fragments = html.split(",");
		for(String f : fragments) {
			if(f.contains("\"score\"")) {
				String sub = f.substring(f.indexOf("score") + 7);
				if(Integer.parseInt(sub) > 100) {
					return sendRandom(message, fragments);
				} else {
					return getRandom(message);
				}
			}
		}
		return false;
	}
	
	private boolean sendRandom(Message message, String[] fragments) {
		for (String f : fragments) {
			if (f.contains("url")) {
				String sub = f.substring(f.indexOf("url") + 6);
				sub = sub.substring(0, sub.indexOf("\""));
				if (sub.contains("i.redd.it")) {
					message.getChannel().sendMessage(sub).queue();
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean getNew(Message message) {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.reddit.com/r/dankmemes/new").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements pics = doc.select("img");
		for(Element pic : pics) {
			String src = pic.attr("src");
			if(src.contains("redd.it")) {
				message.getChannel().sendMessage(src).queue();
				return true;
			}
		}
		return false;
	}
	
}
