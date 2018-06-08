package org.i3xx.data.crawler.lang.util.f.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.ri.model.NodePointer;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.Node;

public class ScratchTools {
	
	/**
	 * Returns the children as a list
	 * 
	 * @param brick The node
	 * @return The List with the children
	 */
	public static List<Node> childrenToList(Node node) {
		
		List<Node> list = null;
		
		if( node instanceof ListNode ) {
			list = ((ListNode)node).getStruct();
		}else {
			list = new ArrayList<Node>();
			list.add(node);
		}
		
		return list;
	}
	
	/**
	 * @param msg
	 * @param parent
	 * @param node
	 */
	public static void print(String msg, Object parent, Object node) {
    	if(parent != null){
    		Node brick = null;
    		if(parent instanceof NodePointer){
    			brick = (Node)((NodePointer) parent).getNode();
    		}else if(parent instanceof Node){
    			brick = (Node)parent;
    		}
			msg = msg+"\n    parent: "+
				(brick==null ? "null" : 
					((Node)brick).getName());
    	}
    	if(node != null){
    		Node brick = null;
    		if(node instanceof NodePointer){
    			brick = (Node)((NodePointer) node).getNode();
    		}else if(node instanceof Node){
    			brick = (Node)node;
    		}
			msg = msg+"\n    node: "+
				(brick==null ? "null" : 
					((Node)brick).getName());
    	}
    	System.out.println( msg );
	}
	
	/**
	 * @param msg
	 * @param result
	 */
	public static void printResult(String msg, Object result) {
		if(result instanceof List){
			for(Object f:((List<?>)result))
				printResult(msg, f);
			
			return;
		}
		if(result instanceof LeafNode){
			LeafNode b = (LeafNode)result;
			printResult(msg, b.getName()+":"+b.getValue());
			
			return;
		}else if(result instanceof Node){
			Node f = (Node)result;
			printResult(msg, f.getName());
			
			return;
		}
		System.out.println(msg+" "+result);
	}
	
	/**
	 * @param data
	 * @param name
	 * @return
	 */
	public static Object getInnerValue(Node node, String name) {
		
		if(node instanceof LeafNode)
			return ((LeafNode)node).getValue();
		
		return node.getName();
	}
}
