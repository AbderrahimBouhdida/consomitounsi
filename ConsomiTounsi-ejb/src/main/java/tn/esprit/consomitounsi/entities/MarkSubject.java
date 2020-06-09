package tn.esprit.consomitounsi.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MarkSubject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idmarksubject;
	
	@ManyToOne
	private User user;
	
	
	@ManyToOne
	private Subject subject;

	private int updown;

	
	public int getIdmarksubject() {
		return idmarksubject;
	}

	public void setIdmarksubject(int idmarksubject) {
		this.idmarksubject = idmarksubject;
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

	public int getUpdown() {
		return updown;
	}

	public void setUpdown(int updown) {
		this.updown = updown;
	}

	@Override
	public String toString() {
		return "MarkSubject [idmarksubject=" + idmarksubject + ", user=" + user
				+ ", subject=" + subject + ", updown=" + updown + "]";
	}

	
	public MarkSubject() {
		super();
		}
	
	
	public MarkSubject(int idmarksubject, User user, Subject subject, int updown) {
		super();
		this.idmarksubject = idmarksubject;
		this.user = user;
		this.subject = subject;
		this.updown = updown;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idmarksubject;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + updown;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkSubject other = (MarkSubject) obj;
		if (idmarksubject != other.idmarksubject)
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (updown != other.updown)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	
	
	
	
	
	
	
	
}
