package tn.esprit.consomitounsi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Donation implements Serializable {
	private static final long serialVersionUID = 3152690779535828408L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddon;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Donationtype type;
	
	private String description;
	
	private String image;
	
	@ManyToOne
	private  User user;
	

	public Donation() {
		super();
	}



	public Donation(int iddon, String name, Donationtype type, String description, String image) {
		super();
		this.iddon = iddon;
		this.name = name;
		this.type = type;
		this.description = description;
		this.image = image;
	}



	public int getIddon() {
		return iddon;
	}



	public void setIddon(int iddon) {
		this.iddon = iddon;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Donationtype getType() {
		return type;
	}



	public void setType(Donationtype type) {
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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
	
	
	

}
