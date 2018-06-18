package org.i3xx.data.crawler.lang.util.l;

import java.util.Map;

import org.i3xx.data.crawler.lang.core.Node;

public interface Language {

	/**
	 * @param name
	 * @param stmt
	 * @throws LScriptException
	 */
	Object execute(String name, String stmt, Map<String, Node> variables) throws LScriptException;

}
