package com.github.dfc.server;

import java.net.Socket;

import com.github.io.Handler;
import com.github.io.bio.BioTcpServer;

public class DataXceiverServer extends BioTcpServer{
	private int poolSize = 2;
	
	public DataXceiverServer(String hostname, int port) {
		super(hostname, port);
	}

	@Override
	public Handler getHandler(Socket socket){
		return new DataXceiver(socket);
	}

	@Override
	public int getPoolSize() {
		return poolSize;
	}
}
