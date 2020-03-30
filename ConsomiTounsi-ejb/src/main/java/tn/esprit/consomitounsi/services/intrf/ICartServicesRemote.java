package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.CartProduct;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.User;


@Remote
public interface ICartServicesRemote {
	public int addCart(Cart cart);
	public void removeCart(int id);
	public void updateCart(Cart cart);
	public List<Cart> findCartByUserId(int id);
	public List<Cart> findAllCarts();
	public boolean isCartAvailaible(User user);
	public boolean addProdCart(User user,CartProduct prod);
	public Cart findActiveCartByUserId(User user);
	public List<Product> getCurrUserProds(User user);
	public boolean removeProd(User user,CartProduct prod);
	public boolean modProd(User user,CartProduct prod);
}
