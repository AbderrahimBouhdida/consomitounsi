package tn.esprit.consomitounsi.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

import tn.esprit.consomitounsi.entities.Adress;
import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.States;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.AdressService;
import tn.esprit.consomitounsi.services.impl.BillServices;
import tn.esprit.consomitounsi.services.impl.CartServices;
import tn.esprit.consomitounsi.services.impl.PaymentServices;
import tn.esprit.consomitounsi.services.impl.UserServices;



@ManagedBean(name = "addressBean")
@SessionScoped
public class AddressBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3858281584611695421L;
	private Adress adrToAdd = new Adress();
	private Adress adrToMod;
	private Adress defaultAdr = new Adress();
	private Adress selectedAdr = new Adress();
	private Adress shippingAdr = new Adress();
	private Adress billingAdr = new Adress();
	private Adress selectedShipping = new Adress();
	private States[] states;
	private States state ;
	private States shippingState ;
	private String country;
	private boolean def;
	private boolean addBilling;
	private boolean addShipping;
	private boolean same = false;
	private Bill bill = new Bill();
	
	
	@EJB
	AdressService adressServices;
	@EJB
	UserServices userServices;
	@EJB
	BillServices billServices;
	@EJB
	PaymentServices paymentServices;
	@EJB
	CartServices cartservices;
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;
	
	@ManagedProperty(value = "#{cartBean}")
	CartBean cartBean;
	
	
	
	public Adress getDefaultAdr() {
		defaultAdr = adressServices.getDefault(loginBean.getuser());
		return defaultAdr;
	}

	public void setDefaultAdr(Adress defaultAdr) {
		this.defaultAdr = defaultAdr;
	}

	public boolean isDef() {
		return def;
	}

	public void setDef(boolean def) {
		this.def = def;
	}

	public Adress getAdrToMod() {
		return adrToMod;
	}

	public void setAdrToMod(Adress adrToMod) {
		this.adrToMod = adrToMod;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public States[] getStates() {
		states = States.values();
		return states;
	}

	public void setStates(States[] states) {
		this.states = states;
	}
	

	public Adress getAdrToAdd() {
		return adrToAdd;
	}

	public void setAdrToAdd(Adress adrToAdd) {
		this.adrToAdd = adrToAdd;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Adress getSelectedAdr() {
		//System.out.println("adddrre : before"+ selectedAdr.getFirstName());
		selectedAdr = adressServices.getDefault(loginBean.getuser());
		return selectedAdr;
	}

	public void setSelectedAdr(Adress selectedAdr) {
		this.selectedAdr = selectedAdr;
		
	}
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public boolean isSame() {
		return same;
	}

	public void setSame(boolean same) {
		this.same = same;
	}	

	public Adress getShippingAdr() {
		return shippingAdr;
	}

	public void setShippingAdr(Adress shippingAdr) {
		this.shippingAdr = shippingAdr;
	}

	public States getShippingState() {
		return shippingState;
	}

	public void setShippingState(States shippingState) {
		this.shippingState = shippingState;
	}

	public boolean isAddBilling() {
		return addBilling;
	}

	public void setAddBilling(boolean addBilling) {
		this.addBilling = addBilling;
	}

	public boolean isAddShipping() {
		return addShipping;
	}

	public void setAddShipping(boolean addShipping) {
		this.addShipping = addShipping;
	}

	public Adress getBillingAdr() {
		return billingAdr;
	}

	public void setBillingAdr(Adress billingAdr) {
		this.billingAdr = billingAdr;
	}
	
	public Adress getSelectedShipping() {
		return selectedShipping;
	}

	public void setSelectedShipping(Adress selectedShipping) {
		this.selectedShipping = selectedShipping;
	}
	
	
	
	public CartBean getCartBean() {
		return cartBean;
	}

	public void setCartBean(CartBean cartBean) {
		this.cartBean = cartBean;
	}

	public List<Adress> addresses(User user) {
		List<Adress> tmp = (user != null) ? adressServices.getAddresses(user) : new ArrayList<Adress>();
		Collections.sort(tmp, new Comparator<Adress>() {
			
			@Override
			public int compare(Adress o1, Adress o2) {
				return Boolean.compare(o2.isDefAdress(), o1.isDefAdress());
			}
		} );
		return tmp;
	}
	
	public Adress defAdress(User user) {
		return (user != null) ? adressServices.getDefault(user) : new Adress();
	}

	public void delAddress(Adress adress) {
		adressServices.removeAdress(adress.getId());
	}

	public String modAddress(Adress addr) {
		adrToMod = addr;
		this.country=addr.getCountry();
		this.state=addr.getState();
		
		return "/account/edit-address?faces-redirect=true";
	}

	public String confirmModAddr(User user) {
		adrToMod.setCountry(country);
		adrToMod.setState(state);
		adrToMod.setDefAdress(def);
		adressServices.updateAdress(adrToMod);
		def=false;
		return "/account/addresses?faces-redirect=true";
	}
	
	public String addAddr(User user) {
		adrToAdd.setCountry(country);
		adrToAdd.setState(state);
		adrToAdd.setUser(user);
//		Adress tmp = defAdress(user);
//		if(tmp!=null && def== true) {
//			tmp.setDefAdress(false);
//			adressServices.updateAdress(tmp);
//		}
		adrToAdd.setDefAdress(def);
		adressServices.addAdress(adrToAdd);
		def = false;
		adrToAdd = new Adress();
		return "/account/addresses?faces-redirect=true";
	}
	
	public String switchNew() {
		same = true;
		return "";
	}

	////Listener
	
	public Adress getBeer(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Adress beer : adressServices.getAllAddresses()){
            if (id.equals(beer.getId())){
                return beer;
            }
        }
        return null;
    }
	
	public String placeOreder() {
		
		if(!addBilling)
			bill.setBilling(selectedAdr);
		else {
			billingAdr.setUser(loginBean.getuser());
			System.out.println("xcity "+billingAdr.getCity());
			adressServices.addAdress(billingAdr);
			bill.setBilling(billingAdr);
			
		}
		if(same) {
			if(!addShipping)
				bill.setShipping(selectedShipping);
			else {
				shippingAdr.setUser(loginBean.getuser());
				
				adressServices.addAdress(shippingAdr);
				bill.setShipping(shippingAdr);
			}
		}else {
			if(!addBilling)
				bill.setShipping(selectedAdr);
			else 
				bill.setShipping(billingAdr);
		}
		bill.setBillDate(new Timestamp(new Date().getTime()));
		
		bill.setCart(cartBean.getCurCart());	

		
		try {
			String approvalLink = paymentServices.authorizePayment(cartBean.getCurCart());
			
			System.out.println("link : "+approvalLink);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(approvalLink);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//billServices.addBill(bill);
		return null;
		
	}
	
	public String confirmOrder() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String paymentId = request.getParameter("paymentId");
		String payerID = request.getParameter("PayerID");
		String token = request.getParameter("token");
		
		try {
			PaymentServices paymentServices = new PaymentServices();
			Payment payment = paymentServices.executePayment(paymentId, payerID);
			
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			
			System.out.println("payment successs \n user "+payerInfo.getFirstName()+" with transaction "+transaction.getDescription());
			billServices.addBill(bill);
			cartservices.closeCart(bill.getCart());
			Cart cr = new Cart(loginBean.getuser(), true);
			cartservices.addCart(cr);
			return "/store/index?faces-redirect=true";
			
		} catch (PayPalRESTException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public String curDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");  
		   LocalDateTime now = LocalDateTime.now();  
		   return dtf.format(now);  
	}

	
}
