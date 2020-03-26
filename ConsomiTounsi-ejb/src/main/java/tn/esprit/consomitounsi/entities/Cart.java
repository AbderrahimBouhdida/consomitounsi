package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CartItem> items;
	private int totalQty;
	private float totalPrice;
	private boolean isCurrent; 
	
	public Cart() {
	}
	
	
	
	public Cart(User user, boolean isCurrent) {
		this.user = user;
		this.isCurrent = isCurrent;
	}



	public Cart(User user, List<CartItem> items, int totalQty, float totalPrice, boolean isCurrent) {
		this.user = user;
		this.items = items;
		this.totalQty = totalQty;
		this.totalPrice = totalPrice;
		this.isCurrent = isCurrent;
	}



	public Cart(int idCart, User user, List<CartItem> items, int totalQty, float totalPrice, boolean isCurrent) {
		this.idCart = idCart;
		this.user = user;
		this.items = items;
		this.totalQty = totalQty;
		this.totalPrice = totalPrice;
		this.isCurrent = isCurrent;
	}



	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
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



	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
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
	
	
}
