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
	public void removeProduct(int Barecode) {
		// TODO Auto-generated method stub
		 em.remove(em.find(Product.class, Barecode));
	     System.out.println("product deleted");
	}

	@Override
	public void updateProduct(Product productNewValues) {
		// TODO Auto-generated method stub
		Product product = em.find(Product.class, productNewValues.getBarecode());
		product.setNameProduct(productNewValues.getNameProduct());
		product.setDateAdd(productNewValues.getDateAdd());
		product.setDateExpire(productNewValues.getDateExpire());
		product.setDecription(productNewValues.getDecription());
		product.setPrice(productNewValues.getPrice());
		product.setQuantity(productNewValues.getQuantity());
		product.setPicture(productNewValues.getPicture());
	}

	@Override
	public Product findProductById(int Barecode) {
		// TODO Auto-generated method stub
		Product product = em.find(Product.class, Barecode);
        return product;
	}

	@Override
	public List<Product> findAllProduct() {
		// TODO Auto-generated method stub
		List<Product> product = em.createQuery("Select p from Product p", Product.class).getResultList();
        return product;
	}
	
}
