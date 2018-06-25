package org.i3xx.lemonade.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.DataNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.i3xx.lemonade.lang.util.l.PythonRt;
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
