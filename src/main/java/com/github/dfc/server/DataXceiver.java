package com.github.dfc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.github.dfc.protocol.DataTransferProtocol;
import com.github.dfc.protocol.Op;
import com.github.dfc.utils.IOUtils;
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
		try {
			Op op = readOp(in);
			processOp(op, in);
		} catch (IOException e) {
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
		try {
			int filePathLength = in.read();
			byte[] filePathBuf = new byte[filePathLength];
			in.read(filePathBuf, 0 , filePathLength);
			
			String filePath = new String(filePathBuf);
			File file = new File(filePath);
			FileInputStream fin = new FileInputStream(file);
			IOUtils.copyBytes(fin, out, 1024, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void opWriteFile(DataInputStream in) {
		try {
			int filePathLength = in.read();
			byte[] filePathBuf = new byte[filePathLength];
			in.read(filePathBuf, 0 , filePathLength);
			
			String filePath = new String(filePathBuf);
			File file = new File(filePath);
			if(file.exists()){
				return;
			}
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			file.createNewFile();
			IOUtils.copyBytes(in, new FileOutputStream(file), 1024, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
