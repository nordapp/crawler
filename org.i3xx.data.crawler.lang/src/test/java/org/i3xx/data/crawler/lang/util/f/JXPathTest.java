package org.i3xx.data.crawler.lang.util.f;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.ListNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.f.json.ScratchPointerFactory;
import org.junit.jupiter.api.Test;

class JXPathTest {

	@Test
	void test() {
		
		List<Node> la = new ArrayList<Node>();
		la.add( new LeafNodeImpl(Type.NODE, "key1", "value1") );
		la.add( new LeafNodeImpl(Type.NODE, "key2", "value2") );
		la.add( new LeafNodeImpl(Type.NODE, "key3", "value3") );
		la.add( new LeafNodeImpl(Type.NODE, "key4", "value4") );
		
		List<Node> lb = new ArrayList<Node>();
		lb.add( new ListNodeImpl(Type.NODE, "a", la) );
		lb.add( new ListNodeImpl(Type.NODE, "b", la) );
		lb.add( new ListNodeImpl(Type.NODE, "c", la) );
		lb.add( new ListNodeImpl(Type.NODE, "d", la) );
		
		ListNode root = new ListNodeImpl(Type.NODE, "root", lb);
		
		JXPathContextReferenceImpl.addNodePointerFactory(new ScratchPointerFactory());
		JXPathContext c = JXPathContext.newContext(root);
		
		assertEquals( c.getValue("/b/key2/@value"), "value2" );
		assertEquals( c.getValue("/a/key1/@value"), "value1" );
		assertEquals( c.getValue("/c/key3"), ((ListNode)root.getStruct().get(2)).getStruct().get(2) );
		assertEquals( c.getValue("/a/key4"), ((ListNode)root.getStruct().get(0)).getStruct().get(3) );
		assertEquals( c.getValue("/b"), root.getStruct().get(1) );
		assertEquals( c.getValue("/"), root );
	}

}
