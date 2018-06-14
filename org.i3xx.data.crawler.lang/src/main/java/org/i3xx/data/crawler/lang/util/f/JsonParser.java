package org.i3xx.data.crawler.lang.util.f;

import java.util.ArrayList;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.ListNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.f.json.JnContentResolver;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser implements Function {
	
	public static final String NAME = "json";
	
	@Override
	public Function getInstance() {
		return new JsonParser();
	}
	
	@Override
	public Node exec(Node node, boolean fix) throws Exception {
		
		LeafNode param = (LeafNode)node;
		String val = param.getValue();
		
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		try {
			obj = parser.parse(val);
		}catch(ParseException e) {
			throw new RuntimeException("The argument '"+val+"' is not a  valid JSON.", e);
		}
		
		ListNode temp = new ListNodeImpl(Type.NODE, fix?NAME:node.getName(), new ArrayList<Node>());
		
		JnContentResolver jr = new JnContentResolver(temp);
		jr.process(null, obj);
		
		//The first object node is appended, this is the variable
		return temp;
	}

}
