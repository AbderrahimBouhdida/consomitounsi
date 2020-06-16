package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Repayment extends ReclamationTreatment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double amount;

	private String description;

	public Repayment() {

	}

	public Repayment(double amount, String description, Reclamation rec) {
		super(rec);
		this.amount = amount;
		this.description = description;

	}

	public Repayment(int id, double amount, String description,  Reclamation rec) {
		super(id, rec);

		this.amount = amount;
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Repayment other = (Repayment) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

}
