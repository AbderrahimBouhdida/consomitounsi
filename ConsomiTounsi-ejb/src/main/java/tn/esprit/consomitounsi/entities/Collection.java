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
public class Collection implements Serializable{
	private static final long serialVersionUID = 3152690779735828408L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcollection;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Collectiontype type;
	
	private String description;
	
	private String image;
	
	private int goal;
	
	private int collectedamount;
	
	@ManyToOne
	private  User user;
	
	
	
	
	public Collection() {
		super();
	}

	public Collection(int idcollection, String name, Collectiontype type, String description, String image, int goal,
			int collectedamount) {
		super();
		this.idcollection = idcollection;
		this.name = name;
		this.type = type;
		this.description = description;
		this.image = image;
		this.goal = goal;
		this.collectedamount = collectedamount;
	}

	public int getIdcollection() {
		return idcollection;
	}

	public void setIdcollection(int idcollection) {
		this.idcollection = idcollection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collectiontype getType() {
		return type;
	}

	public void setType(Collectiontype type) {
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

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getCollectedamount() {
		return collectedamount;
	}

	public void setCollectedamount(int collectedamount) {
		this.collectedamount = collectedamount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	
	
	

}
