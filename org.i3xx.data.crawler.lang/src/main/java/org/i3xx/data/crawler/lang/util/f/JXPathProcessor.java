package org.i3xx.data.crawler.lang.util.f;

import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.FunctionVars;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.f.json.ScratchPointerFactory;

/**
 * JXPath processing of the intern data struct. Used to put a specified
 * node to the variable map.
 *  
 * @author green
 *
 */
public class JXPathProcessor extends FunctionVars {
	
	public static final String NAME = "jxpath";
	
	@Override
	public Function getInstance() {
		return new JXPathProcessor();
	}

	@Override
	public Node exec(Node node, Map<String, Node> variables, boolean fix) throws Exception {
		
		LeafNode param = (LeafNode)node;
		
		String path = param.getValue();
		String key = param.getName();
		
		Node val = variables.get(key);
		if(val==null)
			throw new IllegalArgumentException("The variable '"+key+"' is not available.");
		
		JXPathContextReferenceImpl.addNodePointerFactory(new ScratchPointerFactory());
		JXPathContext c = JXPathContext.newContext( val );
		
		Object value = c.getValue(path);
		
		String result = String.valueOf( value );
		if(result==null)
			throw new IllegalArgumentException("The path '"+path+"' is not available.");
		
		return new LeafNodeImpl(Type.NODE, fix?NAME:node.getName(), result);
	}

}
