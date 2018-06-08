package org.i3xx.data.crawler.lang.util.f;

import java.util.Map;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.FunctionVars;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.NodeTools;
import org.i3xx.data.crawler.lang.util.StringTools;

public class ResolveVariables extends FunctionVars {
	
	public static final String NAME = "resolve";
	
	@Override
	public Function getInstance() {
		return new ResolveVariables();
	}

	@Override
	public Node exec(Node node, Map<String, Node> variables, boolean fix) throws Exception {
		
		if( ! (node instanceof LeafNode) )
			throw new IllegalArgumentException("Unable to resolve a structure node, only a leaf node is allowed '" +
					node.toString() + "");
		
		String text = ((LeafNode)node).getValue();
		
		//Move, if the text is a node name and the node is no LeafNode
		if(variables.containsKey(text)) {
			Node v = variables.get(text);
			if(v instanceof ListNode) {
				return NodeTools.newListNode(Type.NODE, fix?NAME:node.getName(), ((ListNode)v).getStruct().get(0));
			}
		}
		
		//Resolve
		
		for(int i=0;i<100;i++) {
			String resl = StringTools.replace(text, variables);
			if( text.equals(resl) )
				break;
			if(i==99)
				throw new IllegalArgumentException("The node "+node.getName()+" has stopped because of too much loops (recursion prohibited).");
			
			text = resl;
		}//for
		
		return new LeafNodeImpl(Type.NODE, fix?NAME:node.getName(), text);
	}

}
