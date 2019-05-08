package org.i3xx.lemonade.lang.util.func;

import java.util.Map;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.FunctionVars;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.util.NodeTools;

public class UnpackLiteral extends FunctionVars {
	
	public static final String NAME = "offliteral";
	
	@Override
	public Function getInstance() {
		return new UnpackLiteral();
	}
	
	@Override
	public Node exec(Node node, Map<String, Node> variables) throws Exception {
		
		LeafNode param = (LeafNode)node;
		String text = param.getValue();
		
		if(text!=null) {
			text = text.trim();
			
			//removes the first and last char if they are equal.
			if(text.length()>1) {
				char c = text.charAt(0);
				if(text.charAt(text.length()-1)==c)
					text = text.substring(1, text.length()-1);
				
			}
		}//fi
		
		return NodeTools.createUnknownLeaf(node, text, variables);
	}

}
