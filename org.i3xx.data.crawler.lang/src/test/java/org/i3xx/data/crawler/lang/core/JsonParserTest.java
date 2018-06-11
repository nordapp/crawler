package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JsonParserTest {

	@Test
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "jsonm ( a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}} )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		Node b = null;
		
		assertTrue( n instanceof ListNodeImpl );
		assertEquals( n.getName(), "a");
		
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
		stmt += "b ( jsonm a )\n";
		
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
