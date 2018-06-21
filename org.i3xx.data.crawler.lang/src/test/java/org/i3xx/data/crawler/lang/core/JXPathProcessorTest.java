package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JXPathProcessorTest {
	
	//
	// The jxpath result is set to the variable 'jxpath'
	//
	
	@Test
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}}\n";
		stmt += "json ~a\n";
		stmt += "jxpath ( ~a /person/first-name )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		//
		// n is the leaf node of the struct
		//
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "first-name");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}
	
	//
	// This sets a literal only
	//
	
	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "json ~a\n";
		stmt += "jxpath ( ~a /person/first-name/@value )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "first-name");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}

	@Test
	void testC() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "b (json a)\n";
		stmt += "c (jxpath ( b /person/first-name ))\n";
		stmt += "^d $c;\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "John");
		//The variable d
		assertEquals( ((LeafNode)r.getVariables().get("d")).getValue(), "John");
	}
	
	//
	// This resolves to b
	//

	@Test
	void testD() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "~a (json ~a)\n";
		stmt += "~a (jxpath ( ~a /person/first-name/@value ))\n";
		stmt += "resolve Mr. $~a; Doe\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		//assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "Mr. John Doe");
	}

	@Test
	void testE() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "b (json a)\n";
		stmt += "c ( jxpath ( b /person/first-name/@value ))\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}

	@Test
	void testF() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "b (json a)\n";
		stmt += "c ( jxpath ( b /person/first-name/@value ) )\n";
		stmt += "print c\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}

}
