package com.yaannsloot.jwolfram.entities;

/**
 * Represents a Wolfram|Alpha subpod. Subpods are subsections of information
 * contained within pods. For reference, here is the documentation that covers
 * all of the Wolfram|Alpha API, including subpods: <a href=
 * "http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf">http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf</a>
 */
public class Subpod {

	private String title;
	private String plaintext;
	private String imageurl;

	/**
	 * Creates an instance of a {@linkplain Subpod}, which represents a
	 * Wolfram|Alpha subpod
	 * 
	 * @param title     The title of the subpod
	 * @param plaintext The plaintext representation of this subpod
	 * @param imageurl  The image representation of this subpod
	 */
	public Subpod(String title, String plaintext, String imageurl) {
		this.title = title;
		this.plaintext = plaintext;
		this.imageurl = imageurl;
	}

	/**
	 * Retrieves the title of this subpod
	 * 
	 * @return A {@linkplain String} representing the title of this subpod
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Retrieves the plaintext representation of this subpod
	 * 
	 * @return A {@linkplain String} representing the plaintext version of this
	 *         subpod
	 */
	public String getPlaintext() {
		return plaintext;
	}

	/**
	 * Retrieves the url for the image representation of this subpod
	 * 
	 * @return A {@linkplain String} representing the url of the image version of
	 *         this subpod
	 */
	public String getImageUrl() {
		return imageurl;
	}

}
