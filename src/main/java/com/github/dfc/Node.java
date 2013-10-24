package com.github.dfc;

public class Node {
	private String hostname;
	private int port;
	public Node(String name){
		this.hostname = name;
	}
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String name) {
		this.hostname = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
