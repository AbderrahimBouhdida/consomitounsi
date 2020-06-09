package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Adress;
import tn.esprit.consomitounsi.entities.User;



@Remote
public interface IAdressServices {
	public int addAdress(Adress adress);
	public void removeAdress(int id);
	public void updateAdress(Adress adressNewValues);
	List<Adress> getAddresses(User user);
	Adress getDefault(User user);
	List<Adress> getAllAddresses();
}
