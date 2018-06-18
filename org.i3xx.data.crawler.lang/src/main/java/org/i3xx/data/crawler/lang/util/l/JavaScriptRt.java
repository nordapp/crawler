package org.i3xx.data.crawler.lang.util.l;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.FunctionVars;
import org.i3xx.data.crawler.lang.core.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaScriptRt extends FunctionVars implements Language {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaScriptRt.class);
	
	public static final String NAME = "js";
	
	public JavaScriptRt() {
	}
	
	@Override
	public Object execute(String name, String stmt, Map<String, Node> variables) throws LScriptException {
		
		try {
			ScriptEngineManager factory  = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("nashorn");
			
			Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
			for(Map.Entry<String, Node> v : variables.entrySet()) {
				b.put(v.getKey(), v.getValue());
			}
			
			return engine.eval(stmt);
			
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
		return null;
	}
	
}
