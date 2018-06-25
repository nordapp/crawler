package org.i3xx.lemonade.lang.util;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.Node;

public class StringTools {
	
	/**
	 * @param stmt
	 * @param variables
	 * @return
	 */
	public static final String replace(String stmt, Map<String, Node> variables) {
		
		Pattern pattern = Pattern.compile("\\$(.+?)\\;");
		Matcher matcher = pattern.matcher(stmt);
		
		StringBuilder builder = new StringBuilder();
		int i = 0;
		while (matcher.find()) {
			String key = matcher.group(1);
			Node node = variables.get(key);
			if(node==null)
				throw new NoSuchElementException("The key '"+key+"' is not available in variables.");
			String replacement = ((LeafNode)node).getValue();
			builder.append(stmt.substring(i, matcher.start()));
			if (replacement == null)
				builder.append(matcher.group(0));
			else
				builder.append(replacement);
			i = matcher.end();
		}
		
		builder.append(stmt.substring(i, stmt.length()));
		return builder.toString();
	}
}
