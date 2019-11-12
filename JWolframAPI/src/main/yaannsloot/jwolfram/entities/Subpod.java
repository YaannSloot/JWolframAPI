package main.yaannsloot.jwolfram.entities;

public class Subpod {
	
	private String title;
	private String plaintext;
	private String imageurl;
	
	public Subpod(String title, String plaintext, String imageurl) {
		this.title = title;
		this.plaintext = plaintext;
		this.imageurl = imageurl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPlaintext() {
		return plaintext;
	}
	
	public String getImageUrl() {
		return imageurl;
	}
	
}
