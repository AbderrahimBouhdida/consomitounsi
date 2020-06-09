package tn.esprit.consomitounsi.entities;

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
public class Bill implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8247935569312149789L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBill;
	@OneToOne
	private Cart cart;
	@Temporal(TemporalType.TIMESTAMP)
	private Date billDate;
	@OneToOne
	private Adress shipping;
	@OneToOne
	private Adress billing;
	
	
	public int getIdBill() {
		return idBill;
	}
	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Adress getShipping() {
		return shipping;
	}
	public void setShipping(Adress shipping) {
		this.shipping = shipping;
	}
	public Adress getBilling() {
		return billing;
	}
	public void setBilling(Adress billing) {
		this.billing = billing;
	}
	
	
}
