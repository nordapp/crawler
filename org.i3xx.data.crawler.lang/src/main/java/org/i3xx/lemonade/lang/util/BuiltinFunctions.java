package org.i3xx.lemonade.lang.util;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.util.func.Monades;
import org.i3xx.lemonade.lang.util.func.PrintOut;
import org.i3xx.lemonade.lang.util.func.ResolveVariables;
import org.i3xx.lemonade.lang.util.func.ReturnResult;
import org.i3xx.lemonade.lang.util.func.UnpackLiteral;
import org.i3xx.lemonade.lang.util.func.UnshiftData;
import org.i3xx.lemonade.lang.util.lang.LemonadeRt;

public class BuiltinFunctions {

	public static final Map<String, Function> getDefault() {
		
		Map<String, Function> functions = new HashMap<String, Function>();
		
		//func
		functions.put(Monades.NAME, new Monades());
		functions.put(PrintOut.NAME, new PrintOut());
		functions.put(ResolveVariables.NAME, new ResolveVariables());
		functions.put(ReturnResult.NAME, new ReturnResult());
		functions.put(UnpackLiteral.NAME, new UnpackLiteral());
		functions.put(UnshiftData.NAME, new UnshiftData());
		
		//lang
		functions.put(LemonadeRt.NAME, new LemonadeRt());
		
		return functions;
	}
	
}
