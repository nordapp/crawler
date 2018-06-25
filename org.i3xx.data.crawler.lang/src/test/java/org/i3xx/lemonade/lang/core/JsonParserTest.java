package org.i3xx.lemonade.lang.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.junit.jupiter.api.Test;

class JsonParserTest {

	@Test
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "json ( a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}} )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		Node b = null;
		
		assertTrue( n instanceof ListNodeImpl );
		assertEquals( n.getName(), "unknown");
		
		n = ((ListNode)n).getStruct().get(0);
		
		assertTrue( n instanceof ListNodeImpl );
		assertEquals( n.getName(), "person");
		
		b = ((ListNode)n).getStruct().get(0);
		
		assertTrue( b instanceof LeafNodeImpl );
		assertEquals( b.getName(), "first-name");
		assertEquals( ((LeafNode)b).getValue(), "John");
		
		b = ((ListNode)n).getStruct().get(1);
		
		assertTrue( b instanceof LeafNodeImpl );
		assertEquals( b.getName(), "name");
		assertEquals( ((LeafNode)b).getValue(), "Doe");
		
		b = ((ListNode)n).getStruct().get(2);
		
		assertTrue( b instanceof LeafNodeImpl );
		assertEquals( b.getName(), "test");
		assertEquals( ((LeafNode)b).getValue(), "7");
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}}\n";
		stmt += "b ( json a )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		Node b = null;
		
		assertTrue( n instanceof ListNodeImpl );
		assertEquals( n.getName(), "b");
		
		n = ((ListNode)n).getStruct().get(0);
		
		assertTrue( n instanceof ListNodeImpl );
		assertEquals( n.getName(), "person");
		
		b = ((ListNode)n).getStruct().get(0);
		
		assertTrue( b instanceof LeafNodeImpl );
		assertEquals( b.getName(), "first-name");
		assertEquals( ((LeafNode)b).getValue(), "John");
		
		b = ((ListNode)n).getStruct().get(1);
		
		assertTrue( b instanceof LeafNodeImpl );
		assertEquals( b.getName(), "name");
		assertEquals( ((LeafNode)b).getValue(), "Doe");
		
		b = ((ListNode)n).getStruct().get(2);
		
		assertTrue( b instanceof LeafNodeImpl );
		assertEquals( b.getName(), "test");
		assertEquals( ((LeafNode)b).getValue(), "7");
		
		//
		// *** but a contains the JSON ***
		//
		
		n = r.getVariables().get("a");
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "a");
		assertEquals( ((LeafNode)n).getValue(), "{\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}}");
	}

}
