package tn.esprit.manaegedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.services.impl.CategoryService;




@ManagedBean(name = "categoryBean")
@SessionScoped
public class CategoryBean implements Serializable  {
	private static final long serialVersionUID = 1L;
	private int idCategory;
    private String nom;
    private String description;
    private String type;
    private String stock;
    
    List <Category> cat = new ArrayList<Category>();
    
   private Integer categoryidCategoryToBeUpdated; 
	@EJB
	CategoryService categoryService;
	
	public Integer getCategoryidCategoryToBeUpdated() {
		return categoryidCategoryToBeUpdated;
	}
	
	public void setCategoryidCategoryToBeUpdated(Integer productBaracodeToBeUpdated) {
		this.categoryidCategoryToBeUpdated = productBaracodeToBeUpdated;
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
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public List<Category> getCat() {
		return categoryService.findAllCategory();
	}

	public void setCat(List<Category> cat) {
		this.cat = cat;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void addCategory() {
		Category category = new Category();
		category.setIdCategory(idCategory);
		category.setNom(nom);
		category.setDescription(description);
		category.setStock(stock);
		category.setType(type);
		categoryService.addCategory(category);
	}
	public String removeCategory(int idCategory) {
		System.out.println(idCategory);
		categoryService.removeCategory(idCategory);
		return "";
	}
	
	public void updateCategory() {
		Category cat = new Category();
		cat.setIdCategory(idCategory);
		cat.setDescription(description);
		cat.setNom(nom);
		cat.setStock(stock);
		cat.setType(type);
		System.out.println("test "+stock);
		categoryService.updateCategory(cat);
	}
	
	 public Category findCategoryById(int idCategory) {
			categoryService.findAllCategory();
			return null;
		 }
}
