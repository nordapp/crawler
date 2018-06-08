package org.i3xx.data.crawler.lang.core;

public interface Node {
	
	public enum Type { NODE, FIX, SET }
	
	Type getType();
	
	String getName();
}
