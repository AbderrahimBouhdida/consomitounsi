package tn.esprit.consomitounsi.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CartProdPk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5868713300927753151L;
	private int cart;
	private	int prod;
	
	public CartProdPk() {
	}
	public CartProdPk(int cart, int prod) {
		super();
		this.cart = cart;
		this.prod = prod;
	}
	public int getCart() {
		return cart;
	}
	public void setCart(int cart) {
		this.cart = cart;
	}
	public int getProd() {
		return prod;
	}
	public void setProd(int prod) {
		this.prod = prod;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cart;
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
		CartProdPk other = (CartProdPk) obj;
		if (cart != other.cart)
			return false;
		return true;
	}
	
	
}
