package com.github.dfc.server;

import com.github.dfc.Node;

public class Server extends Node{
	DataXceiverServer dataServer;
	public Server(String hostname, int port) {
		super(hostname, port);
		dataServer = new DataXceiverServer(hostname, port);
	}
	
	private void start() {
		dataServer.start();
	}

	public static void main(String[] args) {
		Server server = new Server("localhost", 50088);
		server.start();
	}
}