package com.github.dfc.server;

import java.net.Socket;

import com.github.io.Handler;

public class DataXceiver implements Handler{
	Socket clientSocket;
	
	public DataXceiver(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		
	}

}
