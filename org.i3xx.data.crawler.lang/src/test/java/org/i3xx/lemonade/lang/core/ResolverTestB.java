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

/**
 * The test is set and resolve
 * 
 * @author green
 *
 */
class ResolverTestB {

	@Test
	void testA1() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "^a hello\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "hello");
	}

	@Test
	void testA2() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b world\n";
		stmt += "c (^a a)\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "c");
		assertEquals( ((LeafNode)n).getValue(), "hello");
	}

	@Test
	void testB1() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "^b $a; world\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "unknown");
		assertEquals( ((LeafNode)n).getValue(), "hello world");
	}

	@Test
	void testB2() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a hello\n";
		stmt += "b $a; world\n";
		stmt += "^c b\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
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
		stmt += "^c $a; $b;\n";
		
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
		stmt += "^f $e;\n";
		
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
		
		stmt += "a ==XXX::\n";
		stmt += "This\n";
		stmt += "is\n";
		stmt += "a\n";
		stmt += "test\n";
		stmt += "::XXX==\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		final String c = System.lineSeparator();
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "a");
		assertEquals( ((LeafNode)n).getValue(), "This"+c+"is"+c+"a"+c+"test"+c);
	}
	
	@Test
	void testH2() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a ==::\n";
		stmt += "This\n";
		stmt += "is\n";
		stmt += "a\n";
		stmt += "test\n";
		stmt += "::==\n";
		stmt += "b (^a a)\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		final String c = System.lineSeparator();
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "This"+c+"is"+c+"a"+c+"test"+c);
	}

}
