package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Product;


@Remote
public interface ProductRemote {
	public int addProduct(Product product);
	public void removeProduct(int barecode);
	public void updateProduct(Product p);
	public Product findProductById(int barecode);
	public List<Product> findAllProduct();
	
}
