package tn.esprit.consomitounsi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MarkrResponse implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int idmarkresponse;
	
	@ManyToOne
	private User user;
	
	
	@ManyToOne
	private Response reponse;
	
	public int getIdmarkresponse() {
		return idmarkresponse;
	}
	public void setIdmarkresponse(int idmarkresponse) {
		this.idmarkresponse = idmarkresponse;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Response getReponse() {
		return reponse;
	}
	public void setReponse(Response reponse) {
		this.reponse = reponse;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	@Override
	public String toString() {
		return "MarkrResponse [idmarkresponse=" + idmarkresponse + ", user=" + user + ", reponse=" + reponse + "]";
	}
	
	public MarkrResponse() {}
	
	public MarkrResponse(int idmarkresponse, User user, Response reponse) {
		this.idmarkresponse = idmarkresponse;
		this.user = user;
		this.reponse = reponse;
	}
	
	
	
	
	
	
}
