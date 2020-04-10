package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import tn.esprit.consomitounsi.entities.Product;



public interface ProductRemote {
	public int addProduct(Product product);
    public void removeProduct(int Barecode);
    public void updateProduct(Product productNewValues);
    public Product findProductById(int Barecode);
    public List<Product> findAllProduct();
	
}
