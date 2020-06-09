package tn.esprit.consomitounsi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Spam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idspam;
	
	@ManyToOne
	private User user;
	
	
	@ManyToOne
	private Subject subject;
	
	public Spam() {
	}
	
	
	public Spam(int idspam, User user, Subject subject) {
		super();
		this.idspam = idspam;
		this.user = user;
		this.subject = subject;
	}



	@Override
	public String toString() {
		return "Spam [idspam=" + idspam + ", user=" + user + ", subject=" + subject + "]";
	}


	public int getIdspam() {
		return idspam;
	}


	public void setIdspam(int idspam) {
		this.idspam = idspam;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}



	
	
	
	

}
