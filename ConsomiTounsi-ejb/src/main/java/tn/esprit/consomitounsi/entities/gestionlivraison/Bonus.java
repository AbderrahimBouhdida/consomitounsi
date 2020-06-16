package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bonus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double amount;

	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	private DeliveryMan deliveryMan;

	public Bonus() {
		super();
	}
	
	

	public Bonus(double amount) {
		super();
		this.amount = amount;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DeliveryMan getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(DeliveryMan deliveryMan) {
		this.deliveryMan = deliveryMan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((deliveryMan == null) ? 0 : deliveryMan.hashCode());
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
		Bonus other = (Bonus) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} 
		else if (!date.equals(other.date))
			return false;
		if (deliveryMan == null) {
			if (other.deliveryMan != null)
				return false;
		} 
		else if (!deliveryMan.equals(other.deliveryMan))
			return false;
		return id==other.id;
	}

	@Override
	public String toString() {
		return "amount=" + amount + ", date=" + date + ", deliveryMan=" + deliveryMan.getFirstName() + " "
				+ deliveryMan.getLastName();
	}

}
