package org.i3xx.data.crawler.lang.util.f;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;

public class UnpackLiteral implements Function {
	
	public static final String NAME = "offliteral";
	
	@Override
	public Function getInstance() {
		return new UnpackLiteral();
	}
	
	@Override
	public Node exec(Node node, boolean fix) throws Exception {
		
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
		
		return new LeafNodeImpl(Type.NODE, fix?NAME:node.getName(), text);
	}

}
