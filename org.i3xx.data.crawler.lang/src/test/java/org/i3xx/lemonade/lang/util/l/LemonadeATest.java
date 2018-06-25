package org.i3xx.lemonade.lang.util.l;

import java.util.HashMap;
import java.util.Map;

import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.TupleParser;
import org.i3xx.lemonade.lang.core.TupleResolver;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.junit.jupiter.api.Test;

class LemonadeATest {

	@Test
	void test() throws Exception {
		
		String stmt = URLTool.readText( LemonadeATest.class.getResource("ATest.lem") );
		
		ListNode list = new ListNodeImpl();
		
		TupleParser p = new TupleParser();
		p.parse(list, stmt);

		TupleResolver r = new TupleResolver();
		r.getFunctions().put(LemonadeRt.NAME, new LemonadeRt());
		Node n = r.resolveAndGetLast(list);
		System.out.println(n);
		
	}

	@Test
	void testB() throws Exception {
		
		String stmt = URLTool.readText( LemonadeATest.class.getResource("ATest.lem") );
		
		LemonadeRt rt = new LemonadeRt();
		
		LeafNode node = new LeafNodeImpl(Type.NODE, "test_lem", stmt);
		Map<String, Node> vars = new HashMap<String, Node>();
		
		Object rs = rt.exec(node, vars);
		System.out.println(rs);
		
	}

}
