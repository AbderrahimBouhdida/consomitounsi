package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event implements Serializable{
	private static final long serialVersionUID = 3152690779735828428L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idevent;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Eventtype type;
	
	private String description;
	
	private String image;
	
	private int participantsnumber;
	
	private Date date;
	
	@ManyToOne
	private  User user;
	
	

	public Event() {
		super();
	}


	public Event(int idevent, String name, Eventtype type, String description, String image, int participantsnumber,
			Date date) {
		super();
		this.idevent = idevent;
		this.name = name;
		this.type = type;
		this.description = description;
		this.image = image;
		this.participantsnumber = participantsnumber;
		this.date = date;
	}


	public int getIdevent() {
		return idevent;
	}


	public void setIdevent(int idevent) {
		this.idevent = idevent;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Eventtype getType() {
		return type;
	}


	public void setType(Eventtype type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public int getParticipantsnumber() {
		return participantsnumber;
	}


	public void setParticipantsnumber(int participantsnumber) {
		this.participantsnumber = participantsnumber;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
