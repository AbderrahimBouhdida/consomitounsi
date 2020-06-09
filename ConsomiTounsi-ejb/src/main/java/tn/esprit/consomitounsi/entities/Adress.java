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
public class Adress implements Serializable{


	private static final long serialVersionUID = -4041654978767030333L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String companyName;
	private String country;
	private String street;
	private String appartement;
	private String city;
	@Enumerated(EnumType.STRING)
	private States state;
	private Integer zipCode;
	private String phone;
	private boolean defAdress;
	
	@ManyToOne
	private User user;
	
	public Adress() {
	}

	public Adress(String firstName, String lastName, String country, String street, String city, States state,
			Integer zipCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAppartement() {
		return appartement;
	}

	public void setAppartement(String appartement) {
		this.appartement = appartement;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isDefAdress() {
		return defAdress;
	}

	public void setDefAdress(boolean defAdress) {
		this.defAdress = defAdress;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    Adress other = (Adress) obj;
	    if(id == other.getId()) {
	    	return true;
	    }
	    return false;
		
	}
	
	
	
	
}
