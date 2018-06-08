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
		
		stmt += "json ( a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\", \"test\":7}} )\n";
		stmt += "jxpath ( a /person/first-name )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "a");
		assertEquals( ((LeafNode)n).getValue(), "{\"first-name\":\"John\"}");
	}
	
	//
	// This sets a literal only
	//
	
	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "json a\n";
		stmt += "jxpath ( a /person/first-name )\n";
		stmt += "b jxpath\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "jxpath");
	}

	@Test
	void testC() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "json a\n";
		stmt += "jxpath ( a /person/first-name )\n";
		stmt += "b $jxpath;\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "$jxpath;");
	}
	
	//
	// This resolves to b
	//

	@Test
	void testD() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "json a\n";
		stmt += "jxpath ( a /person/first-name/@value )\n";
		stmt += "resolve ( b $a; )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}

	@Test
	void testE() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "json a\n";
		stmt += "b ( jxpath ( a /person/first-name/@value ))\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}

	@Test
	void testF() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a {\"person\": {\"first-name\":\"John\", \"name\":\"Doe\"}}\n";
		stmt += "json a\n";
		stmt += "b ( jxpath ( a /person/first-name/@value ) )\n";
		stmt += "print b\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "John");
	}

}
