package tn.esprit.consomitounsi.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Category  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCategory;
    private String nom;
    private String description;
    private String type;
    private String Stock;
    
    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    List <Product> prod = new ArrayList<Product>();

    
    
	public Category() {
		super();
	}


	public Category(int idCategory, String nom, String description, String type) {
		super();
		this.idCategory = idCategory;
		this.nom = nom;
		this.description = description;
		this.type = type;
	}


	public int getIdCategory() {
		return idCategory;
	}


	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStock() {
		return Stock;
	}


	public void setStock(String stock) {
		Stock = stock;
	}


	public Category(int idCategory, String nom, String description, String type, String stock) {
		super();
		this.idCategory = idCategory;
		this.nom = nom;
		this.description = description;
		this.type = type;
		Stock = stock;
	}
	
    
	
    
}