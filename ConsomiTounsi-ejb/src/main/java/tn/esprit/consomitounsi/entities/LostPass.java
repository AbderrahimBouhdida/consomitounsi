package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class LostPass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2327502006655025588L;
	@Id
	@OneToOne
	private User user;
	private String token;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date timestamp = new java.sql.Date(new java.util.Date().getTime());
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
