package tn.esprit.consomitounsi.services.impl;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import tn.esprit.consomitounsi.entities.Ads;
import tn.esprit.consomitounsi.services.intrf.AdsRemote;



@Stateless
@LocalBean
public class AdsService implements AdsRemote{
	@PersistenceContext
	EntityManager em;

	@Override
	public int addAds(Ads ads) {
		// TODO Auto-generated method stub
		em.persist(ads);
        return ads.getIdAds();
	}

	@Override
	public void removeAds(int idAds) {
		// TODO Auto-generated method stub
		em.remove(em.find(Ads.class, idAds));
	     System.out.println("ads deleted");
	}

	@Override
	public void updateAds(Ads adsNewValues) {
		// TODO Auto-generated method stub
		Ads ads = em.find(Ads.class, adsNewValues.getIdAds());
		ads.setName(adsNewValues.getName());
		ads.setViewsNumber(adsNewValues.getViewsNumber());
		ads.setFinalViewsNumber(adsNewValues.getFinalViewsNumber());
		ads.setStartDay(adsNewValues.getStartDay());
		ads.setEndDay(adsNewValues.getEndDay());
	}

	@Override
	public Ads findAdsById(int idAds) {
		// TODO Auto-generated method stub
		Ads ads = em.find(Ads.class, idAds);
        return ads;
	}

	@Override
	public List<Ads> findAllAds() {
		// TODO Auto-generated method stub
		List<Ads> ads = em.createQuery("Select a from Ads a", Ads.class).getResultList();
        return ads;
	}
}