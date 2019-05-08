package org.i3xx.lemonade.lang.ext.util.f.json;

import java.util.Locale;

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.NodePointerFactory;
import org.i3xx.lemonade.lang.core.Node;

public class ScratchPointerFactory implements NodePointerFactory {
	
    /** factory order constant */
    public static final int SCRATCH_POINTER_FACTORY_ORDER = 120;

	public ScratchPointerFactory() {}
	
	// >> -------------- abstract -------------- >>
	
	public int getOrder() {
		return SCRATCH_POINTER_FACTORY_ORDER;
	}

	public NodePointer createNodePointer(QName name, Object object, Locale locale) {
		//Println.debug("createNodePointer (QName, Object, Locale)");
        if (object instanceof Node) {
            return new ScratchNodePointer((Node)object, locale);
        }
		return null;
	}

	public NodePointer createNodePointer(NodePointer parent, QName name, Object object) {
		//Println.debug("createNodePointer (NodePointer, QName, Object)");
        if (object instanceof Node) {
            return new ScratchNodePointer(parent, (Node)object);
        }
		return null;
	}
	
	// << -------------- abstract -------------- <<

}
