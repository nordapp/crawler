package org.i3xx.lemonade.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UnshiftTest {

	@Test
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a ==::\n";
		stmt += "~a \"Test\"\n";
		stmt += "return ~a\n";
		stmt += "::==\n";
		stmt += "\n";
		stmt += "lemonade ~a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof DataNodeImpl );
		assertEquals( n.getName(), "~a");
		assertEquals( ((LeafNode)n).getValue(), "lemonade");
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a ==::\n";
		stmt += "~a \"Test\"\n";
		stmt += "return ~a\n";
		stmt += "::==\n";
		stmt += "\n";
		stmt += "<lemonade ~a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "~a");
		assertEquals( ((LeafNode)n).getValue(), "\"Test\"");
	}

	@Test
	void testC() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "\n";
		stmt += "~b Test\n";
		stmt += "\n";
		stmt += "~a ==::\n";
		stmt += "^~a \"$~b;\"\n";
		stmt += "return ~a\n";
		stmt += "::==\n";
		stmt += "\n";
		stmt += "<lemonade ~a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "~a");
		assertEquals( ((LeafNode)n).getValue(), "\"Test\"");
	}

}
