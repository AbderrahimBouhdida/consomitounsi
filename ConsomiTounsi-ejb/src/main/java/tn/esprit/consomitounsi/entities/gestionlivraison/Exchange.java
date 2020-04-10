package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tn.esprit.consomitounsi.entities.Product;

@Entity
public class Exchange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String code;
	@ManyToOne
	private Product product;
	private boolean done;
	@Temporal(TemporalType.DATE)
	private Date created;
	@Temporal(TemporalType.DATE)
	private Date doneOn;

	public Exchange() {

	}

	public Exchange(Product product, String code, Date created) {
		super();
		this.product = product;
		this.code = code;
		this.created = created;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDoneOn() {
		return doneOn;
	}

	public void setDoneOn(Date doneOn) {
		this.doneOn = doneOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		Exchange other = (Exchange) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} 
		else if (!code.equals(other.code))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} 
		else if (!created.equals(other.created))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} 
		else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "code=" + code + ", product=" + product.getNameProduct() + ", done=" + done + ", created=" + created
				+ ", doneOn=" + doneOn;
	}

	

}
