package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int barecode;
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



	public Product(int barecode, String nameProduct, String picture, int price, String description, Date dateExpire,
			Date dateAdd, double weight, int quantity, Category category) {
		super();
		this.barecode = barecode;
		this.nameProduct = nameProduct;
		this.picture = picture;
		this.price = price;
		
		this.dateExpire = dateExpire;
		this.dateAdd = dateAdd;
		this.weight = weight;
		this.quantity = quantity;
		this.category = category;
	}




	public Product(int barecode, String nameProduct, String picture, int price, String description, double weight,
			int quantity) {
		super();
		this.barecode = barecode;
		this.nameProduct = nameProduct;
		this.picture = picture;
		this.price = price;
		
		this.weight = weight;
		this.quantity = quantity;
	}




	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getBarecode() {
		return barecode;
	}

	public void setBarecode(int barecode) {
		this.barecode = barecode;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String toString() {
		return "Product [barecode=" + barecode + ", nameProduct=" + nameProduct + ", picture=" + picture + ", price="
				+ price + ", description=" + description + ", dateExpire=" + dateExpire + ", dateAdd=" + dateAdd
				+ ", weight=" + weight + ", quantity=" + quantity + ", category=" + category + "]";
	}

	
}
