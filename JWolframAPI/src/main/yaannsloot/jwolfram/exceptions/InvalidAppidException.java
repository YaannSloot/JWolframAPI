package main.yaannsloot.jwolfram.exceptions;

public class InvalidAppidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1557299582004360058L;
	
	public InvalidAppidException(String appid) {
		super("The app id \"" + appid + "\" is invalid");
	}

}
