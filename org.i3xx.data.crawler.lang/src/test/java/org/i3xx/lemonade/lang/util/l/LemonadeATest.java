package org.i3xx.lemonade.lang.util.l;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.DataNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.junit.jupiter.api.Test;

class LemonadeATest {

	@Test
	void test() throws Exception {
		
		String stmt = "";
		stmt += "~a ==::\n";
		stmt += "	#\n";
		stmt += "	~a Hallo\n";
		stmt += "	~b $~a; Welt\n";
		stmt += "	resolve ~b\n";
		stmt += "	return ~b\n";
		stmt += "::==\n";

		stmt += "~c ==1::\n";
		stmt += "	lemonade ~a\n";
		stmt += "	return ~a\n";
		stmt += "::1==\n";

		stmt += "lemonade ~c\n";
		
		ListNode list = new ListNodeImpl();
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);

		TupleResolver r = new TupleResolver();
		r.getFunctions().put(LemonadeRt.NAME, new LemonadeRt());
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof DataNodeImpl );
		assertEquals( n.getName(), "~c");
		assertEquals( ((DataNode)n).getData(), "Hallo Welt");
		
	}
}
