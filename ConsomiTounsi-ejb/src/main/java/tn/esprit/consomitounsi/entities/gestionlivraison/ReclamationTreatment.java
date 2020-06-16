package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ReclamationTreatment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date created;
	@OneToOne
	private Reclamation reclamation;

	public ReclamationTreatment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReclamationTreatment(int id) {
		super();
		this.id = id;

	}

	public ReclamationTreatment(Reclamation reclamation) {
		super();
		this.reclamation = reclamation;
	}

	public ReclamationTreatment(int id, Reclamation reclamation) {
		super();
		this.id = id;
		this.reclamation = reclamation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Reclamation getReclamation() {
		return reclamation;
	}

	public void setReclamation(Reclamation reclamation) {
		this.reclamation = reclamation;
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
		ReclamationTreatment other = (ReclamationTreatment) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
