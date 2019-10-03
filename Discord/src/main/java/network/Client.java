package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import network.packets.Packet;
import network.packets.Packet00Login;
import network.packets.Packet01Disconnect;
import network.packets.Packet02RequestConnectionStatus;
import network.packets.Packet03ReturnConnectionStatus;

public class Client extends Thread {
	
	InetAddress ipAddress;
	DatagramSocket socket;
	int port;
	
	boolean running;
	
	protected static Client client;
	
	public Client(String ipAddress) {
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
			this.port = 2604;
			client = this;
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void run() {
		while(running) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			data = packet.getData();
			Packet p = null;
			
			switch(data[0]) {
			case 0:
				p = new Packet00Login(packet);
				break;
			case 1:
				p = new Packet01Disconnect(packet);
				break;
			case 2:
				p = new Packet02RequestConnectionStatus(packet);
				break;
			case 3:
				p = new Packet03ReturnConnectionStatus(packet);
				break;
			}
			
			p.clientExecute();
		}
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Client getClient() {
		return client;
	}
	
}
