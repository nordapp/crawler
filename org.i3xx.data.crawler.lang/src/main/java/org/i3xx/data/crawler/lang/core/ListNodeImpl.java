package org.i3xx.data.crawler.lang.core;

import java.util.Collections;
import java.util.List;

public class ListNodeImpl implements ListNode {
	
	private final Type type;
	
	private final String name;
	
	private List<Node> struct;
	
	/**  */
	public ListNodeImpl() {
		this.type = Type.NODE;
		name = null;
		struct = null;
	}
	
	/**  */
	public ListNodeImpl(Type type, String name, List<Node> struct) {
		this.type = type;
		this.name = name;
		this.struct = struct;
	}
	
	@Override
	public List<Node> getStruct() {
		return struct;
	}
	
	/**
	 * @param struct
	 */
	public void setStruct(List<Node> struct) {
		this.struct = Collections.unmodifiableList(struct);
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<struct.size();i++) {
			if(i>0)
				buf.append(',');
			buf.append(struct.get(i).toString());
		}
		
		return "{\""+name+"\":["+buf.toString()+"]}";
	}

}
