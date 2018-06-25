package org.i3xx.lemonade.lang.core;

public class ScriptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	private int line;
	
	/**  */
	private String source;
	
	/**
	 * 
	 */
	public ScriptException() {
		super();
		
		line = 0;
		source = null;
	}
	
	/**
	 * 
	 */
	public ScriptException(String message, int line, String source) {
		super(message);
		
		this.line = line;
		this.source = source;
	}
	
	/**
	 * 
	 */
	public ScriptException(Exception e) {
		super(e);
		
		line = 0;
		source = null;
	}
	
	/**
	 * 
	 */
	public ScriptException(Exception e, int line, String source) {
		super(e);
		
		this.line = line;
		this.source = source;
	}
	
	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
