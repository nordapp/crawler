package org.i3xx.lemonade.lang.ext.util.f.json;

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.i3xx.lemonade.lang.core.LeafNode;

/**
 * Caution: There is a difference between attributes and fields.
 * 
 * @author Stefan Hauptmann
 *
 */
public class ScratchAttributePointer extends NodePointer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String qname;
	private LeafNode data;

	public ScratchAttributePointer(NodePointer parent, LeafNode data, String qname) {
		super(parent);
		
		this.qname = qname;
		this.data = data;
	}
	
	// >> -------------- abstract -------------- >>
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public QName getName() {
		return new QName(qname);
	}

	@Override
	public Object getBaseValue() {
		return data;
	}

	@Override
	public Object getImmediateNode() {
		return data;
	}

	@Override
	public void setValue(Object value) {
		//TODO: implement set value
	}

	@Override
	public int compareChildNodePointers(NodePointer pointer1, NodePointer pointer2) {
        // Won't happen - attributes don't have children
		// and proxies are handled otherwise 
		return 0;
	}
	
	// << -------------- abstract -------------- <<

	@Override
	public Object getValue() {
		return data.getValue();
	}
	
}
