package com.github.dfc;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.github.dfc.utils.Context;

public final class KetamaNodeLocator extends CommonNodeLocator{

	private TreeMap<Long, Node> ketamaNodes;
	private HashAlgorithm hashAlg;
	private int numReps = 160;
	public static final String SERVER_FILE = "server.properties";
	
	public KetamaNodeLocator(HashAlgorithm alg, int nodeCopies) {
		hashAlg = alg;
		ketamaNodes = new TreeMap<Long, Node>();
		numReps = nodeCopies;
		for (Node node : getNodes()) {
			for (int i = 0; i < numReps / 4; i++) {
				byte[] md5 = hashAlg.computeMd5(node.getHostname() + i);
				for (int h = 0; h < 4; h++) {
					long m = hashAlg.hash(md5, h);
					ketamaNodes.put(m, node);
				}
			}
		}
	}
	
	private List<Node> getNodes() {
		List<Node> nodes = new ArrayList<Node>();
		List<String> serverList = Context.loadFile(SERVER_FILE);
		for (String server : serverList) {
			String hostname = server.split(":")[0];
			int port = Integer.valueOf(server.split(":")[1]);
			Node node = new Node(hostname, port);
			nodes.add(node);
		}
		
		return nodes;
	}

	@Override
	public Node getNode(final String filePath) {
		Node rv = getNodeByKey(getPathHash(hashAlg, filePath));
		return rv;
	}

	private Node getNodeByKey(long hash) {
		final Node rv;
		Long key = hash;
		if (!ketamaNodes.containsKey(key)) {
			// SortedMap<Long, Node> tailMap = ketamaNodes.tailMap(key);
			// if (tailMap.isEmpty()) {
			// key = ketamaNodes.firstKey();
			// } else {
			// key = tailMap.firstKey();
			// }
			
			// For JDK1.6 version
			key = ketamaNodes.ceilingKey(key);
			if (key == null) {
				key = ketamaNodes.firstKey();
			}
		}
		rv = ketamaNodes.get(key);
		return rv;
	}
}
