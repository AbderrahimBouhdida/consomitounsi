package tn.esprit.consomitounsi.services.impl;


public class Eventbytype {
	
	private String type ;
	private long count;
	public Eventbytype(String type, int i) {
	}
	public Eventbytype(String type, long count) {
		super();
		this.type = type;
		this.count = count;
	}
	public String gettype() {
		return type;
	}
	public void setUsr(String type) {
		this.type = type;
	}
	public long getcount() {
		return count;
	}
	public void setcount(int count) {
		this.count = count;
	}
	
	
	
	
	
	

}
