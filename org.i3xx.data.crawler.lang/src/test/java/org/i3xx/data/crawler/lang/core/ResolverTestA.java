package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The test is set and resolve
 * 
 * @author green
 *
 */
class ResolverTestA {

	@Test
	void testA1() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "a");
		assertEquals( ((LeafNode)n).getValue(), "hello");
	}

	@Test
	void testA2() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "world");
	}

	@Test
	void testA3() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "c ( resolve a )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((LeafNode)n).getValue(), "hello");
	}

	@Test
	void testA4() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "c ( print a )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((LeafNode)n).getValue(), "hello");
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b $a; world\n";
		stmt += "c ( resolve b )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((LeafNode)n).getValue(), "hello world");
	}

	@Test
	void testC() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "text $a; $b;\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "text");
		assertEquals( ((LeafNode)n).getValue(), "$a; $b;");
	}

	@Test
	void testD() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "c $a; $b;\n";
		stmt += "d ( resolve c )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "d");
		assertEquals( ((LeafNode)n).getValue(), "hello world");
	}
	
	//
	// Recursion works, but doesn't make any sense
	//
	
	@Test
	void testF() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "resolve ( resolve ( resolve ( c $a; $b; ) ) )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "hello world");
	}
	
	//
	// Resolve is recursive, but be care of the order.
	//
	
	@Test
	void testG() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "c $a;\n";
		stmt += "d $b;\n";
		stmt += "e $c; $d;\n";
		stmt += "resolve ( f $e; )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "hello world");
	}
	
	@Test
	void testH1() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a ==XXX::\n";
		stmt += "This\n";
		stmt += "is\n";
		stmt += "a\n";
		stmt += "test\n";
		stmt += "::XXX==\n";
		stmt += "resolve ~a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		final String c = System.lineSeparator();
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "This"+c+"is"+c+"a"+c+"test"+c);
	}
	
	@Test
	void testH2() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "~a ==::\n";
		stmt += "This\n";
		stmt += "is\n";
		stmt += "a\n";
		stmt += "test\n";
		stmt += "::==\n";
		stmt += "resolve ~a\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		final String c = System.lineSeparator();
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "This"+c+"is"+c+"a"+c+"test"+c);
	}

}
