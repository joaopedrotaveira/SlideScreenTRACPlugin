package com.thetransactioncompany.jsonrpc2;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * The base abstract class for JSON-RPC 2.0 requests, notifications and
 * responses. Provides generic methods for parsing (from JSON string) and
 * serialisation (to JSON string) of the three message types.
 *
 * <p>Example showing parsing and serialisation back to JSON:
 *
 * <pre>
 * String jsonString = "{\"method\":\"progressNotify\",\"params\":[\"75%\"],\"jsonrpc\":\"2.0\"}";
 *
 * JSONRPC2Message message = null;
 *
 * // parse
 * try {
 *        message = JSONRPC2Message.parse(jsonString);
 * } catch (JSONRPC2ParseException e) {
 *        // handle parse error
 * }
 *
 * if (message instanceof JSONRPC2Request)
 *        System.out.println("The message is a request");
 * else if (message instanceof JSONRPC2Notification)
 *        System.out.println("The message is a notification");
 * else if (message instanceof JSONRPC2Response)
 *        System.out.println("The message is a response");
 *
 * // serialise back to JSON string
 * System.out.println(message);
 *
 * </pre>
 *
 * <p id="map">The mapping between JSON and Java entities (as defined by the 
 * underlying JSON.simple library): 
 * <pre>
 *     true|false  <--->  java.lang.Boolean
 *     number      <--->  java.lang.Number
 *     string      <--->  java.lang.String
 *     array       <--->  java.util.List
 *     object      <--->  java.util.Map
 *     null        <--->  null
 * </pre>
 *
 * <p>The JSON-RPC 2.0 specification and user group forum can be found 
 * <a href="http://groups.google.com/group/json-rpc">here</a>.
 * 
 * @author <a href="http://dzhuvinov.com">Vladimir Dzhuvinov</a>
 * @version 1.14 (2011-03-09)
 */
public abstract class JSONRPC2Message {


