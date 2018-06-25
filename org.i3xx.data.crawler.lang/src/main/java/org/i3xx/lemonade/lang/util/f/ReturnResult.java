package org.i3xx.lemonade.lang.util.f;

import java.util.Map;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.FunctionVars;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.Node;

/**
 * Parses a variable and replace every placeholder by it'data from
 * the variable map.
 * 
 * @author green
 *
 */
public class ReturnResult extends FunctionVars {
	
	public static final String NAME = "return";
	
	@Override
	public Function getInstance() {
		return new ReturnResult();
	}

	@Override
	public Node exec(Node node, Map<String, Node> variables) throws Exception {
		
		if( ! (node instanceof LeafNode) )
			throw new IllegalArgumentException("Unable to return a structure node, only a leaf node is allowed '" +
					node.toString() + "");
		
		//Move, if the text is a node name and the node is no LeafNode
		if(variables.containsKey( node.getName() )) {
			return node;			
		}
		
		throw new IllegalArgumentException("The argument is not a variable '"+node.getName()+"'. Perhaps use resolve.");
	}

}
