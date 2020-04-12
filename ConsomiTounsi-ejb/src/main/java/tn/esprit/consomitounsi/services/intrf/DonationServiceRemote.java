package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;


import tn.esprit.consomitounsi.entities.Donation;

@Remote
public interface DonationServiceRemote {
	public int addDonation(Donation Donation);
    public void removeDonation(int id);
    public void updateDonation(Donation Donation);
    public void updateDonationv2(Donation Donation);
    public Donation findDonationById(int id);
    public List<Donation> findAllDonation();
    public List<Object[]> topdonators();
    public String topdonatorsstring();
    //public int MandateCost();

}
