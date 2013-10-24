package com.github.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.io.Handler;
import com.github.io.Server;

public abstract class BioTcpServer implements Server{
	ExecutorService executor;
	ServerSocket serverSocket;
	int poolSize = 3;
	public BioTcpServer(String hostname, int port){
		int core = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(core * poolSize);
		try {
			serverSocket = new ServerSocket();
			SocketAddress aaa = new InetSocketAddress(hostname, port);
			serverSocket.bind(aaa);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
		//TODO
	}
	
	@Override
	public void start(){
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				executor.execute(getHandler(socket));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public abstract Handler getHandler(Socket socket);
}