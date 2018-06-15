package org.i3xx.data.crawler.lang.core;

public class LeafNodeImpl implements LeafNode {
	
	/** The type */
	private final Type type;
	
	/** The name */
	private final String name;

	/** The value */
	private final String value;
	
	/**  */
	public LeafNodeImpl() {
		this.type = Type.NODE;
		name = null;
		value = null;
	}
	
	/**  */
	public LeafNodeImpl(Type type, String name, String value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	/**  */
	public LeafNodeImpl(Type type, String name, int value) {
		this.type = type;
		this.name = name;
		this.value = String.valueOf(value);
	}
	
	/**  */
	public LeafNodeImpl(Type type, String name, long value) {
		this.type = type;
		this.name = name;
		this.value = String.valueOf(value);
	}
	
	/**  */
	public LeafNodeImpl(Type type, String name, float value) {
		this.type = type;
		this.name = name;
		this.value = String.valueOf(value);
	}
	
	/**  */
	public LeafNodeImpl(Type type, String name, double value) {
		this.type = type;
		this.name = name;
		this.value = String.valueOf(value);
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "leaf::"+name+"::'"+value+"'";
	}
}
