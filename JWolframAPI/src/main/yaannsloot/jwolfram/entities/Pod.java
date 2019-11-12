package main.yaannsloot.jwolfram.entities;

import java.util.List;

public class Pod {

	private String title;
	private boolean error;
	private int position;
	private String scanner;
	private String id;
	private List<Subpod> subpods;

	public Pod(String title, boolean error, int position, String scanner, String id, List<Subpod> subpods) {
		this.title = title;
		this.error = error;
		this.position = position;
		this.scanner = scanner;
		this.id = id;
		this.subpods = subpods;
	}

	public String getTitle() {
		return title;
	}

	public boolean getError() {
		return error;
	}

	public int getPosition() {
		return position;
	}

	public String getScannerID() {
		return scanner;
	}

	public String getID() {
		return id;
	}

	public List<Subpod> getSubpods() {
		return subpods;
	}

}
