package org.i3xx.data.crawler.lang.core;

public class DataNodeImpl implements DataNode{
	
	/** The type */
	private final Type type;
	
	/** The name */
	private final String name;

	/** The value */
	private final String value;

	/** The data */
	private final Object data;
	
	/**  */
	public DataNodeImpl() {
		this.type = Type.NODE;
		name = null;
		value = null;
		data = null;
	}
	
	/**  */
	public DataNodeImpl(Type type, String name, String value, Object data) {
		this.type = type;
		this.name = name;
		this.value = value;
		this.data = data;
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

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "data::"+name+"::'"+value+"::"+data+"'";
	}

}
