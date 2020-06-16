package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Repair extends ReclamationTreatment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String description;

	private double cost;

	public Repair() {

	}

	public Repair(String description, double cost, Reclamation reclamation) {
		super(reclamation);
		this.description = description;
		this.cost = cost;
	}

	public Repair(int id, String description, double cost, Reclamation reclamation) {
		super(id, reclamation);
		this.description = description;
		this.cost = cost;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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
		temp = Double.doubleToLongBits(cost);
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
		Repair other = (Repair) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

}
