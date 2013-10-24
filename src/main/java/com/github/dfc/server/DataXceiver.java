package com.github.dfc.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.commons.compress.utils.IOUtils;

import com.github.dfc.protocol.DataTransferProtocol;
import com.github.dfc.protocol.DataTransferProtocol.Op;
import com.github.dfc.utils.NetUtils;
import com.github.io.Handler;

public class DataXceiver extends DataTransferProtocol.Receiver implements Handler {
	Socket clientSocket;
	DataInputStream in;
	DataOutputStream out;

	public DataXceiver(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		in = new DataInputStream(NetUtils.getInputStream(clientSocket));
		out = new DataOutputStream(NetUtils.getOutputStream(clientSocket));
		Op op;
		try {
			op = readOp(in);
			processOp(op, in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void opReadBlock(DataInputStream in, long blockId, long blockGs,
			long offset, long length, String client) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void opReadFile(DataInputStream in) {
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		try {
			String filePath = bin.readLine();
			File file = new File(filePath);
			FileInputStream fin = new FileInputStream(file);
			IOUtils.copy(fin, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void opWriteFile(DataInputStream in) {
		// TODO Auto-generated method stub
		
	}

}
