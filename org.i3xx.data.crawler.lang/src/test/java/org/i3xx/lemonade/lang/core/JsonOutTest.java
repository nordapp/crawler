package org.i3xx.lemonade.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.junit.jupiter.api.Test;

class JsonOutTest {

	@Test
	void testA() throws Exception {
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}}\n";
		stmt += "json ~a\n";
		stmt += "jsonp ~a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);

		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		//assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		//Caution: The value is "7" because there are only String values allowed.
		assertEquals( ((LeafNode)n).getValue(), "{\"person\":{\"first-name\":\"John\",\"name\":\"Doe\",\"test\":\"7\"}}");
	}

}
