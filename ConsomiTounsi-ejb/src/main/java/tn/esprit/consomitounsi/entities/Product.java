package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int barecode;
	private String nameProduct;
	private String picture;
	private int price;
	private String decription;
	private Date dateExpire;
	private Date dateAdd;
	private int quantity;
	private double weight;

	@ManyToOne
	private Category category;

	public Product() {
		super();
	}

	public Product(int Barecode, String nameProduct, String picture, int price, String decription, Date dateExpire,
			Date dateAdd, int quantity, double weight) {
		super();
		this.barecode = Barecode;
		this.nameProduct = nameProduct;
		this.picture = picture;
		this.price = price;
		this.decription = decription;
		this.dateExpire = dateExpire;
		this.dateAdd = dateAdd;
		this.quantity = quantity;
		this.weight = weight;
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

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + barecode;
		result = prime * result + ((dateAdd == null) ? 0 : dateAdd.hashCode());
		result = prime * result + ((dateExpire == null) ? 0 : dateExpire.hashCode());
		result = prime * result + ((decription == null) ? 0 : decription.hashCode());
		result = prime * result + ((nameProduct == null) ? 0 : nameProduct.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result + price;
		result = prime * result + quantity;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Product other = (Product) obj;
		if (barecode != other.barecode)
			return false;
		if (dateAdd == null) {
			if (other.dateAdd != null)
				return false;
		} else if (!dateAdd.equals(other.dateAdd))
			return false;
		if (dateExpire == null) {
			if (other.dateExpire != null)
				return false;
		} else if (!dateExpire.equals(other.dateExpire))
			return false;
		if (decription == null) {
			if (other.decription != null)
				return false;
		} else if (!decription.equals(other.decription))
			return false;
		if (nameProduct == null) {
			if (other.nameProduct != null)
				return false;
		} else if (!nameProduct.equals(other.nameProduct))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (price != other.price)
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Barecode=" + barecode + ", nameProduct=" + nameProduct + ", picture=" + picture + ", price=" + price
				+ ", decription=" + decription + ", dateExpire=" + dateExpire + ", dateAdd=" + dateAdd + ", quantity="
				+ quantity + ", weight=" + weight + ", category=" + category.getNom();
	}

}