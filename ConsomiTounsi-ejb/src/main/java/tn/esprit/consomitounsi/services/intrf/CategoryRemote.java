package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Category;


@Remote
public interface CategoryRemote {
    public int addCategory(Category category);
    public void removeCategory(int id);
    public void updateCategory(Category category);
    public Category findCategoryById(int id);
    public List<Category> findAllCategory();
}