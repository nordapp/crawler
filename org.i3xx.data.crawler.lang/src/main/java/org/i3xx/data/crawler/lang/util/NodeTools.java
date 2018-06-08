package org.i3xx.data.crawler.lang.util;

import java.util.ArrayList;
import java.util.List;

import org.i3xx.data.crawler.lang.core.ListNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.core.ListNode;

public class NodeTools {

	/**
	 * @param list
	 * @return
	 */
	public static final List<Node> makeModifyable(List<Node> list) {
		return new ArrayList<Node>(list);
	}

	/**
	 * @param type
	 * @param name
	 * @param node
	 * @return
	 */
	public static final ListNode newListNode(Type type, String name, Node node) {
		List<Node> list = new ArrayList<Node>();
		list.add(node);
		return new ListNodeImpl(type, name, list);
	}
}
