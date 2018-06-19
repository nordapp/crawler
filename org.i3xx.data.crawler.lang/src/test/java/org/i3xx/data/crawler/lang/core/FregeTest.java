package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.i3xx.data.crawler.lang.util.l.FregeRt;
import org.junit.jupiter.api.Test;

class FregeTest {

	@Test
	void testA() throws Exception {
		
		String stmt = "";
		stmt += "?a Hello World\n";
		stmt += "b ==::\n";
		//stmt += "x = 3\n";
		stmt += "a\n";
		stmt += "::==\n";
		stmt += "c (frege b)\n";
		
		ListNode list = new ListNodeImpl();
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);

		TupleResolver r = new TupleResolver();
		r.getFunctions().put(FregeRt.NAME, new FregeRt());
		Node n = r.resolveAndGetLast(list);
		
		System.out.println(n);
		//assertTrue( n instanceof DataNodeImpl );
		//assertEquals( n.getName(), "c");
		//assertEquals( ((DataNode)n).getData(), "Test");
	}

}
