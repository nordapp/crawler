package org.i3xx.data.crawler.lang.util.f.json;

import java.util.ArrayList;
import java.util.List;

import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.ListNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.NodeTools;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JnContentResolver {
	
	private Node node;
	
	public JnContentResolver(Node node) {
		this.node = node;
	}
	
	/**
	 * @param name
	 * @param arg
	 */
	public void process(String name, Object arg) {
		
		if( arg == null) {
			//does nothing
		}//fi
		
		else if(arg instanceof JSONObject) {
			JSONObject obj = (JSONObject)arg;
			for(Object k : obj.keySet()) {
				String key = (String)k;
				Object val = obj.get(key);
				
				if(val instanceof JSONObject) {
					child(key, node).process(key, val);
				}else {
					process(key, val);
				}
			}//for
		}//fi
		
		else if(arg instanceof JSONArray) {
			JSONArray arr = (JSONArray)arg;
			for(int i=0;i<arr.size();i++)  {
				Object val = arr.get(i);
				process(null, val);
			}//for
		}//fi
		
		else if(arg instanceof JSONValue) {
			JSONValue val = (JSONValue)arg;
			String v = JSONValue.toJSONString(val);
			append(name, v, node);
		}//fi
		
		else if(arg instanceof String) {
			append(name, arg.toString(), node);
		}
		
		else if(arg instanceof Long) {
			append(name, arg.toString(), node);
		}
		
		else if(arg instanceof Double) {
			append(name, arg.toString(), node);		}
	}
	
	/**
	 * @param name
	 * @param value
	 * @param parent
	 */
	private void append(String name, String value, Node parent) {
		
		ListNode node = (ListNode)parent;
		List<Node> list = NodeTools.makeModifyable(node.getStruct());
		
		LeafNode child = new LeafNodeImpl(Type.NODE, name, value);
		list.add(0, child);
		node.setStruct(list);
	}
	
	/**
	 * @param node
	 * @return
	 */
	private JnContentResolver child(String name, Node parent) {
		
		ListNode node = (ListNode)parent;
		List<Node> list = NodeTools.makeModifyable(node.getStruct());
		
		ListNode child = new ListNodeImpl(Type.NODE, name, new ArrayList<Node>());
		list.add(child);
		node.setStruct(list);
		
		return new JnContentResolver(child);
	}
}
