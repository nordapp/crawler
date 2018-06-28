package org.i3xx.lemonade.lang.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.DataNodeImpl;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;

public class NodeTools {

	/**
	 * @param list
	 * @return
	 */
	public static final List<Node> makeModifyable(List<Node> list) {
		return new ArrayList<Node>(list);
	}

	/**
	 * @param type
	 * @param name
	 * @param node
	 * @return
	 */
	public static final ListNode newListNode(Type type, String name, Node node) {
		List<Node> list = new ArrayList<Node>();
		list.add(node);
		return new ListNodeImpl(type, name, list);
	}
	
	/**
	 * Creates the unknown node typically for a function return
	 * if the variables should not be set.
	 * 
	 * @param node
	 * @return
	 */
	public static final Node createUnknown(Node node) {
		
		if(node instanceof ListNode){
			Node struct = ((ListNode)node).getStruct().get(0);
			return NodeTools.newListNode(Type.NODE, Node.UNKNOWN, struct);
		}else if(node instanceof DataNode) {
			String val = ((LeafNode)node).getValue();
			Object obj = ((DataNode)node).getData();
			return new DataNodeImpl(Type.NODE, Node.UNKNOWN, val, obj);
		}else if(node instanceof LeafNode) {
			String val = ((LeafNode)node).getValue();
			return new LeafNodeImpl(Type.NODE, Node.UNKNOWN, val);
		}else {
			//should not happen
			throw new IllegalArgumentException("Undefined return type: "+node);
		}
	}
	
	/**
	 * Creates the unknown node typically for a function return
	 * if the variables should not be set.
	 * 
	 * @param node The origin leaf node
	 * @param text The new text
	 * @return
	 */
	public static final Node createUnknownLeaf(String text) {
		
		return new LeafNodeImpl(Type.NODE, Node.UNKNOWN, text);
	}
	
	/**
	 * Creates the unknown node typically for a function return
	 * if the variables should not be set.
	 * 
	 * Creates a named leaf node only if it's a variable.<br>
	 * 
	 * @param node The origin leaf node
	 * @param text The new text
	 * @param variables The variables reference to check
	 * @return
	 */
	public static final Node createUnknownLeaf(Node node, String text, Map<String, Node> variables) {
		
		if( ! (node instanceof LeafNode) )
			throw new IllegalArgumentException("Only a leaf node is allowed here, but it's a '" +
					node.toString() + "");
		
		//variable
		String name = Node.UNKNOWN;
		if(variables.containsKey(node.getName())) {
			name = node.getName();
		}
		return new LeafNodeImpl(Type.NODE, name, text);
	}
}
