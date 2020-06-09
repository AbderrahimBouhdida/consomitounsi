package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Ads;


@Remote 
public interface AdsRemote {
	public int addAds(Ads ads);
    public void removeAds(int idAds);
    public void updateAds(Ads adsNewValues);
    public Ads findAdsById(int idAds);
    public List<Ads> findAllAds();
    
}
