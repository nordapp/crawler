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
import org.junit.jupiter.api.Disabled;

class GitHubPropsTest {

	@Disabled
	void testA() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		//http://192.168.214.120:10080/config/primary/blob/master/custom/monades-test.properties
		
		stmt += "host http://192.168.214.120:10080/config/primary/raw/master\n";
		stmt += "addr $host;/custom/monades-test.properties\n";
		stmt += "resolve addr\n";
		stmt += "gitprops~ addr\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "gitprops");
	}

	@Disabled
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "host http://192.168.214.120:10080/config/primary/raw/master\n";
		stmt += "addr ( resolve $host;/custom/monades-test.properties )\n";
		stmt += "gitprops: addr\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		Node n = r.resolveAndGetLast(list);
		Node v = r.getVariables().get("location");
		
		assertTrue( n instanceof LeafNodeImpl );
		assertEquals( n.getName(), "gitprops");
		
		assertTrue( v instanceof LeafNodeImpl );
		assertEquals( v.getName(), "location");
		assertEquals( ((LeafNode)v).getValue(), "Germany");
	}

}
