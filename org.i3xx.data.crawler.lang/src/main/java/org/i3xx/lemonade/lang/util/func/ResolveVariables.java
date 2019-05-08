package org.i3xx.lemonade.lang.util.func;

import java.util.Map;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.FunctionVars;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.util.NodeTools;
import org.i3xx.lemonade.lang.util.StringTools;

/**
 * Parses a variable and replace every placeholder by it'data from
 * the variable map.
 * 
 * @author green
 *
 */
public class ResolveVariables extends FunctionVars {
	
	public static final String NAME = "resolve";
	
	@Override
	public Function getInstance() {
		return new ResolveVariables();
	}

	@Override
	public Node exec(Node node, Map<String, Node> variables) throws Exception {
		
		if( ! (node instanceof LeafNode) )
			throw new IllegalArgumentException("Unable to resolve a structure node, only a leaf node is allowed '" +
					node.toString() + "");
		
		String text = ((LeafNode)node).getValue();
		
		//Resolve
		for(int i=0;i<100;i++) {
			String resl = StringTools.replace(text, variables);
			if( text.equals(resl) )
				break;
			if(i==99)
				throw new IllegalArgumentException("The node "+node.getName()+" has stopped because of too much loops (recursion prohibited).");
			
			text = resl;
		}//for
		
		//variable
		return NodeTools.createUnknownLeaf(node, text, variables);
	}

}
