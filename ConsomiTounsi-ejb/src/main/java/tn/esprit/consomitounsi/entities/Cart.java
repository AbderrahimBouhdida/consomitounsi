package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Cart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1308884803640646306L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idCart;
	@ManyToOne
	private User user;
	//@ManyToMany(fetch = FetchType.EAGER)
	//private List<Product> products;
	private int totalQty;
	private float totalPrice;
	private boolean isCurrent; 
	@Temporal(TemporalType.DATE)
	private Date created;
	
	@OneToMany(mappedBy = "cart")
	private List<CartProduct> products;
	public Cart() {
	}
	
	
	
	public Cart(User user, boolean isCurrent) {
		this.user = user;
		this.isCurrent = isCurrent;
	}



	public Cart(User user, int totalQty, float totalPrice, boolean isCurrent) {
		this.user = user;
		//this.products = products;
		this.totalQty = totalQty;
		this.totalPrice = totalPrice;
		this.isCurrent = isCurrent;
	}



	public Cart(int idCart, User user, int totalQty, float totalPrice, boolean isCurrent) {
		this.idCart = idCart;
		this.user = user;
		//this.products = products;
		this.totalQty = totalQty;
		this.totalPrice = totalPrice;
		this.isCurrent = isCurrent;
	}

	

	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
	}



	public int getIdCart() {
		return idCart;
	}



	public void setIdCart(int idCart) {
		this.idCart = idCart;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public List<CartProduct> getProducts() {
		return products;
	}



	public void setProducts(List<CartProduct> products) {
		this.products = products;
	}



	public int getTotalQty() {
		return totalQty;
	}



	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}



	public float getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}



	public boolean isCurrent() {
		return isCurrent;
	}



	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCart;
		result = prime * result + (isCurrent ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(totalPrice);
		result = prime * result + totalQty;
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
		Cart other = (Cart) obj;
		if (idCart != other.idCart)
			return false;
		if (isCurrent != other.isCurrent)
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (Float.floatToIntBits(totalPrice) != Float.floatToIntBits(other.totalPrice))
			return false;
		if (totalQty != other.totalQty)
			return false;
		return true;
	}




	
}
