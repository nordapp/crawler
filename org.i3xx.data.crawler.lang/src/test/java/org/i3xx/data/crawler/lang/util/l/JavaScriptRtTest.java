package org.i3xx.data.crawler.lang.util.l;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.data.crawler.lang.core.DataNode;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.ListNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.TupleParser;
import org.i3xx.data.crawler.lang.core.TupleResolver;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.junit.jupiter.api.Test;

class JavaScriptRtTest {

	@Test
	void testA() throws Exception {
		
		JavaScriptRt rt = new JavaScriptRt();
		
		String stmt = "";
		stmt += "var a = '100';\n";
		stmt += "a;\n";
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_js", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		
		Object rs = rt.exec(node, vars);
		
		assertTrue( rs instanceof DataNode );
		assertEquals( ((DataNode)rs).getName(), "test_js" );
		assertEquals( ((DataNode)rs).getData(), "100" );
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a ==::\n";
		stmt += "var a = 100;\n";
		stmt += "var b = 30;\n";
		stmt += "var c = a+b;\n";
		stmt += "c;\n";
		stmt += "::==\n";
		stmt += "b (javascript a)\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		r.getFunctions().put(JavaScriptRt.NAME, new JavaScriptRt());
		
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNode );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "javascript");
		
		assertTrue( n instanceof DataNode );
		assertEquals( ((DataNode)n).getData(), new Double(130));
		
	}

	@Test
	void testC() throws Exception {
		
		JavaScriptRt rt = new JavaScriptRt();
		
		String stmt = "";
		stmt += "b.getValue();\n";
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_js", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		
		vars.put("b", new LeafNodeImpl(Type.NODE, "b", "Hello World"));
		
		Object rs = rt.exec(node, vars);
		
		assertTrue( rs instanceof DataNode );
		assertEquals( ((DataNode)rs).getName(), "test_js" );
		assertEquals( ((DataNode)rs).getData(), "Hello World" );
	}

}
