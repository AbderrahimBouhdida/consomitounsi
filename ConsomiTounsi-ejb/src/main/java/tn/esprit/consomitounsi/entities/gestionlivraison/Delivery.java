package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import tn.esprit.consomitounsi.entities.Bill;

@Entity
public class Delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private DeliveryState deliveryState;

	@OneToOne
	private Bill bill;

	@Temporal(TemporalType.DATE)
	private Date deliveryDate;

	@Temporal(TemporalType.DATE)
	private Date toDo;

	@Transient
	private String description;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
	private Set<String> items = new HashSet<>();

	@ManyToOne
	private DeliveryMan deliveryMan;

	private Date deliveredDate;

	private float cost;

	public Delivery() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public DeliveryMan getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(DeliveryMan deliveryMan) {
		this.deliveryMan = deliveryMan;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public DeliveryState getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(DeliveryState deliveryState) {
		this.deliveryState = deliveryState;
	}

	public Set<String> getItems() {
		return items;
	}

	public void setItems(Set<String> items) {
		this.items = items;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getToDo() {
		return toDo;
	}

	public void setToDo(Date toDo) {
		this.toDo = toDo;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Delivery other = (Delivery) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + id + ", deliveryState=" + deliveryState + ", address=" + bill.getShipping()
				+ ", deliveryDate=" + deliveryDate + ", toDo=" + toDo + ", description=" + description + ", items="
				+ items + ", deliveryMan=" + deliveryMan + ", deliveredDate=" + deliveredDate + ", cost=" + cost + "]";
	}

}
