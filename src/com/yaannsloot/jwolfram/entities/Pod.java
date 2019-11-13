package com.yaannsloot.jwolfram.entities;

import java.util.List;

/**
 * Represents a Wolfram|Alpha pod. Pods are rectangular regions found in the
 * output of a query performed on the Wolfram|Alpha website. For reference, here
 * is the documentation that covers all of the Wolfram|Alpha API, including
 * pods: <a href=
 * "http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf">http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf</a>
 */
public class Pod {

	private String title;
	private boolean error;
	private int position;
	private String scanner;
	private String id;
	private List<Subpod> subpods;

	/**
	 * Creates a new instance of a {@linkplain Pod}, which represents a
	 * Wolfram|Alpha pod
	 * 
	 * @param title    The title of the pod
	 * @param error    Whether or not the pod had an error
	 * @param position The position of the pod
	 * @param scanner  The scanner of the pod
	 * @param id       The pod id
	 * @param subpods  A list of {@linkplain Subpod}s that were contained in this
	 *                 pod
	 */
	public Pod(String title, boolean error, int position, String scanner, String id, List<Subpod> subpods) {
		this.title = title;
		this.error = error;
		this.position = position;
		this.scanner = scanner;
		this.id = id;
		this.subpods = subpods;
	}

	/**
	 * Retrieves the title of this pod
	 * 
	 * @return A {@linkplain String} representing the pod title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * If some error happened with this pod, this will return true
	 * 
	 * @return A {@linkplain Boolean} representing the error flag for this pod
	 */
	public boolean getError() {
		return error;
	}

	/**
	 * Retrieves the recommended position for this pod
	 * 
	 * @return An {@linkplain Integer} representing the recommended position for
	 *         this pod
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Retrieves the id of the scanner associated with this pod
	 * 
	 * @return A {@linkplain String} representing the id of the scanner associated
	 *         with this pod
	 */
	public String getScannerID() {
		return scanner;
	}

	/**
	 * Retrieves the id of this pod
	 * 
	 * @return A {@linkplain String} representing the id of this pod
	 */
	public String getID() {
		return id;
	}

	/**
	 * Retrieves a {@linkplain List} of {@linkplain Subpod}s contained within this
	 * pod
	 * 
	 * @return A {@linkplain List} representing the {@linkplain Subpod}s contained
	 *         within this pod
	 */
	public List<Subpod> getSubpods() {
		return subpods;
	}

}
