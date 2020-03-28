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
    @PersistenceContext
    EntityManager em;

    @Override
    public int addCategory(Category category) {
        // TODO Auto-generated method stub
         em.persist(category);
         return category.getIdCategory();
    }

    @Override
    public void removeCategory(int id) {
        // TODO Auto-generated method stub
         em.remove(em.find(Category.class, id));
            System.out.println("category deleted");
    }

    @Override
    public void updateCategory(Category c) {
        // TODO Auto-generated method stub
        System.out.println("gggggggggggggggggs");
        Category category = em.find(Category.class, c.getIdCategory());
        //coll.setPassword(connectionNewValues.getPassword());
        category.setNom(c.getNom());

    }

    @Override
    public Category findCategoryById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Category> findAllCategory() {
        // TODO Auto-generated method stub
        return null;
    }


}