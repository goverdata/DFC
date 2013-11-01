package com.github.dfc.locator;

import com.github.dfc.HashAlgorithm;

public abstract class CommonNodeLocator implements NodeLocator{
	
	public static long getPathHash(HashAlgorithm alg, final String path){
		byte[] digest = alg.computeMd5(path);
		return alg.hash(digest, 0);
	}
}
