package com.wtx.riptide;

import java.net.Proxy;

import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

public class BotObject {

	private String username, password;

	private Client client;

	public BotObject(String username, String password, String serverAddress, int port, Proxy proxy)
			throws RequestException {
		this.username = username;
		this.password = password;
		client = new Client(serverAddress, port, new MinecraftProtocol(username, password, false),
				new TcpSessionFactory(proxy));
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Client getClient() {
		return client;
	}

	public Session getSession() {
		return client.getSession();
	}

	public void addClientListener(SessionListener listener) {
		this.getSession().addListener(listener);
	}

}
