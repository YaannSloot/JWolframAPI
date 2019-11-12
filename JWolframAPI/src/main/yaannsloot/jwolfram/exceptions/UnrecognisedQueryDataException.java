package main.yaannsloot.jwolfram.exceptions;

public class UnrecognisedQueryDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7796409920617087653L;
	
	public UnrecognisedQueryDataException() {
		super("The provided data was not recognized as valid query data from the wolfram alpha api");
	}

}
