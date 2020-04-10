package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Category;


@Remote
public interface CategoryRemote {
	
	public int addCategory(Category category);
    public void removeCategory(int idCategory);
    public void updateCategory(Category categoryNewValues);
    public Category findCategoryById(int idCategory);
    public List<Category> findAllCategory();
}