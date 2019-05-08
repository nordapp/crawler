package org.i3xx.lemonade.lang.util.lang;

import java.util.Map;

import org.i3xx.lemonade.lang.core.Node;

public interface Language {

	/**
	 * @param name
	 * @param stmt
	 * @throws LanguageException
	 */
	Object execute(String name, String stmt, Map<String, Node> variables) throws LanguageException;

}
