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

/**
 * The return value is the variable 'engine_return',
 * otherwise you can set the '$' variables direct
 * in Phyton (Jython).
 * 
 * Example: a.setValue(100)
 * 
 * @author green
 *
 */
class PythonRtTest {

	@Test
	void testA() throws Exception {
		
		PythonRt rt = new PythonRt();
		
		String stmt = "";
		stmt += "a = 'hello world'\n";
		stmt += "print(a)\n";
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_py", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		
		Object rs = rt.exec(node, vars);
		System.out.println(rs);
		
		assertTrue( rs instanceof DataNode );
		assertEquals( ((DataNode)rs).getName(), "test_py" );
	}

	@Test
	void testB() throws Exception {
		
		ListNode list = new ListNodeImpl();
		String stmt = "";
		
		stmt += "a ==::\n";
		stmt += "a = 100;\n";
		stmt += "b = 30;\n";
		stmt += "c = a+b;\n";
		stmt += "engine_return = c;\n";
		stmt += "::==\n";
		stmt += "b (python a)\n";
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);
		
		TupleResolver r = new TupleResolver();
		r.getFunctions().put(PythonRt.NAME, new PythonRt());
		
		Node n = r.resolveAndGetLast(list);
		
		assertTrue( n instanceof LeafNode );
		assertEquals( n.getName(), "b");
		assertEquals( ((LeafNode)n).getValue(), "python");
		
		assertTrue( n instanceof DataNode );
		assertEquals( ((DataNode)n).getData(), new Integer(130));
		
	}

	@Test
	void testC() throws Exception {
		
		PythonRt rt = new PythonRt();
		
		String stmt = "";
		stmt += "print(a.getValue())\n";
		stmt += "a.setValue(\"hello world\")\n";
		stmt += "engine_return = a\n";
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_py", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		vars.put("?a", new LeafNodeImpl(Type.NODE, "a", "test"));
		
		Object rs = rt.exec(node, vars);
		
		assertTrue( rs instanceof DataNode );
		assertEquals( ((DataNode)rs).getName(), "test_py" );
		assertEquals( ((DataNode)rs).getData(), "hello world" );
	}
}
