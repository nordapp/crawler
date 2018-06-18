package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OffLiteralTest {

	@Test
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "?a \"Test\"\n";
		stmt += "offliteral ?a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "Test");
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "?a \"Test\"\n";
		stmt += "offliteral ?a\n";
		stmt += "print ?a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "Test");
	}

}
