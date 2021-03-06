package org.i3xx.lemonade.lang.ext.util.f;

import java.util.ArrayList;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.i3xx.lemonade.lang.ext.util.f.json.JnContentResolver;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Parses JSON to the intern data struct
 * 
 * @author green
 *
 */
public class JsonParser implements Function {
	
	public static final String NAME = "json";
	
	@Override
	public Function getInstance() {
		return new JsonParser();
	}
	
	@Override
	public Node exec(Node node) throws Exception {
		
		LeafNode param = (LeafNode)node;
		String val = param.getValue();
		
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		try {
			obj = parser.parse(val);
		}catch(ParseException e) {
			throw new RuntimeException("The argument '"+val+"' is not a  valid JSON.", e);
		}
		
		ListNode temp = new ListNodeImpl(Type.NODE, Node.UNKNOWN, new ArrayList<Node>());
		
		JnContentResolver jr = new JnContentResolver(temp);
		jr.process(null, obj);
		
		//The first object node is appended, this is the variable, the 'unknown' node
		//will be thrown away.
		return temp;
	}

}
