package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import tn.esprit.consomitounsi.entities.User;

@Entity
public class DeliveryMan extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String transportation;

	@OneToMany(mappedBy = "deliveryMan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Bonus> bonus = new HashSet<>();

	@Column
	private String availabilities;

	@OneToMany(mappedBy = "deliveryMan", fetch = FetchType.EAGER)
	private Set<Delivery> deliveries = new HashSet<>();


	private String base;

	public DeliveryMan() {
		super();

	}

	public DeliveryMan(int role, String firstName, String lastName, String email, String username, String password,
			Date dob, String address, String phone, String img) {
		super(role, firstName, lastName, email, username, password, dob, address, phone, img);
		this.setRole(2);
	}

	public DeliveryMan(String transportation) {
		super();
		this.transportation = transportation;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Set<Bonus> getBonus() {
		return bonus;
	}

	public void setBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}



	public String getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(String availabilities) {
		this.availabilities = availabilities;
	}

	public Set<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Set<Delivery> deliveries) {
		this.deliveries = deliveries;
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
		} 
		else if (!availabilities.equals(other.availabilities))
			return false;
		if (base == null) {
			if (other.base != null)
				return false;
		} 
		else if (!base.equals(other.base))
			return false;
		if (transportation == null) {
			if (other.transportation != null)
				return false;
		} 
		else if (!transportation.equals(other.transportation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeliveryMan [transportation=" + transportation + ", bonus=" + bonus + ", availabilities="
				+ availabilities + ", deliveries=" + deliveries + ", base=" + base + "]";
	}

}
