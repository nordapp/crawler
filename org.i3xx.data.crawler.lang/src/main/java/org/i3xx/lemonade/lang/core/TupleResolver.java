package org.i3xx.lemonade.lang.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

import org.i3xx.lemonade.lang.core.Node.Type;
import org.i3xx.lemonade.lang.util.BuiltinFunctions;
import org.i3xx.lemonade.lang.util.f.ResolveVariables;
import org.i3xx.lemonade.lang.util.f.UnshiftData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TupleResolver {
	
	private static Logger logger = LoggerFactory.getLogger(TupleResolver.class);
	
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
	 * @return
	 */
	public Map<String, Function> getFunctions() {
		return functions;
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
			result.add( resolveMonad(n, variables, 0, i) );
		}
		return result;
	}
	
	/**
	 * @param node
	 * @param variables
	 * @param depth
	 * @param lncnt
	 * @return
	 * @throws Exception
	 */
	private Node resolveMonad(final Node node, final Map<String, Node> variables, int depth, int lncnt) throws Exception {
		
		Node result = null;
		
		// The data is a list
		if(node instanceof ListNode) {
			
			Node strc = ((ListNode)node).getStruct().get(0);
			Node rsvd = resolveMonad( strc, variables, depth+1, lncnt);
			
			// A function
			if(functions.containsKey(node.getName())) {
				
				//Always resolves to leaf !!!
				if( ! (rsvd instanceof LeafNode) )
					throw new ScriptException("Unable to resolve the statement '"+rsvd.toString()+"'.", lncnt, null);
				
				//If it's a function run the function
				//and set the result to the parameter map
				result = funcExec(node.getName(), (LeafNode)rsvd, variables);
				putVar(result.getName(), result, depth, variables);
			}
			
			// A variable set
			else {
				//Update the resolved node
				if(rsvd instanceof DataNode) {
					rsvd = new DataNodeImpl(node.getType(), node.getName(), ((LeafNode)rsvd).getValue(),
							((DataNode)rsvd).getData());
				}else if(rsvd instanceof LeafNode) {
					rsvd = new LeafNodeImpl(node.getType(), node.getName(), ((LeafNode)rsvd).getValue());
				}else {
					rsvd = new ListNodeImpl(node.getType(), node.getName(), ((ListNode)rsvd).getStruct());
				}
				
				//### some sugar ###
				//<~a (lemonade a)
				//In case of unshift, unshift the function's result
				if(node.getType()==Type.UNSHIFT) {
					Function func = functions.get(UnshiftData.NAME).getInstance();
					rsvd = func.exec(rsvd);
				}
				//###
				
				//Set the value to the parameter map
				putVar(node.getName(), rsvd, depth, variables);
				//variables.put(n.getName(), r);
				result = rsvd;
			}
		}
		
		// The data is a leaf node
		else{
			
			// The accent ':'
			if(node.getType()==Type.SET) {
				//Set the result to the parameter map
				result = new LeafNodeImpl(node.getType(), node.getName(), ((LeafNode)node).getValue());
				putVar(result.getName(), result, depth, variables);
			}
			
			// The accent '^'
			else if(node.getType()==Type.RESOLVE) {
				//Set the result to the parameter map
				result = funcExec(ResolveVariables.NAME, (LeafNode)node, variables);
				//In case of depth 0, variable name instead of result.getName() 'unknown'
				String name = ( depth==0 ? node.getName() : result.getName() );
				putVar(name, result, depth, variables);
				//putVar(result.getName(), result, depth, variables);
			}
			
			// The function (implied by key test)
			else if(functions.containsKey(node.getName())) {
				//If it's a function run the function
				//and set the result to the parameter map
				result = funcExec(node.getName(), (LeafNode)node, variables);
				
				//### some sugar ###
				//~a (<lemonade a)
				//In case of unshift, unshift the function's result
				if(node.getType()==Type.UNSHIFT) {
					Function func = functions.get(UnshiftData.NAME).getInstance();
					result = func.exec(result);
				}
				
				//A variable may point to another one
				//In case of depth 0, variable name instead of result.getName() 'unknown'
				//offliteral ~a
				//if the variable is changeable (not final).
				String name = result.getName();
				if(depth==0) {
					String n = ((LeafNode)node).getValue();
					//Only a variable can start with Accent.CHANGE, need no extra test
					if(n.charAt(0)==Accent.CHANGE)
						name = n;
				}
				//###
				
				putVar(name, result, depth, variables);
				//putVar(result.getName(), result, depth, variables);
			}
			
			// A variable set
			else {
				//Set the value to the parameter map
				
				//### some sugar ###
				//<~a ~a
				//In case of unshift, unshift the variable's data
				if(node.getType()==Type.UNSHIFT) {
					if(variables.containsKey(node.getName())) {
						result = variables.get(node.getName());
					}else {
						result = node;
					}
					Function func = functions.get(UnshiftData.NAME).getInstance();
					result = func.exec(result);
				}
				//###
				
				else {
					result = node;
				}
				putVar(result.getName(), result, depth, variables);
			}
		}//fi
		
		return result;
	}
	
	/**
	 * The variables are not modifyable except the name starts with '?'
	 * or is 'unknown'
	 * 
	 * @param name
	 * @param node
	 * @param depth
	 * @param variables
	 */
	private void putVar(String name, Node node, int depth, Map<String, Node> variables) {
		//The variable UNKNOWN is not valid
		if(Node.UNKNOWN.equals(name))
			return;
		
		//Ein explizites Set liegt im Falle einer inneren Zuweisung vor
		if(node.getType()==Node.Type.SET ){
			logger.debug("Set(:) variable key:{}, hash:{}, node:{}", name, node.hashCode(), node);
			if(variables.containsKey(name) && name.charAt(0)!=Accent.CHANGE)
				throw new UnsupportedOperationException("The variable '"+name+"' is unmodifyable.");
			
			variables.put(name, node);
		}
		//oder ein explizites Set liegt im Falle einer inneren Zuweisung vor
		else if(node.getType()==Node.Type.RESOLVE){
			logger.debug("Set(^) variable key:{}, hash:{}, node:{}", name, node.hashCode(), node);
			if(variables.containsKey(name) && name.charAt(0)!=Accent.CHANGE)
				throw new UnsupportedOperationException("The variable '"+name+"' is unmodifyable.");
			
			variables.put(name, node);
		}
		//oder eine Zuweisung nur auf Ebene '0'
		else if(depth==0) {
			logger.debug("Set variable key:{}, hash:{}, node:{}", name, node.hashCode(), node);
			if(variables.containsKey(name) && name.charAt(0)!=Accent.CHANGE)
				throw new UnsupportedOperationException("The variable '"+name+"' is unmodifyable.");
			
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
	private Node funcExec(String fname, LeafNode leaf, Map<String, Node> variables) throws Exception {
		
		//
		Function func = functions.get(fname).getInstance();
		
		String key = leaf.getValue();
		Node node = leaf;
		
		if(variables.containsKey(key)) {
			node = variables.get(key);
			logger.debug("{}: Get variable by function key:{}, hash:{}",
					fname, key, node.hashCode());
		}
		
		//Structure nodes allowed, sh 11.06
		//if( !(node instanceof LeafNode))
		//	throw new IllegalArgumentException(fname+": The variable '"+key+"' is not a leaf node.");
		Node resl = null;
		logger.debug("{}: Node to function key:{}, hash:{}, node:{}", fname, node.getName(), node.hashCode(), node);
		
		if( func instanceof FunctionVars  )
			resl = ((FunctionVars) func).exec(node, variables);
		else
			resl = func.exec(node);
		
		logger.debug("{}: Node return key:{}, hash:{}, node:{}", fname, resl.getName(), resl.hashCode(), resl);
		return resl;
	}
	
	/**
	 * @param leaf
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isVariable(LeafNode leaf, Map<String, Node> variables) {
		String key = leaf.getValue();
		
		return ( variables.containsKey(key) && key.charAt(0)==Accent.CHANGE );
	}
}
