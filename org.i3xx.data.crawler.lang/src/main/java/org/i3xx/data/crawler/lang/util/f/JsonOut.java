package org.i3xx.data.crawler.lang.util.f;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;

/**
 * Print out the intern data to a JSON String
 * 
 * @author green
 *
 */
public class JsonOut implements Function {
	
	public static final String NAME = "jsonp";
	
	@Override
	public Function getInstance() {
		return this;
	}
	
	@Override
	public Node exec(Node node) throws Exception {
		
		StringBuffer buf = new StringBuffer();
		print(node, buf, 0);
		
		return new LeafNodeImpl(Type.NODE, Node.UNKNOWN, buf.toString());
	}
	
	private void print(Node node, StringBuffer buf, int depth) {
		
		if(node instanceof ListNode) {
			
			if(depth>0 && node.getName()!=null) {
				buf.append('"');
				buf.append(node.getName());
				buf.append('"');
				buf.append(':');
			}
			
			ListNode list = (ListNode)node;
			for(int i=0;i<list.getStruct().size();i++) {
				Node child = list.getStruct().get(i);
				if(i==0) {
					buf.append('{');
				}else {
					buf.append(',');
				}//
				
				print(child, buf, depth+1);
				
				if(i==(list.getStruct().size()-1)) {
					buf.append('}');
				}//fi
			}//for
		}else {
			if(depth>0 && node.getName()!=null) {
				buf.append('"');
				buf.append(node.getName());
				buf.append('"');
				buf.append(':');
				buf.append('"');
				buf.append( ((LeafNode)node).getValue() );
				buf.append('"');
			}
		}
	}
}
