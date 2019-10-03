package commands.reddit;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import commands.Command;
import net.dv8tion.jda.core.entities.Message;

public class Hentai extends Command {

	public Hentai() {
		super("hentai", "Sends a random post from r/hentai");
	}

	@Override
	public void execute(Message message, String[] args) {
		getRandom(message);
		//search(message);
		//TODO: search 4chan/h, imgur(if anything good), deviant art(if anything good), reddit search page or r/hentai(over n upvotes, withing past x time)
		//TODO: set up database to store per guild config files (prefix, etc), database cache system which queues for more pictures every n interval of time and tries to combat against duplicates (store link would be easiest and then
		// compare) and mostly send stuff out of the local cached files, store how many times each file is liked/disliked(deleted also) to only show more of the liked stuff(can be guild specific for targeting)
	}
	
	private boolean getRandom(Message message) {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.reddit.com/r/hentai/random.json").ignoreContentType(true).followRedirects(true).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String html = doc.outerHtml();
		html = html.replaceAll("\\s", "");
		String[] fragments = html.split(",");
		for(String f : fragments) {
			if(f.contains("\"score\"")) {
				String sub = f.substring(f.indexOf("score") + 7);
				if(Integer.parseInt(sub) > 2) {
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
				if (sub.contains("i.")) {
					if(!sub.endsWith(".gif")) {
						message.getChannel().sendMessage(sub).queue();
						return true;
					} else {
						getRandom(message);
					}
				}
			}
		}
		return false;
	}
	
	private boolean search(Message message) {
		WebClient web = new WebClient(BrowserVersion.FIREFOX_60);
		web.getOptions().setJavaScriptEnabled(true);
		web.getOptions().setThrowExceptionOnScriptError(true);
		web.waitForBackgroundJavaScript(5000);
		HtmlPage initial = null;
		try {
			initial = web.getPage("https://www.reddit.com/r/hentai/");
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		web.close();
		
		return true;
	}
	
}
