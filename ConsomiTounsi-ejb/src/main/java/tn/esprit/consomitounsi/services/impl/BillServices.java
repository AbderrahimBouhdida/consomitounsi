package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.IBillServiceRemote;

@Stateless
@LocalBean
public class BillServices implements IBillServiceRemote{
	@PersistenceContext
	EntityManager em;
	//ProductService ps;
	CartServices cs = new CartServices();
	
	@Override
	public int addBill(Bill bill) {
		em.persist(bill);
		return 0;
	}
	@Override
	public boolean removeBill(int idBill) {
		try {
			em.remove(em.find(Bill.class, idBill));
			return true;
		}catch (Exception e) {
			System.out.println("doesnt exist");
			return false;
		}
	}
	@Override
	public Bill findBillById(int id) {
		try {
			return em.find(Bill.class, id);
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<Bill> findAllBill() {
		TypedQuery<Bill> query = em.createQuery("SELECT b FROM Bill b", Bill.class);
		try {
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("no bills ");		
			return null;
		}
		
	}
	@Override
	public List<Bill> findBillsByUser(User user) {//to do 
		TypedQuery<Cart> query = em.createQuery("select c from Cart c where user=:user", Cart.class);
		query.setParameter("user", user);
		List<Cart> carts = new ArrayList<Cart>();
		try { 
			carts = query.getResultList();
			Set<Cart> tmpc = new HashSet<Cart>(carts);
			tmpc.forEach(p->System.out.println("cart"+p.getIdCart()));
			TypedQuery<Bill> query2 = em.createQuery("SELECT b FROM Bill b WHERE cart IN :carts", Bill.class);
			query2.setParameter("carts",tmpc);
			List<Bill> bills = query2.getResultList();
			bills.forEach(p->System.out.println("biillss : "+p.getIdBill()));
			return bills;
		}catch (Exception e) { System.out.println("Erreur : " + e); }
		return null;
	}
}
