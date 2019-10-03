package network.packets;

import java.net.DatagramPacket;
import java.util.Arrays;

import network.Client;
import network.Server;
import network.User;

public class Packet03ReturnConnectionStatus extends Packet {

	public Packet03ReturnConnectionStatus(String username) {
		super((byte) 3, username.getBytes());
	}
	
	public Packet03ReturnConnectionStatus(DatagramPacket packet) {
		super(packet);
	}

	@Override
	public void serverExecute() {
		server = Server.getServer();
		byte[] data = p.getData();
		String username = new String(Arrays.copyOfRange(data, 1, data.length)).trim();
		User user = server.getUserByName(username);
		Packet03ReturnConnectionStatus packet = new Packet03ReturnConnectionStatus(server.getUserByIP(p.getAddress(), p.getPort()).getName());
		server.sendToUser(packet.getData(), user);
	}

	@Override
	public void clientExecute() {
		client = Client.getClient();
	}
	
	
	
}
