package org.i3xx.lemonade.lang.core;

import java.util.List;

public interface ListNode extends Node {
	
	/**
	 * @param list
	 */
	void setStruct(List<Node> list);
	
	/**
	 * @return
	 */
	List<Node> getStruct();
}
