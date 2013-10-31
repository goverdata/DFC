package com.github.dfc.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.github.dfc.HashAlgorithm;
import com.github.dfc.KetamaNodeLocator;
import com.github.dfc.Node;
import com.github.dfc.protocol.DataTransferProtocol;
import com.github.dfc.protocol.Op;

public class DFCClient {

	private static final Integer VIRTUAL_NODE_COUNT = 160;

	public InputStream read(String file) throws UnknownHostException,
			IOException {
		KetamaNodeLocator locator = new KetamaNodeLocator(HashAlgorithm.KETAMA_HASH, VIRTUAL_NODE_COUNT);
		Node server = locator.getNode(file);

		Socket socket = new Socket(server.getHostname(), server.getPort());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		// Write version(short)
		out.writeShort(DataTransferProtocol.DATA_TRANSFER_VERSION);
		// Write Op(byte)
		Op.READ_FILE.write(out);
		// Write file path
		//out.writeChars(file);
		byte[] filePathBytes = file.getBytes();
		out.write(filePathBytes.length);
		out.write(filePathBytes);
		
		out.flush();
		return socket.getInputStream();
	}
	
	public OutputStream open(String file) throws UnknownHostException,
	IOException {
		KetamaNodeLocator locator = new KetamaNodeLocator(HashAlgorithm.KETAMA_HASH, VIRTUAL_NODE_COUNT);
		Node server = locator.getNode(file);
		
		Socket socket = new Socket(server.getHostname(), server.getPort());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		// Write version(short)
		out.writeShort(DataTransferProtocol.DATA_TRANSFER_VERSION);
		// Write Op(byte)
		Op.WRITE_FILE.write(out);
		// Write file path
		//out.writeChars(file);
		byte[] filePathBytes = file.getBytes();
		out.write(filePathBytes.length);
		out.write(filePathBytes);
		
		return out;
	}
}
