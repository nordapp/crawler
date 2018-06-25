package org.i3xx.lemonade.lang.core;

/**
 * Only a module can create a data node
 * 
 * @author green
 *
 */
public interface DataNode extends LeafNode {

	Object getData();
}
