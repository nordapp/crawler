package org.i3xx.data.crawler.lang.util.l;

public class LScriptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	private int line;
	
	/**  */
	private int col;
	
	/**  */
	private String source;
	
	/**
	 * 
	 */
	public LScriptException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public LScriptException(String message) {
		super(message);
	}
	
	/**
	 * @param cause
	 */
	public LScriptException(Exception cause) {
		super(cause);
	}
	
	/**
	 * @param cause
	 * @param line
	 * @param col
	 * @param source
	 */
	public LScriptException(Exception cause, int line, int col, String source) {
		super(cause);
		
		this.line = line;
		this.col = col;
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
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
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
