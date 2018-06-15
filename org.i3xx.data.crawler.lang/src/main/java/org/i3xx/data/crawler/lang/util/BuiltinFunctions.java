package org.i3xx.data.crawler.lang.util;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.util.f.GetHttpTensor;
import org.i3xx.data.crawler.lang.util.f.GitHubProperties;
import org.i3xx.data.crawler.lang.util.f.JXPathProcessor;
import org.i3xx.data.crawler.lang.util.f.JsonOut;
import org.i3xx.data.crawler.lang.util.f.JsonParser;
import org.i3xx.data.crawler.lang.util.f.Monades;
import org.i3xx.data.crawler.lang.util.f.PrintOut;
import org.i3xx.data.crawler.lang.util.f.ResolveVariables;
import org.i3xx.data.crawler.lang.util.f.UnpackLiteral;

public class BuiltinFunctions {

	public static final Map<String, Function> getDefault() {
		
		Map<String, Function> functions = new HashMap<String, Function>();
		functions.put(ResolveVariables.NAME, new ResolveVariables());
		functions.put(GetHttpTensor.NAME, new GetHttpTensor());
		functions.put(PrintOut.NAME, new PrintOut());
		functions.put(JXPathProcessor.NAME, new JXPathProcessor());
		functions.put(JsonParser.NAME, new JsonParser());
		functions.put(JsonOut.NAME, new JsonOut());
		functions.put(GitHubProperties.NAME, new GitHubProperties());
		functions.put(Monades.NAME, new Monades());
		functions.put(UnpackLiteral.NAME, new UnpackLiteral());
		
		return functions;
	}
	
}
