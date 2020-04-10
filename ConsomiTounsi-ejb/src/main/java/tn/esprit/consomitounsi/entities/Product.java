package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Product implements Serializable{
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Barecode;
	private String nameProduct;
	private String picture;
	private int price;
	private String description;
	private Date dateExpire;
	private Date dateAdd;
	private double weight;
	private int quantity;
	 @ManyToOne
	private Category category;
	public Product() {
		super();
	}

	
	public Product(int barecode, String nameProduct, String picture, int price, String decription, Date dateExpire,
			Date dateAdd, int quantity) {
		super();
		Barecode = barecode;
		this.nameProduct = nameProduct;
		this.picture = picture;
		this.price = price;
		this.description = decription;
		this.dateExpire = dateExpire;
		this.dateAdd = dateAdd;
		this.quantity = quantity;
	}

	public int getBarecode() {
		return Barecode;
	}

	public void setBarecode(int barecode) {
		Barecode = barecode;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDecription() {
		return description;
	}

	public void setDecription(String decription) {
		this.description = decription;
	}

	public Date getDateExpire() {
		return dateExpire;
	}

	public void setDateExpire(Date dateExpire) {
		this.dateExpire = dateExpire;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	
	
	
}
