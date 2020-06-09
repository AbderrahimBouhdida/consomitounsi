package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Adress;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.IAdressServices;

@Stateless
@LocalBean
public class AdressService implements IAdressServices {
	@PersistenceContext
	EntityManager em;

	@Override
	public int addAdress(Adress adress) {
		if (getAddresses(adress.getUser()).size() != 0) {

			if (getDefault(adress.getUser()) == null)
				adress.setDefAdress(true);
			if (adress.isDefAdress() && getDefault(adress.getUser()) != null)
				getDefault(adress.getUser()).setDefAdress(false);
		}else {
			adress.setDefAdress(true);
		}
		em.persist(adress);
		return adress.getId();
	}

	@Override
	public void removeAdress(int id) {
		em.remove(em.find(Adress.class, id));
	}

	@Override
	public void updateAdress(Adress adressNewValues) {
		try {
			if (adressNewValues.isDefAdress())
				getDefault(adressNewValues.getUser()).setDefAdress(false);
			em.merge(adressNewValues);
		} catch (Exception e) {
			System.out.println("doesn't exist or error");
		}
	}

	@Override
	public List<Adress> getAddresses(User user) {
		List<Adress> addresses = new ArrayList<Adress>();
		TypedQuery<Adress> query = em.createQuery("SELECT a FROM Adress a WHERE a.user=:user", Adress.class);
		query.setParameter("user", user);
		try {
			addresses = query.getResultList();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
		return addresses;

	}

	@Override
	public Adress getDefault(User user) {
		Adress def = null;
		TypedQuery<Adress> query = em.createQuery("SELECT a FROM Adress a WHERE a.user=:user and  a.defAdress=true",
				Adress.class);
		query.setParameter("user", user);
		try {
			def = query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
		return def;
	}

	@Override
	public List<Adress> getAllAddresses() {
		List<Adress> addresses = new ArrayList<Adress>();
		TypedQuery<Adress> query = em.createQuery("SELECT a FROM Adress a ", Adress.class);

		try {
			addresses = query.getResultList();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
		return addresses;

	}

}
