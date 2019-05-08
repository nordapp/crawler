package org.i3xx.lemonade.lang.util.func;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleResolver;

/**
 * More than one monade in a single line
 * 
 * @author green
 *
 */
public class Monades implements Function {
	
	public static final String NAME = "monades";

	@Override
	public Function getInstance() {
		return new Monades();
	}

	@Override
	public Node exec(Node node) throws Exception {
		
		if( ! (node instanceof ListNode) )
			throw new IllegalArgumentException("Unable to resolve a leaf node, only a structure node is allowed '" +
					node.toString() + "");
		
		ListNode list = (ListNode)node;
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		return n;
	}

}
