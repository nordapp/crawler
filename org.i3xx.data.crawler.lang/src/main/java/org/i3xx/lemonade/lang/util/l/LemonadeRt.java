package org.i3xx.lemonade.lang.util.l;

import java.io.IOException;
import java.util.Map;

import org.i3xx.lemonade.lang.core.Accent;
import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.DataNodeImpl;
import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.FunctionVars;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.ScriptException;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LemonadeRt extends FunctionVars implements Language {
	
	private static final Logger logger = LoggerFactory.getLogger(LemonadeRt.class);
	
	public static final String NAME = "lemonade";
	
	public LemonadeRt() {
		
	}
	
	@Override
	public Object execute(String name, String stmt, Map<String, Node> variables) throws LScriptException {
		
		try {
			ListNode list = new ListNodeImpl();
			
			TupleParser parser = new TupleParser();
			parser.parse(list, stmt);
			
			TupleResolver resolver = new TupleResolver();
			
			for(Map.Entry<String, Node> v : variables.entrySet()) {
				String key = v.getKey();
				if(key.charAt(0)==Accent.CHANGE)
					key = key.substring(1);
				
				resolver.getVariables().putAll(variables);
			}
			
			Node node = resolver.resolveAndGetLast(list);
			
			if( node instanceof LeafNode) {
				return ((LeafNode)node).getValue();
			}else if( node instanceof ListNode) {
				return ((ListNode)node).getStruct().get(0);
			}else if( node instanceof DataNode) {
				return ((DataNode)node).getData();
			}else {
				//should not happen
				throw new ScriptException("Undefined return type. Fix it in the java code: "+node, 1, stmt);
			}
			
		} catch (ScriptException e) {
			logger.debug("Exception in name {}, stmt {}", name,
					stmt);
			
			throw new LScriptException(e, e.getLine(), 1, stmt);
		} catch (IOException e) {
			throw new LScriptException(e, 1, 1, stmt);
		} catch (Exception e) {
			throw new LScriptException(e, 1, 1, stmt);
		}
	}


	@Override
	public Node exec(Node node, Map<String, Node> variables) throws Exception {
		
		Object result = execute(node.getName(), ((LeafNode)node).getValue(), variables);
		
		return new DataNodeImpl(Type.NODE, node.getName(), NAME, result);
	}

	@Override
	public Function getInstance() {
		return new LemonadeRt();
	}

}
