package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import tn.esprit.consomitounsi.entities.Donation;
import tn.esprit.consomitounsi.services.intrf.DonationServiceRemote;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.Topdonators;

@LocalBean
@Stateless
public class DonationServices implements DonationServiceRemote {
	
	public String out="";
	
	
	@PersistenceContext
    EntityManager em;

	@Override
	public int addDonation(Donation donation) {
		em.persist(donation);
        return donation.getIddon();
	}

	@Override
	public void removeDonation(int id) {
		 em.remove(em.find(Donation.class, id));
	     System.out.println("Donation deleted");
		
	}

	@Override
	public void updateDonation(Donation d) {
		Donation coll = em.find(Donation.class, d.getIddon());
		//coll.setPassword(connectionNewValues.getPassword());
		coll.setName(d.getName());
		
	}
	
	@Override
	public void updateDonationv2(Donation Donation) {
		
		
		try {
			em.merge(Donation);
	    } catch (PersistenceException e) {
	        System.out.println("Update Error: " + e.getMessage());
	    }
		
		
	}

	@Override
	public Donation findDonationById(int id) {
		Donation Donation = em.find(Donation.class, id);
        return Donation;
	}

	@Override
	public List<Donation> findAllDonation() {
		List<Donation> Donation = em.createQuery("from Donation", Donation.class).getResultList();
        return Donation;
	}
	
	@Override
	public  List<Object[]> topdonators() {
        Query query = em.createQuery("SELECT user,Count(iddon) FROM Donation d GROUP BY d.user ORDER BY Count(iddon) DESC");
	     List<Object[]> results = query.getResultList();	
	     	     
        return results;
	    }
	
	@Override
	public String topdonatorsstring() {

		List<Object[]> l = topdonators();
		List<Topdonators> result = new ArrayList<>(l.size());
		for (Object[] row : l) {
		    result.add(new Topdonators((User) row[0],
		                            (long) row[1]
		                          ));
		}
        
		for (final Topdonators donator : result) {

	       System.out.println("user"+donator.getUsr().getUsername()+"has donated "+donator.getIdusr()+"times !");
	         
	        
	          
			// out += "user"+Integer.toString(donator.getUsr().getUsername())+" has donated "+Long.toString(donator.getIdusr())+"times !"+"\n";
			 out += "user "+donator.getUsr().getUsername()+" has donated "+Long.toString(donator.getIdusr())+"times !"+"\n";

					 
	        
	}
	//	System.out.println(out);
		return out;
	}
	
	
	
//	@Override
//	public int MandateCost() {
//		// TODO Auto-generated method stub
//		Query jpql = em.createQuery("SELECT Count(iddon) FROM Donation d");
//		int x =((Number)jpql.getSingleResult()).intValue();
//		System.out.println("le nombre est "+x);
//		return x;
//		
//	}

	

}
