package org.i3xx.lemonade.lang.util.func;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.util.NodeTools;

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
	public Node exec(Node node) throws Exception {
		
		System.out.println( node.toString() );
		
		return NodeTools.createUnknown(node);
	}
}
