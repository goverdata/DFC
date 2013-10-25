package com.github.dfc.server;

import com.github.dfc.Node;

public class DataNode extends Node{
	DataXceiverServer dataServer;
	public DataNode(String hostname, int port) {
		super(hostname, port);
		dataServer = new DataXceiverServer(hostname, port);
	}
	
	private void start() {
		dataServer.start();
	}

	public static void main(String[] args) {
		DataNode server = new DataNode("localhost", 50088);
		server.start();
	}
}