package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.services.impl.BillServices;


@ManagedBean
@SessionScoped
public class BillBean implements Serializable {

	
	@EJB
	BillServices billServices;
	
	private Bill bill;
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<Bill> allBills(){
		return billServices.findAllBill();
	}

	public String billDetails(Bill bill) {
		this.bill = bill;
		return "invoice_payment?faces-redirects=true";
	}
	
	public List<Bill> userBills(){
		return billServices.findBillsByUser(loginBean.getuser());
	}
	
	public String userBillDetails(Bill bill) {
		this.bill = bill;
		return "detail?faces-redirects=true";
	}
	
	public int billCount() {
		return this.allBills().size();
	}
}
