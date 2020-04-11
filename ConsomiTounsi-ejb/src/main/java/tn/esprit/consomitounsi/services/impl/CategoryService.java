package tn.esprit.consomitounsi.services.impl;



import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.services.intrf.CategoryRemote;


@Stateless
@LocalBean
public class CategoryService implements CategoryRemote{
	private static final Object IdCategory = null;
	@PersistenceContext
	EntityManager em;

	@Override
	public int addCategory(Category category) {
		// TODO Auto-generated method stub
		em.persist(category);
        return category.getIdCategory();
	}

	@Override
	public void removeCategory(int idCategory) {
		// TODO Auto-generated method stub
		 em.remove(em.find(Category.class, idCategory));
	     System.out.println("category deleted");
	}

	@Override
	public void updateCategory(Category categoryNewValues) {
		// TODO Auto-generated method stub
		Category category = em.find(Category.class, categoryNewValues.getIdCategory());
		category.setNom(categoryNewValues.getNom());
		category.setIdCategory(categoryNewValues.getIdCategory());

		

		category.setDescription(categoryNewValues.getDescription());
		category.setType(categoryNewValues.getType());
		
	}

	@Override
	public Category findCategoryById(int id) {
		// TODO Auto-generated method stub
		Category category = em.find(Category.class, IdCategory);
        return category;
	}

	@Override
	public List<Category> findAllCategory() {
		// TODO Auto-generated method stub
		List<Category> category = em.createQuery("Select c from Category c", Category.class).getResultList();
        return category;
	}

}
