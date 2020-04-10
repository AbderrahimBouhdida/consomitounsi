package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.entities.User;

@Remote
public interface IBillServiceRemote {
	public int addBill(Bill bill);

	public boolean removeBill(int idBill);

	//public void updateBill(Bill bill);

	public Bill findBillById(int id);

	public List<Bill> findAllBill();
	
	public List<Bill> findBillsByUser(User user);
}
