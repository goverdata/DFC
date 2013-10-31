package com.github.dfc;

public abstract class CommonNodeLocator implements NodeLocator{
	
	public static long getPathHash(HashAlgorithm alg, final String path){
		byte[] digest = alg.computeMd5(path);
		return alg.hash(digest, 0);
	}
}
