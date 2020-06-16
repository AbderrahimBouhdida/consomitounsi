package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.User;

@Entity
public class DeliveryMan extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String transportation;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
	private Set<String> availabilities = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
	private Set<String> base = new HashSet<>();

	public DeliveryMan() {
		super();
		this.setRole(Roles.DELEVERYMAN);

	}

	public DeliveryMan(int id, String firstName, String lastName, String email, String username, String password,
			String address, String phone, String transportation, Set<String> availabilities, Set<String> base,
			String img) {

		this.setFirstName(firstName);
		this.setIdUser(id);
		this.setAddress(address);
		this.setEmail(email);
		this.setImg(img);
		this.setLastName(lastName);
		this.setPhone(phone);
		this.setUsername(username);
		this.setPassword(password);
		this.availabilities = availabilities;
		this.base = base;
		this.transportation = transportation;
		this.setRole(Roles.DELEVERYMAN);

	}

	public DeliveryMan(String firstName, String lastName, String email, String username, String password,
			String address, String phone, String transportation, Set<String> availabilities, Set<String> base,
			String img) {

		this.setFirstName(firstName);
		this.setAddress(address);
		this.setEmail(email);
		this.setImg(img);
		this.setLastName(lastName);
		this.setPhone(phone);
		this.setUsername(username);
		this.setPassword(password);
		this.availabilities = availabilities;
		this.base = base;
		this.transportation = transportation;
		this.setRole(Roles.DELEVERYMAN);

	}

	public DeliveryMan(int id, String img) {
		this.setIdUser(id);
		this.setImg(img);
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public Set<String> getBase() {
		return base;
	}

	public void setBase(Set<String> base) {
		this.base = base;
	}

	public Set<String> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(Set<String> availabilities) {
		this.availabilities = availabilities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((availabilities == null) ? 0 : availabilities.hashCode());
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + ((transportation == null) ? 0 : transportation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryMan other = (DeliveryMan) obj;
		if (availabilities == null) {
			if (other.availabilities != null)
				return false;
		} else if (!availabilities.equals(other.availabilities))
			return false;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (transportation == null) {
			if (other.transportation != null)
				return false;
		} else if (!transportation.equals(other.transportation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeliveryMan [transportation=" + transportation + ", availabilities=" + availabilities + ", base=" + base
				+ "]";
	}

}
