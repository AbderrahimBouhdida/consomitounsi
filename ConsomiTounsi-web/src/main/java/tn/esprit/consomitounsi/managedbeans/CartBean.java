package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.CartProdPk;
import tn.esprit.consomitounsi.entities.CartProduct;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.CartServices;
import tn.esprit.consomitounsi.services.impl.ProductService;



@ManagedBean
@SessionScoped
public class CartBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -591983136804924635L;

	private Cart guestCart = new Cart();
	private Cart curCart = new Cart();
	private int count = 0;
	private User curUser;
	private boolean state = true;
	private List<CartProduct> items;
	private float subtotal = 0f;
	private float shipping = 5f;
	private float tax = 0f;
	private float total = 0f;
	private List<Integer> qty;
	private Integer test;
	boolean ca = false;

	@EJB
	CartServices carts;
	@EJB
	ProductService prods;
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;
	
	@PostConstruct
	public void init() {
		qty = new ArrayList<Integer>();
	}

	public List<CartProduct> getItems() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		LoginBean user = application.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);

		Optional.ofNullable(carts.findActiveCartByUserId(user.getuser())).ifPresent(c -> {
			guestCart = c;
		});
		this.items = guestCart.getProducts();
		if (items != null) {
			qty = this.items.stream().map(c -> c.getQuantity()).collect(Collectors.toList());
		}
		return this.items;
	}

	public void setItems(List<CartProduct> items) {
		this.items = items;
	}

	public int getCount() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		LoginBean user = application.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
		Optional.ofNullable(carts.findActiveCartByUserId(user.getuser()))
				.ifPresent(c -> count = c.getProducts().size());

		return count;
	}

	
	public void setCount(int count) {
		this.count = count;
	}

	public float getSubtotal() {
		subtotal = guestCart.getTotalPrice();
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		LoginBean user = application.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
		Optional.ofNullable(carts.findActiveCartByUserId(user.getuser()))
				.ifPresent(c -> this.subtotal = c.getTotalPrice());
		return subtotal;
	}

	public void setSubtotal(float subtotal) {

		this.subtotal = subtotal;
	}

	public float getShipping() {
		return shipping;
	}

	public void setShipping(float shipping) {
		this.shipping = shipping;
	}

	public float getTax() {
		tax = subtotal * 0.19f;
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		LoginBean user = application.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
		Optional.ofNullable(carts.findActiveCartByUserId(user.getuser()))
				.ifPresent(c -> this.tax = c.getTotalPrice() * 0.19f);
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getTotal() {

		return tax + subtotal + shipping;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<Integer> getQty() {
		return qty;
	}

	public void setQty(List<Integer> qty) {
		this.qty = qty;
	}

	public void display(String test) {
		System.out.println("tessstt " + test);

	}

	public Integer getTest() {
		return test;
	}

	public void setTest(Integer test) {
		this.test = test;
	}

	public Cart getGuestCart() {
		return guestCart;
	}

	public void setGuestCart(Cart guestCart) {
		this.guestCart = guestCart;
	}
	
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean openCart() {

		ca = false;
		Optional.ofNullable(guestCart.getProducts()).ifPresent(p -> {
			if (p.size() != 0)
				this.ca = true;
		});
		return ca;
	}
	
	public boolean found() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		LoginBean user = application.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
		Optional.ofNullable(carts.findActiveCartByUserId(user.getuser())).ifPresent(c -> {
			if (c.getProducts().size() != 0)
				state = false;
		});

		return state;
	}

	/*
	 * public String addProd(Product prod, User user) { prod.setQuantity(1);
	 * System.out.println(carts.addProdCart(user, prod)); guestCart =
	 * carts.findActiveCartByUserId(user); return ""; }
	 */

	public String addProd(Product prod, User user) {
		prod.setQuantity(1);
		System.out.println(prod.getBarecode());
		if (user != null) {
			System.out.println(carts.addProdCart(user, prod));
			guestCart = carts.findActiveCartByUserId(user);
		}else {
			CartProdPk pk = new CartProdPk();
			pk.setCart(0);
			pk.setProd(prod.getBarecode());
			CartProduct cp = new CartProduct();
			cp.setCartProdPk(pk);
			System.out.println("servicees   "+prod.getQuantity());
			cp.setProduct(prods.findProductBybarcode(prod.getBarecode()));
			cp.setQuantity(prod.getQuantity());
			if(guestCart.getProducts()==null) {
				guestCart.setProducts(new ArrayList<CartProduct>());
			}
			if(!guestCart.getProducts().stream().map(p -> p.getCartProdPk().getProd()).collect(Collectors.toList()).contains(prod.getBarecode())) {
				System.out.println("adding");
				guestCart.getProducts().add(cp);
				guestCart.setTotalPrice(guestCart.getTotalPrice()+(prod.getQuantity()*prod.getPrice()));
				count++;
				return "";
			}
			
			return "";
		}
		return "";
	}

	public String removeProd(Product prod, User user,int  qt) {
		prod.setQuantity(qt);
		if(user!=null) {
		carts.removeProd(user, prod);
		}else {
			CartProdPk pk = new CartProdPk();
			pk.setCart(0);
			pk.setProd(prod.getBarecode());
			CartProduct cp = new CartProduct();
			cp.setCartProdPk(pk);
			if(guestCart.getProducts()==null) {
				return "";
			}
			
			if(guestCart.getProducts().stream().map(p -> p.getCartProdPk()).collect(Collectors.toList()).contains(pk)) {
				List<CartProduct> t = new ArrayList<CartProduct>();
				for (CartProduct cartProduct : guestCart.getProducts()) {
					if(cartProduct.getCartProdPk().getProd()==prod.getBarecode())
						continue;
					t.add(cartProduct);
				}
				guestCart.setProducts(t);
				guestCart.setTotalPrice(guestCart.getTotalPrice()-(prod.getQuantity()*prod.getPrice()));
				count--;
				return "";
			}
			
			return "";
		}
		return "";
	}

	public String modProd(Product prod, User user, int qt) {
		prod.setQuantity(qt);
		if(user!=null) {
		carts.modProd(user, prod);
		}else {
			
			CartProdPk pk = new CartProdPk();
			pk.setCart(0);
			pk.setProd(prod.getBarecode());
			CartProduct cp = new CartProduct();
			cp.setCartProdPk(pk);
			if(guestCart.getProducts()==null) {
				return "";
			}
			if(guestCart.getProducts().stream().map(p -> p.getCartProdPk()).collect(Collectors.toList()).contains(pk)) {
				int diff=0;
				for (CartProduct c : guestCart.getProducts()) {
					if(c.getCartProdPk()==pk) {
						diff=qt-c.getQuantity();
						c.setQuantity(qt);
						break;
					}
					
				}
				
				guestCart.setTotalPrice(guestCart.getTotalPrice()+(diff*prod.getPrice()));
				
				return "";
			}
			return "";
		}
		return "";
	}

	public Cart getCurCart() {
		
		return carts.findActiveCartByUserId(loginBean.getuser());
	}

	public void setCurCart(Cart curCart) {
		this.curCart = curCart;
	}

	
}
