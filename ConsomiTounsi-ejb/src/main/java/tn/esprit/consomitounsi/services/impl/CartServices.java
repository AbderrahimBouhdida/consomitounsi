package tn.esprit.consomitounsi.services.impl;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.DuplicateKeyException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sound.midi.Soundbank;
import javax.validation.ConstraintViolationException;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.jdbc.JdbcPropertySetImpl;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.CartProdPk;
import tn.esprit.consomitounsi.entities.CartProduct;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;
import tn.esprit.consomitounsi.services.impl.ProductService;


@Stateless
@LocalBean
public class CartServices implements ICartServicesRemote{
	@PersistenceContext
	EntityManager em;
	ProductService ps;
	
	@Override
	public int addCart(Cart cart) {
		em.persist(cart);
		return cart.getIdCart();
	}

	@Override
	public void removeCart(int id) {
		em.remove(em.find(Cart.class, id));
		System.out.println("Cart deleted");
		
	}

	@Override
	public void updateCart(Cart cartNewValues) {
		Cart cart = em.find(Cart.class, cartNewValues.getIdCart());
		cart.setProducts(cartNewValues.getProducts());
	}

	@Override
	public List<Cart> findCartByUserId(int id) {
		TypedQuery<Cart> query = em.createQuery("select c from cart c where user_IdUser=:user ", Cart.class);
		query.setParameter("user", id);
		List<Cart> carts = new ArrayList<Cart>();
		try { carts = query.getResultList(); }
		catch (Exception e) { System.out.println("Erreur : " + e); }
		return carts;
	}
	
	@Override
	public List<Cart> findAllCarts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCartAvailaible(User user) {
		TypedQuery<Cart> query = em.createQuery("select c from Cart c where user=:user and isCurrent=:curr", Cart.class);
		query.setParameter("user", user);
		query.setParameter("curr", true);
		List<Cart> cr = new ArrayList<Cart>();
		try {
			cr = query.getResultList();
			System.out.println(cr.size());
			return (cr.size()>0) ? true:false;
			}catch (Exception e) {
				return false;
			}
	}


	@Override
	public Cart findActiveCartByUserId(User user) {
		TypedQuery<Cart> query = em.createQuery("select c from Cart c where user=:user and isCurrent=:curr", Cart.class);
		query.setParameter("user", user);
		query.setParameter("curr", true);
		Cart cr = null;
		try {
			System.out.println("geeeeeeeeeeeeeeeeeeeeez");
			cr = query.getSingleResult();
			System.out.println(cr.getIdCart());
				return cr;
		
		}catch (Exception e) {
			System.out.println("Error "+e);
			return null;
		}
	}

	@Override
	public boolean addProdCart(User user, CartProduct prod) {
		Cart cr = findActiveCartByUserId(user);
		CartProdPk pk = new CartProdPk();
		pk.setCart(cr.getIdCart());
		pk.setProd(prod.getCartProdPk().getProd());
		Product ppp = em.find(Product.class, prod.getCartProdPk().getProd());
		CartProduct cp = new CartProduct();
		cp.setCartProdPk(pk);
		cp.setQuantity(120);
		if(!prodExist(cr, ppp)) {
			em.persist(cp);
			return true;
		}
		System.out.println("exists");
		return false;
	}
	
	@Override
	public boolean removeProd(User user,CartProduct prod) {
		Cart cr = findActiveCartByUserId(user);
		Product ppp = em.find(Product.class, prod.getCartProdPk().getProd());
		if(prodExist(cr, ppp)) {
			CartProdPk cpk = new CartProdPk();
			cpk.setCart(cr.getIdCart());
			cpk.setProd(prod.getCartProdPk().getProd());
			em.remove(em.find(CartProduct.class, cpk));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean modProd(User user,CartProduct prod) {
		Cart cr = findActiveCartByUserId(user);
		Product ppp = em.find(Product.class, prod.getCartProdPk().getProd());
		if(prodExist(cr, ppp)) {
			CartProdPk cpk = new CartProdPk();
			cpk.setCart(cr.getIdCart());
			cpk.setProd(prod.getCartProdPk().getProd());
			CartProduct cp = em.find(CartProduct.class, cpk);
			cp.setQuantity(prod.getQuantity());;
			return true;
		}
		return false;
	}
	
	@Override
	public List<Product> getCurrUserProds(User user) {
		/*Cart cr = new Cart();
		cr = findActiveCartByUserId(user);
		List<Product> prods = new ArrayList<Product>();
		System.out.println("zzzzzzzzzzzzzzzzzz"+cr.getIdCart());
		prods = cr.getProducts();
		System.out.println("zzzzzzzzzzzzzzzzzz"+cr.getProducts().size());
		//List<CartItem> its = cr.getItems();
		return prods;*/
		return null;
	}
	
	public boolean prodExist(Cart cr, Product pr) {
		Product prod=em.find(Product.class, pr.getBarecode());
		List<CartProduct> c = cr.getProducts();
		List<CartProdPk> pk = c.stream().map(x -> x.getCartProdPk()).collect(Collectors.toList());
		List<Product> p = pk.stream().map(x -> em.find(Product.class, x.getProd())).collect(Collectors.toList());
		if(p.contains(prod))
			return true;
		return false;
	}
}
