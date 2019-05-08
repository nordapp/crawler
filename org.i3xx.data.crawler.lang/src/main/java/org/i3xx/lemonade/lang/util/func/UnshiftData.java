package org.i3xx.lemonade.lang.util.func;

import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;

/**
 * Print out a node of the intern data struct
 * 
 * @author green
 *
 */
public class UnshiftData implements Function {
	
	public static final String NAME = "unshift";
	
	@Override
	public Function getInstance() {
		return this;
	}
	
	@Override
	public Node exec(Node node) throws Exception {
		
		Node temp = node;
		
		if(node instanceof DataNode) {
			Object object = ((DataNode)node).getData();
			String text = ((LeafNode)node).getValue();
			
			if(object instanceof String) {
				text = (String)object;
			}else if(object instanceof Number) {
				text = ((Number)object).toString();
			}else if(object instanceof Boolean) {
				text = ((Boolean)object).toString();
			}else if(object instanceof LeafNode) {
				text = ((LeafNode)object).getValue();
			}
			
			temp = new LeafNodeImpl(Type.NODE, node.getName(), text);
		}
		
		return temp;
	}
}
