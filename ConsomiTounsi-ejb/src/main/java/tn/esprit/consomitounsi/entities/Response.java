package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int  idResponse;
	
	
	private String content;
	
	

	
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateResponse = new java.sql.Date(new java.util.Date().getTime());
	
	
	//@ManyToOne
	//private Subject subject;
	
	@ManyToOne
    private User  user;
	
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getIdResponse() {
		return idResponse;
	}
	public void setIdResponse(int idResponse) {
		this.idResponse = idResponse;
	}

	public Date getDateResponse() {
		return dateResponse;
	}
	public void setDateResponse(Date dateResponse) {
		this.dateResponse = dateResponse;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Response [idResponse=" + idResponse + ", content=" + content +
				 ", dateResponse=" + dateResponse + ", user=" + user + "]";
	}
	public Response() {}
	
	public Response(int idResponse, String content, Date dateResponse,User user) {
		this.idResponse = idResponse;
		this.content = content;
		this.dateResponse = dateResponse;
		this.user = user;
	}
	
	
	
	
	

}
