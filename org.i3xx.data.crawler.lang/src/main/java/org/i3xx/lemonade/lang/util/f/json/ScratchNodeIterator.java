package org.i3xx.lemonade.lang.util.f.json;

import java.util.List;

import org.apache.commons.jxpath.ri.compiler.NodeTest;
import org.apache.commons.jxpath.ri.model.NodeIterator;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.i3xx.lemonade.lang.core.Node;

public class ScratchNodeIterator implements NodeIterator {
	
	protected int position;
	protected List<Node> nodelist;
	protected NodePointer parent;
	protected boolean reverse;
	
	//the test data and list index
	protected NodeTest test;
	protected int index;
	protected Node node;
	
	public ScratchNodeIterator(NodePointer parent, List<Node> nodelist, 
			boolean reverse, NodeTest test, NodePointer startWith) {
		
		this.position = 0;
		this.nodelist = nodelist;
		this.parent = parent;
		this.reverse = reverse;
		
		this.test = test;
		this.index = 0;
		this.node = null; //can be set as starts with IBrick here (than start index is found)
	}
	
	// >> -------------- abstract -------------- >>
	
	public int getPosition() {
		//Println.debug("ScratchNodeIterator.getPosition");
		return this.position;
	}

	public boolean setPosition(int position) {
		//Println.debug("ScratchNodeIterator.setPosition");
        while (this.position < position) {
            if (!next()) {
                return false;
            }
        }
        while (this.position > position) {
            if (!previous()) {
                return false;
            }
        }
        return true;
	}

	public NodePointer getNodePointer() {
		//Println.debug("ScratchNodeIterator.getNodePointer");
        if (node == null) {
            if (!setPosition(1)) {
                return null;
            }
            position = 0;
        }
		return new ScratchNodePointer(parent, node);
	}
	
	// << -------------- abstract -------------- <<
	
	private boolean previous() {
        position--;
        if (!reverse) {
            while (--index >= 0) {
                node = nodelist.get(index);
                if (testChild()) {
                    return true;
                }
            }
        }
        else {
            for (; index < nodelist.size(); index++) {
            	node = nodelist.get(index);
                if (testChild()) {
                    return true;
                }
            }
        }
        return false;
	}
	
	private boolean next() {
        position++;
        if (!reverse) {
            if (position == 1) {
                index = 0;
                if (node != null) {
                    index = nodelist.indexOf(node) + 1;
                }
            }
            else {
                index++;
            }
            for (; index < nodelist.size(); index++) {
            	node = nodelist.get(index);
                if (testChild()) {
                    return true;
                }
            }
            return false;
        }
        else {
            if (position == 1) {
                index = nodelist.size() - 1;
                if (node != null) {
                    index = nodelist.indexOf(node) - 1;
                }
            }
            else {
                index--;
            }
            for (; index >= 0; index--) {
            	node = nodelist.get(index);
                if (testChild()) {
                    return true;
                }
            }
            return false;
        }
	}
	
	/**
	 * @return
	 */
	private boolean testChild() {
    	if(node == null){
    		return false;
    	}
		return ScratchNodePointer.testNode(parent, node, test);
	}
}
