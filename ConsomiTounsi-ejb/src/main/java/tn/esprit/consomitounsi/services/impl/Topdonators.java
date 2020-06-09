package tn.esprit.consomitounsi.services.impl;

import tn.esprit.consomitounsi.entities.User;

public class Topdonators {
	
	private User usr ;
	private long idusr;
	public Topdonators(User user, int i) {
	}
	public Topdonators(User usr, long idusr) {
		super();
		this.usr = usr;
		this.idusr = idusr;
	}
	public User getUsr() {
		return usr;
	}
	public void setUsr(User usr) {
		this.usr = usr;
	}
	public long getIdusr() {
		return idusr;
	}
	public void setIdusr(int idusr) {
		this.idusr = idusr;
	}
	
	
	
	
	
	

}
