package org.i3xx.data.crawler.lang.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.i3xx.data.crawler.lang.core.Node.Type;

/**
 * The language is a set of tuples of names and values. There are functions and
 * variables but no conditional clauses or loops available.
 * 
 * A variable may contain a function and can be a given as a parameter to
 * another function. A variable can be a data struct or something else
 * depending on the needs of the programmer.
 * 
 * If the variable is a leaf node it is referenced by value, if it is a tree
 * node it is referenced by ref.
 * 
 * The tuples build a list or a tree that is resolved to a value.
 * 
 * All names are final, all values are final. Lists and arrays are immutable,
 * but replaceable.
 * 
 * @author green
 *
 */
public class TupleParser {
	
	/**
	 * @param list
	 * @param stmt
	 * @throws IOException 
	 */
	public void parse(ListNode list, String stmt) throws IOException {
		
		BufferedReader r = new BufferedReader( new StringReader(stmt) );
		List<Node> ls = new ArrayList<Node>();
		
		boolean extendMode = false;
		String extendKey = null;
		StringBuffer extendVal = null;
		
		try {
			for(;;) {
				String line = r.readLine();
				if(line==null)
					break;
				
				//test for extended end
				if(extendMode && line.trim().equals("::"+extendKey+"==")) {
					extendMode = false;
					extendKey = null;
					
					Node temp = ls.get(ls.size()-1);
					ls.set(ls.size()-1, new LeafNodeImpl(temp.getType(), temp.getName(), extendVal.toString()) );
					
					extendVal = null;
					continue;
				}
				
				//extended
				if(extendMode) {
					extendVal.append(line);
					extendVal.append(System.lineSeparator());
					continue;
				}
				
				line = line.trim();
				
				if(line.length()==0)
					continue;
				
				if(line.startsWith("#"))
					continue;
				
				String[] tp = line.split("\\s+", 2);
				if(tp.length!=2)
					throw new IllegalArgumentException("'"+line+"' is not a tuple.");
				
				Node node = null;
				Type type = Type.NODE;
				
				if(tp[0].startsWith(":")) {
					tp[0] = tp[0].substring(1);
					type = Type.SET;
				}else if(tp[0].startsWith("^")) {
					tp[0] = tp[0].substring(1);
					type = Type.RESOLVE;
				}
				
				if(tp[1].startsWith("(") && tp[1].endsWith(")")) {
					node = new ListNodeImpl(type, tp[0], null);
					parse( (ListNode)node, tp[1].substring(1, tp[1].length()-1) );
				}
				else if(tp[1].startsWith("==") && tp[1].endsWith("::")) {
					extendMode = true;
					extendKey = tp[1].substring(2, tp[1].length()-2);
					extendVal = new StringBuffer();
					node = new LeafNodeImpl(type, tp[0], null);
				}
				else {
					node = new LeafNodeImpl(type, tp[0], tp[1]);
				}
				
				ls.add(node);
			}//for
			
			list.setStruct(ls);
		}finally {
			r.close();
		}
	}
	
}
