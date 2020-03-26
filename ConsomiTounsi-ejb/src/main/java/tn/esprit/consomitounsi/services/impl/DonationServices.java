package tn.esprit.consomitounsi.services.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import tn.esprit.consomitounsi.entities.Donation;
import tn.esprit.consomitounsi.services.intrf.DonationServiceRemote;

@LocalBean
@Stateless
public class DonationServices implements DonationServiceRemote {
	
	
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
	public void updateDonation(Donation connectionNewValues) {
		Donation coll = em.find(Donation.class, connectionNewValues.getIddon());
		//coll.setPassword(connectionNewValues.getPassword());
		coll.setName(connectionNewValues.getName());
		
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

}
