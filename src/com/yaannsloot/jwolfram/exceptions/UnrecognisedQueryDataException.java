package com.yaannsloot.jwolfram.exceptions;

/**
 * Thrown to indicate that the data provided does not match valid Wolfram|Alpha API query data
 */
public class UnrecognisedQueryDataException extends RuntimeException {

	private static final long serialVersionUID = 7796409920617087653L;
	
	public UnrecognisedQueryDataException() {
		super("The provided data was not recognized as valid query data from the wolfram alpha api");
	}

}
