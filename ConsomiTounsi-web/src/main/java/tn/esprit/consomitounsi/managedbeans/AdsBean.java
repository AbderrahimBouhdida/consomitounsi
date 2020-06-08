package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import tn.esprit.consomitounsi.entities.Ads;

import tn.esprit.consomitounsi.services.impl.AdsService;


@ManagedBean(name = "adsBean")
@SessionScoped
public class AdsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idAds;
	private String name;
	private Date StartDay;
	private Date EndDay;
	private int ViewsNumber;
	private int FinalViewsNumber;
	private List<Ads> ad;
	
	@EJB
	AdsService adsService;
	
	public int getIdAds() {
		return idAds;
	}
	public void setIdAds(int idAds) {
		this.idAds = idAds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDay() {
		return StartDay;
	}
	public void setStartDay(Date startDay) {
		StartDay = startDay;
	}
	public Date getEndDay() {
		return EndDay;
	}
	public void setEndDay(Date endDay) {
		EndDay = endDay;
	}
	public int getViewsNumber() {
		return ViewsNumber;
	}
	public void setViewsNumber(int viewsNumber) {
		ViewsNumber = viewsNumber;
	}
	public int getFinalViewsNumber() {
		return FinalViewsNumber;
	}
	public void setFinalViewsNumber(int finalViewsNumber) {
		FinalViewsNumber = finalViewsNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	public List<Ads> getAd() {
		return adsService.findAllAds();
	}
	public void setAd(List<Ads> ad) {
		this.ad = ad;
	}
	public AdsService getAdsService() {
		return adsService;
	}
	public void setAdsService(AdsService adsService) {
		this.adsService = adsService;
	}
	public void addAds() {
		Ads ads = new Ads();
		ads.setName(name);
		ads.setIdAds(idAds);
		ads.setStartDay(StartDay);
		ads.setEndDay(EndDay);
		ads.setViewsNumber(ViewsNumber);
		ads.setFinalViewsNumber(FinalViewsNumber);
	
		adsService.addAds(ads);
	}
	
	public String removeAds() {
		System.out.println(idAds);
		adsService.removeAds(idAds);
		return "";
	}
	
	public void updateAds() {
		Ads ad = new Ads();
		ad.setIdAds(idAds);
		ad.setName(name);
		ad.setStartDay(StartDay);
		ad.setEndDay(EndDay);
		ad.setFinalViewsNumber(FinalViewsNumber);
		ad.setViewsNumber(ViewsNumber);
		adsService.updateAds(ad);
	}
	
	
	

	

}

