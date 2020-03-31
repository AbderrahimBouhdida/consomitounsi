package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String surname;

	/* ex : Rue, appartement, bloc, batiment */
	private String adresseDetail;

	/* ex : ariana */
	private String region;

	/* ex: raoued */
	private String city;

	private String number;

	public Address() {
	}

	public Address(String name, String surname, String adresseDetail, String region, String city, String number) {
		super();
		this.name = name;
		this.surname = surname;
		this.adresseDetail = adresseDetail;
		this.region = region;
		this.city = city;
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAdresseDetail() {
		return adresseDetail;
	}

	public void setAdresseDetail(String adresseDetail) {
		this.adresseDetail = adresseDetail;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresseDetail == null) ? 0 : adresseDetail.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Address other = (Address) obj;
		if (adresseDetail == null) {
			if (other.adresseDetail != null)
				return false;
		} else if (!adresseDetail.equals(other.adresseDetail))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "name=" + name + ", surname=" + surname + ", adresseDetail=" + adresseDetail + ", region="
				+ region + ", city=" + city + ", number=" + number;
	}

}
