package com.github.dfc.locator;

import com.github.dfc.CacheNode;

public interface NodeLocator {
	public CacheNode getCacheNode(final String filePath);
}
