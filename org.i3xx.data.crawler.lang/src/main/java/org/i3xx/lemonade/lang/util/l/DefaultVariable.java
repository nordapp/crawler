package org.i3xx.lemonade.lang.util.l;

import java.util.Map;

import org.i3xx.lemonade.lang.core.Accent;
import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.DataNodeImpl;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.Node;

public class DefaultVariable {
	
	private String name;
	private Map<String, Node> variables;
	
	public DefaultVariable(String name, Map<String, Node> variables) {
		this.name = name;
		this.variables = variables;
	}
	
	/**
	 * @return
	 */
	public Object getValue() {
		Node node = variables.get(name);
		if(node instanceof DataNode)
			return ((DataNode)node).getData();
		else if(node instanceof LeafNode)
			return ((LeafNode)node).getValue();
		else if(node instanceof ListNode)
			return ((ListNode)node).getStruct().get(0);
		else
			throw new IllegalArgumentException("The node '"+name+"' has not a valid type.");
	}
	
	/**
	 * @param value
	 */
	public void setValue(Object value) {
		if(name.charAt(0)!=Accent.CHANGE)
			throw new UnsupportedOperationException("The variable '"+name+"' is unmodifyable."); 
		
		//do not set a DefaultVariable
		if(value instanceof DefaultVariable)
			value = ((DefaultVariable)value).getValue();
		
		Node node = variables.get(name);
		if(node instanceof DataNode) {
			variables.put(name,
					new DataNodeImpl(node.getType(), node.getName(),
							((DataNode)node).getValue(), value));
		}else if(node instanceof LeafNode) {
			variables.put(name,
					new LeafNodeImpl(node.getType(), node.getName(),
							String.valueOf(value) ));
		}else if(node instanceof ListNode) {
			if(node instanceof ListNode) {
				((ListNode)node).getStruct().set(0, (ListNode)value);
			}else {
				throw new IllegalArgumentException("The value to set '"+name+
						"' has not a valid type '"+String.valueOf(value)+"'.");
			}
		}else
			throw new IllegalArgumentException("The node '"+name+"' has not a valid type.");
	}

}
