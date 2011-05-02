package com.thetransactioncompany.jsonrpc2;


/** 
 * Thrown to indicate an exception during the parsing of a JSON-RPC 2.0 
 * message string.
 *
 * <p>The JSON-RPC 2.0 specification and user group forum can be found 
 * <a href="http://groups.google.com/group/json-rpc">here</a>.
 *
 * @author <a href="http://dzhuvinov.com">Vladimir Dzhuvinov</a>
 * @version 1.14 (2010-03-15)
 */
public class JSONRPC2ParseException extends Exception {
	
	private static final long serialVersionUID = 883977515113619976L;

	/** The string that didn't parse */
	private String unparsableString = null;
	
	
	/** 
	 * Creates a new parse exception with the specified message.
	 *
	 * @param message The exception message.
	 */
	public JSONRPC2ParseException(String message) {
	
		super(message);
	}
	
	
	/**
	 * Creates a new parse exception with the specified message
	 * and the original string that didn't parse.
	 *
	 * @param message          The exception message.
	 * @param unparsableString The unparsable string.
	 */
	public JSONRPC2ParseException(String message, String unparsableString) {
	
		super(message);
		this.unparsableString = unparsableString;
	}
	
	
	/**
	 * Gets original string that caused the parse exception (if specified).
	 *
	 * @return The string that didn't parse, {@code null} if none.
	 */
	public String getUnparsableString() {
	
		return unparsableString;
	}
}
