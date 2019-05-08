package org.i3xx.lemonade.lang.ext.util.f.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.ri.model.NodeIterator;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.i3xx.lemonade.lang.core.LeafNode;

public class ScratchAttributeIterator implements NodeIterator {
	
	private NodePointer parent;
	private LeafNode data;
	
    private List<String> attributes;
	private int position;
	
	public ScratchAttributeIterator(NodePointer parent, LeafNode data, String qname) {
		this.parent = parent;
		this.data = data;
		
		this.attributes = new ArrayList<String>();
		this.attributes.add(qname);
		
		this.position = 0;
	}
	
	// >> -------------- abstract -------------- >>
	
	public int getPosition() {
		return position;
	}

	public boolean setPosition(int position) {
        if (attributes == null) {
            return false;
        }
        this.position = position;
        return position >= 1 && position <= attributes.size();
	}

	public NodePointer getNodePointer() {
        if (position == 0) {
            if (!setPosition(1)) {
                return null;
            }
            position = 0;
        }
        int index = position - 1; //index in xpath is based 1
        if (index < 0) {
            index = 0;
        }
        return new ScratchAttributePointer(
            parent, data, attributes.get(index));
	}
	
	// << -------------- abstract -------------- <<

}
