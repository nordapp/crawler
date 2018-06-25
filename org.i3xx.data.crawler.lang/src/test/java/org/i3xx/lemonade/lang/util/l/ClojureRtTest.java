package org.i3xx.lemonade.lang.util.l;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.lemonade.lang.core.DataNode;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.i3xx.lemonade.lang.util.l.ClojureRt;
import org.junit.jupiter.api.Test;

/**
 * The return value is the variable 'engine_return',
 * usually you write 'engine_return = a;' in the last line,
 * otherwise you can set the '$' variables direct
 * in Clojure (Clojure).
 * 
 * Example: a.setValue(100)
 * 
 * @author green
 *
 */
class ClojureRtTest {

	@Test
	void testA() throws Exception {
		
		ClojureRt rt = new ClojureRt();
		
		String stmt = "";
		stmt += "(def a \"hello world\")\n";
		stmt += "a\n";
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_cli", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		
		Object rs = rt.exec(node, vars);
		System.out.println(rs);
		
		assertTrue( rs instanceof DataNode );
		assertEquals( ((DataNode)rs).getName(), "test_cli" );
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a ==::\n";
		stmt += "(def a 100)\n";
		stmt += "(def b 30)\n";
		stmt += "(def c (+ a b))\n";
		stmt += "c\n";
		stmt += "::==\n";
		stmt += "b (clojure a)\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		r.getFunctions().put(ClojureRt.NAME, new ClojureRt());
		
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNode );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "clojure");
		
		assertTrue( n instanceof DataNode );
		assertEquals( ((DataNode)n).getData(), new Long(130));
		
	}

	@Test
	void testC() throws Exception {
		
		ClojureRt rt = new ClojureRt();
		
		String stmt = "";
		stmt += "( print (. a getValue) )\n";
		stmt += "(. a setValue \"hello world\"  )\n";
		stmt += "a";
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_cli", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		vars.put("~a", new LeafNodeImpl(Type.NODE, "a", "test"));
		
		Object rs = rt.exec(node, vars);
		
		assertTrue( rs instanceof DataNode );
		assertEquals( ((DataNode)rs).getName(), "test_cli" );
		assertEquals( ((DataNode)rs).getData(), "hello world" );
	}
}
