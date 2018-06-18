package org.i3xx.data.crawler.lang.core;

public interface Node {
	
	/**
	 * The Types of nodes
	 * 
	 * @author green
	 */
	public enum Type { NODE, SET, RESOLVE }
	
	/**
	 * The name of a node created by a function
	 */
	public static final String UNKNOWN = "unknown";
	
	/**
	 * 
	 * @return
	 */
	Type getType();
	
	/**
	 * 
	 * @return
	 */
	String getName();
}
