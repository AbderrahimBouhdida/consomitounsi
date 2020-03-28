package tn.esprit.consomitounsi.services.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.services.intrf.ProductRemote;



@Stateless
@LocalBean
public class ProductService implements ProductRemote {
    @PersistenceContext
    EntityManager em;

    @Override
    public int addProduct(Product product) {
        // TODO Auto-generated method stub
         em.persist(product);
         return product.getBarecode();
    }

    @Override
    public void removeProduct(int barecode) {

        em.remove(em.find(Product.class, barecode));
        System.out.println("product deleted");
    }

    @Override
    public void updateProduct(Product p) {
        System.out.println("gggggggggggggggggs");
        Product product = em.find(Product.class, p.getBarecode());
        //coll.setPassword(connectionNewValues.getPassword());
        product.setNameProduct(p.getNameProduct());

    }

    @Override
    public Product findProductById(int barecode) {
        // TODO Auto-generated method stub
        Product prod = em.find(Product.class, barecode);
        return prod;
    }

    @Override
    public List<Product> findAllProduct() {
        // TODO Auto-generated method stub
        List<Product> prod = em.createQuery("from Product", Product.class).getResultList();
        return prod;
    }


}