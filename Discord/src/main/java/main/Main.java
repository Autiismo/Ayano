package main;

import javax.security.auth.login.LoginException;

import commands.Commands;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import network.Client;
import network.packets.Packet00Login;
import network.packets.Packet01Disconnect;
import utils.Settings;

public class Main {
	
	private static Main main;
	
	private Client client;
	
	private static final String TOKEN = "Mzg0MDgxNTU3ODEzNTkyMDY2.Dy1ScA.Iwd9jdzcu1_Ay0Xct8iIDU9gaHg";
	
	public static void main(String[] args) throws LoginException {
		JDABuilder jda = new JDABuilder(AccountType.BOT)
				.setToken(TOKEN)
				.addEventListener(new Commands())
				.setGame(Game.playing(Settings.getPrefix() + "help"));
		jda.build();
		
		main = new Main();
	}
	
	public Main() {
		init();
		
		//Packet00Login login = new Packet00Login("ayano");
		//client.sendData(login.getData());
		
		//new DatabaseConnection();
	}

	private void init() {
		client = new Client("localhost");
		client.start();
		client.setRunning(true);
	}
	
	public void exit() {
		if(client != null) {
			client.setRunning(false);
		}
		
		Packet01Disconnect disconnect = new Packet01Disconnect();
		client.sendData(disconnect.getData());
		
		System.exit(0);
	}

	public static Main getMain() {
		return main;
	}
	
}
