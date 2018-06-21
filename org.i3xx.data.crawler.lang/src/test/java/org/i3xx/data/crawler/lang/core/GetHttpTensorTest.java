package org.i3xx.data.crawler.lang.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

class GetHttpTensorTest {

	@Disabled
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		//http://192.168.214.120:10080/config/primary/blob/master/custom/monades-test.properties
		
		stmt += "host http://192.168.214.146:8086\n";
		stmt += "addr $host;/if/get-latest-worknumber\n";
		stmt += "resolve addr\n";
		stmt += "gethttp: addr\n";
		stmt += ":jxpath gethttp\n";
		stmt += "resolve jxpath\n";
		stmt += "jxpath /result/content/@value\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "jxpath");
		assertEquals( ((LeafNode)n).getValue(), "\"6\"");
	}

	@Disabled
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		//http://192.168.214.120:10080/config/primary/blob/master/custom/monades-test.properties
		
		stmt += "host http://192.168.214.146:8086\n";
		stmt += "addr $host;/if/get-latest-worknumber\n";
		stmt += "a ( gethttp ( resolve addr ) )\n";
		stmt += "jxpath ( a /result/content/@value )\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "a");
		assertEquals( ((LeafNode)n).getValue(), "\"6\"");
	}

	@Disabled
	void testC() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		//http://192.168.214.120:10080/config/primary/blob/master/custom/monades-test.properties
		
		stmt += "gethttp http://192.168.214.146:8086/if/get-latest-worknumber\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof ListNodeImpl );
		assertEquals( n.getName(), "gethttp");
		//assertEquals( ((LeafNode)n).getValue(), "\"6\"");
	}

}
