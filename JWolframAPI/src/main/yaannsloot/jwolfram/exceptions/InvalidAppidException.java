package main.yaannsloot.jwolfram.exceptions;

/**
 * Thrown to indicate that the appid provided to the WolframClient constructor was invalid
 */
public class InvalidAppidException extends Exception {

	private static final long serialVersionUID = -1557299582004360058L;
	
	public InvalidAppidException(String appid) {
		super("The app id \"" + appid + "\" is invalid");
	}

}
