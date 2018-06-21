package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.i3xx.data.crawler.lang.util.l.PythonRt;
import org.junit.jupiter.api.Test;

class PythonTest {

	@Test
	void testA() throws Exception {
		
		String stmt = "";
		stmt += "~a Hello World\n";
		stmt += "b ==::\n";
		stmt += "d=a.getValue();\n";
		stmt += "a.setValue('Test');\n";
		stmt += "engine_return = a\n";
		stmt += "::==\n";
		stmt += "c (python b)\n";
		
		ListNode list = new ListNodeImpl();
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);

		TupleResolver r = new TupleResolver();
		r.getFunctions().put(PythonRt.NAME, new PythonRt());
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof DataNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((DataNode)n).getData(), "Test");
	}

}
