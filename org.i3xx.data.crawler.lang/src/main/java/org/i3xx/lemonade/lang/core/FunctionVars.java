package org.i3xx.lemonade.lang.core;

import java.util.Map;

public abstract class FunctionVars implements Function {
	
	@Override
	public Function getInstance() {
		return this;
	}
	
	@Override
	public Node exec(Node node) throws Exception {
		throw new UnsupportedOperationException("The function need the variable map.");
	}
	
	/**
	 * @param node The parameter node, this is the value of the tuple.
	 * @param variables The variable mapping
	 * @return
	 * @throws Exception
	 */
	public abstract Node exec(Node node, Map<String, Node> variables) throws Exception;
}
