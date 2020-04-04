package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CartProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private CartProdPk cartProdPk;
	@ManyToOne
	@JoinColumn(name = "cart", referencedColumnName = "idCart", insertable = false, updatable = false)
	private Cart cart;

	// idEmploye est a la fois primary key et foreign key
	@ManyToOne
	@JoinColumn(name = "prod", referencedColumnName = "barecode", insertable = false, updatable = false)
	private Product product;
	@Temporal(TemporalType.DATE)
	private Date added;
	private int quantity;

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public CartProdPk getCartProdPk() {
		return cartProdPk;
	}

	public void setCartProdPk(CartProdPk cartProdPk) {
		this.cartProdPk = cartProdPk;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

}
