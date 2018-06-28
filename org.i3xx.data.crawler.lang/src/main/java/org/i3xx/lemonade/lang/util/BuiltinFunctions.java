package org.i3xx.lemonade.lang.util;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.util.f.GetHttpTensor;
import org.i3xx.lemonade.lang.util.f.GitHubProperties;
import org.i3xx.lemonade.lang.util.f.JXPathProcessor;
import org.i3xx.lemonade.lang.util.f.JsonOut;
import org.i3xx.lemonade.lang.util.f.JsonParser;
import org.i3xx.lemonade.lang.util.f.Monades;
import org.i3xx.lemonade.lang.util.f.PrintOut;
import org.i3xx.lemonade.lang.util.f.ResolveVariables;
import org.i3xx.lemonade.lang.util.f.ReturnResult;
import org.i3xx.lemonade.lang.util.f.UnpackLiteral;
import org.i3xx.lemonade.lang.util.f.UnshiftData;
import org.i3xx.lemonade.lang.util.l.ClojureRt;
import org.i3xx.lemonade.lang.util.l.JavaScriptRt;
import org.i3xx.lemonade.lang.util.l.LemonadeRt;
import org.i3xx.lemonade.lang.util.l.PythonRt;

public class BuiltinFunctions {

	public static final Map<String, Function> getDefault() {
		
		Map<String, Function> functions = new HashMap<String, Function>();
		functions.put(GetHttpTensor.NAME, new GetHttpTensor());
		functions.put(GitHubProperties.NAME, new GitHubProperties());
		functions.put(JXPathProcessor.NAME, new JXPathProcessor());
		functions.put(JsonOut.NAME, new JsonOut());
		functions.put(JsonParser.NAME, new JsonParser());
		functions.put(Monades.NAME, new Monades());
		functions.put(PrintOut.NAME, new PrintOut());
		functions.put(ResolveVariables.NAME, new ResolveVariables());
		functions.put(ReturnResult.NAME, new ReturnResult());
		functions.put(UnpackLiteral.NAME, new UnpackLiteral());
		functions.put(UnshiftData.NAME, new UnshiftData());
		
		functions.put(ClojureRt.NAME, new ClojureRt());
		functions.put(LemonadeRt.NAME, new LemonadeRt());
		functions.put(PythonRt.NAME, new PythonRt());
		functions.put(JavaScriptRt.NAME, new JavaScriptRt());
		
		return functions;
	}
	
}
