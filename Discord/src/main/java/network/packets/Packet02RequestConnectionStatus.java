package network.packets;

import java.net.DatagramPacket;
import java.util.Arrays;

import network.Client;
import network.Server;
import network.User;

public class Packet02RequestConnectionStatus extends Packet {

	public Packet02RequestConnectionStatus(String username) {
		super((byte) 2, username.getBytes());
	}
	
	public Packet02RequestConnectionStatus(DatagramPacket packet) {
		super(packet);
	}

	@Override
	public void serverExecute() {
		server = Server.getServer();
		byte[] data = p.getData();
		String username = new String(Arrays.copyOfRange(data, 1, data.length)).trim();
		User user = server.getUserByName(username);
		Packet02RequestConnectionStatus packet = new Packet02RequestConnectionStatus(server.getUserByIP(p.getAddress(), p.getPort()).getName());
		server.sendToUser(packet.getData(), user);
	}

	@Override
	public void clientExecute() {
		client = Client.getClient();
		String username = new String(Arrays.copyOfRange(p.getData(), 1, p.getLength()));
		Packet03ReturnConnectionStatus packet = new Packet03ReturnConnectionStatus(username);
		client.sendData(packet.getData());
		
	}
	
}
