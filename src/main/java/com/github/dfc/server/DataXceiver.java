package com.github.dfc.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		/*String msg = null;
		BufferedReader reader;
		try {
			reader = IOUtils.wrappedReader(clientSocket.getInputStream());
			while((msg = reader.readLine()) != null){
				System.out.println(msg);
			}
			return;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		in = new DataInputStream(NetUtils.getInputStream(clientSocket));
		out = new DataOutputStream(NetUtils.getOutputStream(clientSocket));
		try {
			/*in.mark(1000);
			System.out.println(in.readShort());
			System.out.println(in.readByte());
			in.reset();*/
//			final short version = in.readShort();
//			System.out.println(version);
			Op op = readOp(in);
//			byte xxx = in.readByte();
//			Op op = Op.valueOf(xxx);
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
			IOUtils.copy(fin, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void opWriteFile(DataInputStream in) {
		// TODO Auto-generated method stub
		
	}

}
