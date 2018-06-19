package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.i3xx.data.crawler.lang.util.l.JavaScriptRt;
import org.junit.jupiter.api.Test;

class JavaScriptTest {


	@Test
	void testA() throws Exception {
		
		String stmt = "";
		stmt += "?a Hello World\n";
		stmt += "b ==::\n";
		stmt += "d=a.getValue();\n";
		stmt += "a.setValue('Test');\n";
		stmt += "a;\n";
		stmt += "::==\n";
		stmt += "c (javascript b)\n";
		
		ListNode list = new ListNodeImpl();
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);

		TupleResolver r = new TupleResolver();
		r.getFunctions().put(JavaScriptRt.NAME, new JavaScriptRt());
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof DataNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((DataNode)n).getData(), "Test");
	}

}