	/** 
	 * Keep a ready JSON parser instance at hand. 
	 */
	protected static final JSONParser parser = new JSONParser();
	
	
	/**
	 * Special container factory for constructing JSON objects in a way
	 * that preserves their original member order.
	 */
	protected static final ContainerFactory linkedContainerFactory = new ContainerFactory() {
	
		// Yes, there is a typo here!
		public List<Object> creatArrayContainer() {
			return new LinkedList<Object>();
		}
		
		public Map<String, Object> createObjectContainer() {
			return new LinkedHashMap<String, Object>();
		}
	};
	
	
	/** 
	 * Provides common parsing of JSON-RPC 2.0 requests, notifications 
	 * and responses. Use this method if you don't know which type of 
	 * JSON-RPC message the input string represents.
	 *
	 * <p>If you are certain about the message type use the dedicated 
	 * {@link JSONRPC2Request#parse}, {@link JSONRPC2Notification#parse} 
	 * and {@link JSONRPC2Response#parse} methods. They are more efficient 
	 * and would provide you with better parse error reporting.
	 *
	 * <p>The member order of parsed JSON objects will not be preserved 
	 * (for efficiency reasons) and the JSON-RPC 2.0 version field must be 
	 * set to "2.0". To change this behaviour check the optional {@link 
	 * #parse(String,boolean,boolean)} method.
	 *
	 * @param jsonString A JSON string representing a JSON-RPC 2.0 request, 
	 *                   notification or response, UTF-8 encoded.
	 *
	 * @return An instance of {@link JSONRPC2Request}, 
	 *         {@link JSONRPC2Notification} or {@link JSONRPC2Response}.
	 *
	 * @throws JSONRPC2ParseException With detailed message if the parsing 
	 *                                failed.
	 */
	public static JSONRPC2Message parse(final String jsonString)
		throws JSONRPC2ParseException {

		return parse(jsonString, false, false);
	}
	
	
	/** 
	 * Provides common parsing of JSON-RPC 2.0 requests, notifications 
	 * and responses. Use this method if you don't know which type of 
	 * JSON-RPC message the input string represents.
	 *
	 * <p>If you are certain about the message type use the dedicated 
	 * {@link JSONRPC2Request#parse}, {@link JSONRPC2Notification#parse} 
	 * and {@link JSONRPC2Response#parse} methods. They are more efficient 
	 * and would provide you with better parse error reporting.
	 *
	 * @param jsonString    A JSON string representing a JSON-RPC 2.0 
	 *                      request, notification or response, UTF-8
	 *                      encoded.
	 * @param preserveOrder If {@code true} the member order of JSON objects
	 *                      in parameters and results will be preserved.
	 * @param noStrict      If {@code true} the {@code "jsonrpc":"2.0"}
	 *                      version field in the JSON-RPC 2.0 message will 
	 *                      not be checked.
	 *
	 * @return An instance of {@link JSONRPC2Request}, 
	 *         {@link JSONRPC2Notification} or {@link JSONRPC2Response}.
	 *
	 * @throws JSONRPC2ParseException With detailed message if the parsing 
	 *                                failed.
	 */
	public static JSONRPC2Message parse(final String jsonString, final boolean preserveOrder, final boolean noStrict)
		throws JSONRPC2ParseException {
		
		// Try each of the parsers until one succeeds (or all fail)
		try {
			return JSONRPC2Request.parse(jsonString, preserveOrder, noStrict);

		} catch (JSONRPC2ParseException e) {
			// ignore
		}
		
		try {
			return JSONRPC2Notification.parse(jsonString, preserveOrder, noStrict);
			
		} catch (JSONRPC2ParseException e) {
			// skip
		}
		
		try {
			return JSONRPC2Response.parse(jsonString, preserveOrder, noStrict);
			
		} catch (JSONRPC2ParseException e) {
			// skip
		}
		
		// Before we throw the parse exception check if
		// the string is a valid JSON at all
		try {
			//JSONObject json = (JSONObject)
			parser.parse(jsonString);
		} catch (ParseException e) {
			// Not JSON
			throw new JSONRPC2ParseException("Invalid JSON: " + e.getMessage(), jsonString);
		}
		
		throw new JSONRPC2ParseException("Invalid JSON-RPC 2.0 message", jsonString);
	}
	
	
	/**
	 * Parses a JSON object string. Provides the initial parsing of JSON-RPC
	 * 2.0 messages.
	 *
	 * @param jsonString    A string representing a JSON object.
	 * @param preserveOrder If {@code true} the member order of JSON objects
	 *                      will be preserved.
	 *
	 * @return The parsed JSON object.
	 *
	 * @throws JSONRPC2ParseException With detailed message if parsing 
	 *                                failed.
	 */
	@SuppressWarnings("unchecked")
	protected static Map<String, Object> parseJSONObject(final String jsonString, final boolean preserveOrder)
		throws JSONRPC2ParseException {
	
		if (jsonString == null)
			throw new JSONRPC2ParseException("Null argument");
		
		if ("".equals(jsonString.trim()))
			throw new JSONRPC2ParseException("Invalid JSON: Empty string");
		
		Object json;
		
		// Parse the JSON string
		try {
			if (preserveOrder)
				json = parser.parse(jsonString, linkedContainerFactory);
			else
				json = parser.parse(jsonString);
			
		} catch (ParseException e) {
			// Not JSON
			String message = e.getMessage();
			
			if (message == null)
				throw new JSONRPC2ParseException("Invalid JSON", jsonString);
			else
				throw new JSONRPC2ParseException("Invalid JSON: " + e.getMessage(), jsonString);
		
		} 
		
		if (json instanceof List<?>)
			throw new JSONRPC2ParseException("JSON-RPC 2.0 batch requests/notifications not supported");
			
		if (! (json instanceof Map<?,?>))
			throw new JSONRPC2ParseException("Invalid JSON-RPC 2.0 message: Message must be a JSON object");
		
		return (Map<String,Object>)json;
	}
	
	
	/**
	 * Ensures the specified parameter is a {@code String} object set to 
	 * "2.0". This method is intended to check the "jsonrpc" field during 
	 * parsing of JSON-RPC messages.
	 *
	 * @param version    The version parameter.
	 * @param jsonString The original JSON string.
	 *
	 * @throws JSONRPC2Exception If the parameter is not a string matching
	 *                           "2.0".
	 */
	protected static void ensureVersion2(final Object version, final String jsonString)
		throws JSONRPC2ParseException {
	
		if (version == null)
			throw new JSONRPC2ParseException("Invalid JSON-RPC 2.0: Version string missing", jsonString);
			
		else if (! (version instanceof String))
			throw new JSONRPC2ParseException("Invalid JSON-RPC 2.0: Version not a JSON string", jsonString);
			
		else if (! version.equals("2.0"))
			throw new JSONRPC2ParseException("Invalid JSON-RPC 2.0: Version must be \"2.0\"", jsonString);
	}

	
	/** 
	 * Gets a JSON object representing this message.
	 *
	 * @return A JSON object.
	 */
	public abstract JSONObject toJSON();
	
	
	/** 
	 * Serialises this message to a JSON string.
	 *
	 * @return A JSON-RPC 2.0 encoded string.
	 */
	public String toString() {
		
		return toJSON().toString();
	}
}
