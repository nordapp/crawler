package org.i3xx.lemonade.lang.core;

public interface Function {
	
	/**
	 * @return
	 */
	public Function getInstance();
	
	/**
	 * Executes a function
	 * 
	 * @param node The parameter node, this is the value of the tuple.
	 * @return The result node
	 */
	public Node exec(Node node) throws Exception;
}
