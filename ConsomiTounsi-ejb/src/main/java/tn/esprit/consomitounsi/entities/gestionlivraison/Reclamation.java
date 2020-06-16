package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.User;

@Entity
public class Reclamation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private String responseDescription;
	@Temporal(TemporalType.DATE)
	private Date created;
	@Temporal(TemporalType.DATE)
	private Date answered;
	@Enumerated(EnumType.STRING)
	private ReclamationState state;
	@Enumerated(EnumType.STRING)
	private Decision decision;

	@OneToOne
	private Product product;
	@ManyToOne
	private User user;

	public Reclamation() {

	}

	public Reclamation(String description, Product product, User user) {
		this.description = description;
		this.product = product;
		this.user = user;
	}

	public Reclamation(int id, String responseDescription, ReclamationState state, Decision decision) {
		super();
		this.id = id;
		this.responseDescription = responseDescription;
		this.state = state;
		this.decision = decision;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getAnswered() {
		return answered;
	}

	public void setAnswered(Date answered) {
		this.answered = answered;
	}

	public ReclamationState getState() {
		return state;
	}

	public void setState(ReclamationState state) {
		this.state = state;
	}

	public Decision getDecision() {
		return decision;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		Reclamation other = (Reclamation) obj;

		return id == other.id;
	}

	@Override
	public String toString() {
		return "id=" + id + ", description=" + description + ", responseDescription=" + responseDescription
				+ ", created=" + created + ", answered=" + answered + ", state=" + state + ", decision=" + decision
				+ ", product=" + product.getNameProduct() + ", user=" + user.getFirstName() + " " + user.getLastName()
				+ "]";
	}

}
