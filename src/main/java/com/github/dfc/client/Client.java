package com.github.dfc.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.dfc.HashAlgorithm;
import com.github.dfc.KetamaNodeLocator;
import com.github.dfc.Node;
import com.github.dfc.protocol.DataTransferProtocol;

public class Client {
static Random ran = new Random();
	
	/** key's count */
	private static final Integer EXE_TIMES = 100000;
	
	private static final Integer NODE_COUNT = 5;
	
	private static final Integer VIRTUAL_NODE_COUNT = 160;
	
	private List<Node> getNodes(int nodeCount) {
		List<Node> nodes = new ArrayList<Node>();
		
		for (int k = 1; k <= nodeCount; k++) {
			Node node = new Node("node" + k);
			nodes.add(node);
		}
		
		return nodes;
	}
	
	/*private List<String> getAllStrings() {
		List<String> allStrings = new ArrayList<String>(EXE_TIMES);
		
		for (int i = 0; i < EXE_TIMES; i++) {
			allStrings.add(generateRandomString(ran.nextInt(50)));
		}
		
		return allStrings;
	}
	
	private String generateRandomString(int length) {
		StringBuffer sb = new StringBuffer(length);
		
		for (int i = 0; i < length; i++) {
			sb.append((char) (ran.nextInt(95) + 32));
		}
		
		return sb.toString();
	}*/

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client test = new Client();
		
		/** Records the times of locating node*/
		//Map<Node, Integer> nodeRecord = new HashMap<Node, Integer>();
		
		List<Node> allNodes = test.getNodes(NODE_COUNT);
		KetamaNodeLocator locator = new KetamaNodeLocator(allNodes, HashAlgorithm.KETAMA_HASH, VIRTUAL_NODE_COUNT);
		Node server = locator.getPrimary("/tmp/johnny/11122.txt");
		System.out.println("Node name :" + server.getHostname());
		
		
		Socket socket = new Socket(server.getHostname(), server.getPort());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.write(DataTransferProtocol.VERSION);
		DataTransferProtocol.Op.READ_FILE.write(out);
		out.write("/tmp/johnny/11122.txt".getBytes());
		out.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line = reader.readLine();
		while(line != null){
			System.out.println(line);
			line = reader.readLine();
		}
	}
}
