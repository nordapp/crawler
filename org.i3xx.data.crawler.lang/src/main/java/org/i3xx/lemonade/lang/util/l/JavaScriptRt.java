package org.i3xx.lemonade.lang.util.l;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.i3xx.lemonade.lang.core.Accent;
import org.i3xx.lemonade.lang.core.DataNodeImpl;
import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.FunctionVars;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaScriptRt extends FunctionVars implements Language {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaScriptRt.class);
	
	public static final String NAME = "javascript";
	
	public JavaScriptRt() {
	}
	
	@Override
	public Object execute(String name, String stmt, Map<String, Node> variables) throws LScriptException {
		
		try {
			ScriptEngineManager factory  = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("nashorn");
			
			Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
			for(Map.Entry<String, Node> v : variables.entrySet()) {
				String key = v.getKey();
				if(key.charAt(0)==Accent.CHANGE)
					key = key.substring(1);
				
				b.put(key, new DefaultVariable(v.getKey(), variables));
			}
			
			Object value = engine.eval(stmt);
			
			//do not return a DefaultVariable
			if(value instanceof DefaultVariable)
				value = ((DefaultVariable)value).getValue();
			
			return value;
			
		} catch (ScriptException e) {
			logger.debug("Exception in name {}, line {}, column {}", name,
					e.getLineNumber(), e.getColumnNumber());
			
			throw new LScriptException(e, e.getLineNumber(), e.getColumnNumber(), stmt);
		}
		
	}

	@Override
	public Function getInstance() {
		return new JavaScriptRt();
	}

	@Override
	public Node exec(Node node, Map<String, Node> variables) throws Exception {
		
		Object result = execute(node.getName(), ((LeafNode)node).getValue(), variables);
		
		return new DataNodeImpl(Type.NODE, node.getName(), NAME, result);
	}
	
}
