package org.i3xx.data.crawler.lang.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.BuiltinFunctions;

public class TupleResolver {

	private final Map<String, Function> functions;
	
	private final Map<String, Node> variables;
	
	public TupleResolver() {
		this.functions = BuiltinFunctions.getDefault();
		this.variables = new LinkedHashMap<String, Node>();
	}
	
	public TupleResolver(Map<String, Function> functions, Map<String, Node> variables) {
		this.functions = functions;
		this.variables = variables;
	}
	
	/**
	 * @return
	 */
	public Map<String, Node> getVariables() {
		return variables;
	}
	
	/**
	 * @param list
	 * @throws Exception 
	 */
	public Node resolveAndGetLast(ListNode list) throws Exception {
		List<Node> temp = resolve(list, variables);
		return temp.get(temp.size()-1);
	}
	
	/**
	 * @param list
	 * @throws Exception 
	 */
	public List<Node> resolve(ListNode list) throws Exception {
		return resolve(list, variables);
	}
	
	/**
	 * @param list
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	public List<Node> resolve(ListNode list, Map<String, Node> variables) throws Exception {
		
		List<Node> result = new ArrayList<Node>();
		
		for(int i=0;i<list.getStruct().size();i++) {
			
			//The monade in the list of commands.
			Node n = list.getStruct().get(i);
			result.add( resolveMonad(n, variables, 0) );
		}
		return result;
	}
	
	/**
	 * @param node
	 * @param variables
	 * @param depth
	 * @return
	 * @throws Exception
	 */
	private Node resolveMonad(final Node node, final Map<String, Node> variables, int depth) throws Exception {
		
		Node result = null;
		
		if(node instanceof ListNode) {
			
			Node rsvd = resolveMonad( ((ListNode)node).getStruct().get(0), variables, depth+1);
			
			if(functions.containsKey(node.getName())) {
				
				//Always resolves to leaf !!!
				if( ! (rsvd instanceof LeafNode) )
					throw new IllegalArgumentException("Unable to resolve the statement '"+rsvd.toString()+"'.");
				
				//If it's a function run the function
				//and set the result to the parameter map
				result = funcExec(node.getName(), (LeafNode)rsvd, variables, node.getType()==Type.FIX);
				putVar(result.getName(), result, depth, variables);
			}else {
				
				//Update the resolved node
				if(rsvd instanceof LeafNode) {
					rsvd = new LeafNodeImpl(node.getType(), node.getName(), ((LeafNode)rsvd).getValue());
				}else {
					rsvd = new ListNodeImpl(node.getType(), node.getName(), ((ListNode)rsvd).getStruct());
				}
				
				//Set the value to the parameter map
				putVar(node.getName(), rsvd, depth, variables);
				//variables.put(n.getName(), r);
				result = rsvd;
			}
		}else{
			if(node.getType()==Type.SET) {
				//Set the result to the parameter map
				result = new LeafNodeImpl(node.getType(), node.getName(), ((LeafNode)node).getValue());
				putVar(result.getName(), result, depth, variables);
			}else if(functions.containsKey(node.getName())) {
				//If it's a function run the function
				//and set the result to the parameter map
				result = funcExec(node.getName(), (LeafNode)node, variables, node.getType()==Type.FIX);
				putVar(result.getName(), result, depth, variables);
			}else {
				//Set the value to the parameter map
				result = node;
				putVar(result.getName(), result, depth, variables);
			}
		}//fi
		
		return result;
	}
	
	/**
	 * @param name
	 * @param node
	 * @param depth
	 * @param variables
	 */
	private void putVar(String name, Node node, int depth, Map<String, Node> variables) {
		
		//Zuweisung nur auf Ebene '0'
		if(depth==0) {
			variables.put(name, node);
		}
		//oder ein explizites Set liegt im Falle einer inneren Zuweisung vor
		else if(node.getType()==Node.Type.SET){
			variables.put(name, node);
		}
	}
	
	/**
	 * Executes the module
	 * 
	 * @param fname The name of the function to call
	 * @param leaf The node as a leaf node
	 * @param variables The variables map
	 * @return
	 * @throws Exception
	 */
	private Node funcExec(String fname, LeafNode leaf, Map<String, Node> variables, boolean fix) throws Exception {
		
		//
		Function func = functions.get(fname).getInstance();
		
		String key = leaf.getValue();
		Node node = leaf;
		
		if(variables.containsKey(key))
			node = variables.get(key);
		
		//Structure nodes allowed, sh 11.06
		//if( !(node instanceof LeafNode))
		//	throw new IllegalArgumentException(fname+": The variable '"+key+"' is not a leaf node.");
		
		if( func instanceof FunctionVars  )
			return ((FunctionVars) func).exec(node, variables, fix);
		
		return func.exec(node, fix);
	}
}