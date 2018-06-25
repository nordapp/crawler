package org.i3xx.lemonade.lang.util.f;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;
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
		
		if(node instanceof ListNode) {
			return NodeTools.newListNode(Type.NODE, Node.UNKNOWN, ((ListNode)node).getStruct().get(0));
		}else {
			return new LeafNodeImpl(Type.NODE, Node.UNKNOWN, ((LeafNode)node).getValue());
		}
	}
}
