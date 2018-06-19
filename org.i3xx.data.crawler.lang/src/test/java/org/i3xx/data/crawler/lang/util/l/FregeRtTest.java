package org.i3xx.data.crawler.lang.util.l;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.data.crawler.lang.core.DataNode;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.junit.jupiter.api.Test;

class FregeRtTest {

	@Test
	void testA() throws Exception {
		
		FregeRt rt = new FregeRt();
		
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
}
