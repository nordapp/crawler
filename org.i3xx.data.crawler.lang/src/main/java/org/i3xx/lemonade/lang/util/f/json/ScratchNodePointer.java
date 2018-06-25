package org.i3xx.lemonade.lang.util.f.json;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.compiler.NodeNameTest;
import org.apache.commons.jxpath.ri.compiler.NodeTest;
import org.apache.commons.jxpath.ri.compiler.NodeTypeTest;
import org.apache.commons.jxpath.ri.model.NodeIterator;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.Node;

public class ScratchNodePointer extends NodePointer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Node node;
	protected List<Node> nodelist;
	
	/**
     * @param node pointed
     * @param locale Locale
	 */
	public ScratchNodePointer(Node node, Locale locale) {
		super(null, locale);
		//Println.debug("constructor (IBrick, Locale) ::= "+node+","+locale);
		
        this.node = node;
        this.nodelist = ScratchTools.childrenToList(node);
	}
	
	public ScratchNodePointer(NodePointer parent, Node node) {
		super(parent);
		//Println.debug("constructor (NodePointer, IBrick) ::= "+parent+","+node);
		
        this.node = node;
        this.nodelist = ScratchTools.childrenToList(node);
	}
	
	// >> -------------- abstract -------------- >>
	
	@Override
	public boolean isLeaf() {
		//Println.debug("isLeaf");
		return false;
	}
	
	@Override
	public boolean isCollection() {
		//Println.debug("isCollection");
		return false;
	}

	@Override
	public int getLength() {
		//Println.debug("getLength");
		return 1;
	}

	@Override
	public QName getName() {
		//Println.debug("getName");
		return new QName(node.getName());
	}

    /* 
     * Returns the value represented by the pointer before indexing.
     * So, if the node represents an element of a collection, this
     * method returns the collection itself.
     */
	@Override
	public Object getBaseValue() {
		//Println.debug("getBaseValue");
		return ScratchTools.childrenToList(node);
	}
	
	/*
	 * Returns the value represented by the pointer. This is the node itself.
	 */
	@Override
	public Object getValue() {
		//Println.debug("getValue");
		return super.getValue();
	}

	@Override
	public Object getImmediateNode() {
		//Println.debug("getImmediateNode");
		return node;
	}

	@Override
	public void setValue(Object value) {
		//Println.debug("setValue");
	}

	@Override
	public int compareChildNodePointers(NodePointer pointer1, NodePointer pointer2) {
		//Println.debug("compareChildNodePointers");
		
        Object node1 = pointer1.getBaseValue();
        Object node2 = pointer2.getBaseValue();
        if (node1 == node2) {
            return 0;
        }
        if ((node1 instanceof Map) && !(node2 instanceof Map)) {
            return -1;
        }
        if (
            !(node1 instanceof Map) && (node2 instanceof Map)) {
            return 1;
        }
        if (
            (node1 instanceof Node) && (node2 instanceof Node)) {
            List<Node> list = ScratchTools.childrenToList(node)/*ScratchTools.childrenToList( (IBrick)getNode() )*/;
            int length = list.size();
            for (int i = 0; i < length; i++) {
                Object n = list.get(i);
                if (n == node1) {
                    return -1;
                }
                else if (n == node2) {
                    return 1;
                }
            }
            return 0; // Should not happen
        }

        if (!(node instanceof Node)) {
            throw new RuntimeException(
                "JXPath internal error: "
                    + "compareChildNodes called for "
                    + node);
        }

        List<Node> children = ScratchTools.childrenToList(node)/*ScratchTools.childrenToList( node )*/;
        int length = children.size();
        for (int i = 0; i < length; i++) {
            Object n = children.get(i);
            if (n == node1) {
                return -1;
            }
            if (n == node2) {
                return 1;
            }
        }

        return 0;
	}
	
	// << -------------- abstract -------------- <<
	
	@Override
    public NodeIterator childIterator(NodeTest test, boolean reverse, NodePointer startWith) {
		//Println.debug("ScratchNodePointer.childIterator ::= "+test+" "+startWith+" "+reverse+" "+node.ID()+" "+node.classname()+" "+node.name());
		
		//
		//List<IBrick> nodelist = ScratchTools.childrenToList(node);
		return new ScratchNodeIterator(this, ScratchTools.childrenToList(node), reverse, test, startWith);
    }
	
	@Override
    public NodeIterator attributeIterator(QName qname) {
		//Println.debug("ScratchNodePointer.attributeIterator ::= "+qname);
		
		if(node instanceof ListNode) {
			throw new IllegalArgumentException("The path '"+super.asPath()+"/"+qname.getName()+"' is not an attribute.");
		}
		
		return new ScratchAttributeIterator(parent, (LeafNode)node, qname.getName());
    }
	
	@Override
	public NodePointer getImmediateParentPointer() {
		//Println.debug("ScratchNodePointer.getImmediateParentPointer");
		
		NodePointer ptr = super.getImmediateParentPointer();
		return ptr;
	}
	
	@Override
    public int hashCode() {
        return node.hashCode();
    }

	@Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof ScratchNodePointer)) {
            return false;
        }

        ScratchNodePointer other = (ScratchNodePointer) object;
        return node == other.node;
    }
	
    /**
     * Execute test against node on behalf of pointer.
     * @param pointer Pointer
     * @param node to test
     * @param test to execute
     * @return true if node passes test
     */
    public static boolean testNode(
        NodePointer pointer,
        Node node,
        NodeTest test) {
        if (test == null) {
            return true;
        }
        if (test instanceof NodeNameTest) {
            if (!(node instanceof Node)) {
                return false;
            }
            
            NodeNameTest nodeNameTest = (NodeNameTest) test;
            QName testName = nodeNameTest.getNodeName();
            boolean wildcard = nodeNameTest.isWildcard();
            String testPrefix = testName.getPrefix();
            if (wildcard && testPrefix == null) {
                return true;
            }
            if (wildcard || testName.getName().equals( node.getName() ) ) {
            	return true;
            }
            return false;
        }
        if (test instanceof NodeTypeTest) {
        	return true;
        }
        return false;
    }
}
