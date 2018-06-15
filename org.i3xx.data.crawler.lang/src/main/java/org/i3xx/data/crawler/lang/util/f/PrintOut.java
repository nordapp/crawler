package org.i3xx.data.crawler.lang.util.f;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.Node;

/**
 * Print out a node of the intern data struct
 * 
 * @author green
 *
 */
public class PrintOut implements Function {
	
	public static final String NAME = "print";
	
	@Override
	public Function getInstance() {
		return this;
	}
	
	@Override
	public Node exec(Node node, boolean fix) throws Exception {
		
		System.out.println( node.toString() );
		
		return node;
	}
}
