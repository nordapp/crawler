package org.i3xx.data.crawler.lang.core;

public interface Function {
	
	/**
	 * @return
	 */
	public Function getInstance();
	
	/**
	 * Executes a function
	 * 
	 * @param node The parameter node
	 * @return The result node
	 */
	public Node exec(Node node, boolean fix) throws Exception;
}
