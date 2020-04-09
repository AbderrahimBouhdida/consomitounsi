package tn.esprit.consomitounsi.services.intrf.gestionlivraison;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.gestionlivraison.Bonus;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryState;

@Remote
public interface DeliveryServiceRemote {

	// delivery man functionalities

	public int addDeliveryMan(DeliveryMan deliveryMan);

	public void addBonus(int deliveryManId, Bonus bonus);

	public List<Bonus> getAllBonusByDeliveryManId(int deliveryManId);

	public List<Bonus> getAllBonusByYear(int year);

	public List<Bonus> getAllBonus();

	public List<DeliveryMan> getDeliveryMenByBaseAndDay(String base, String day);

	public List<DeliveryMan> getDeliveryMenByDay(String day);

	public List<DeliveryMan> getDeliveryMenByBase(String base);

	public List<DeliveryMan> getAllDeliveryMen();

	public DeliveryMan getDeliveryManById(int deliveryManId);

	public long numberOfActiveDeliveryMen();

	public void updateDeliveryMan(DeliveryMan deliverMan);

	public void deleteDeliveryManById(int deliveryManId);

	public void deleteBonusById(int bonusId);

	/* delivery functionalities */

	public void addDelivery(Delivery delivery);

	public void assignDeliveryToDeliveryMan(int deliveryManId, int deliveryId);

	public double shippingCost(String region, double weight, int reduction);

	public void validateDelivery(int deliveryId);

	public void deleteDeliveryById(int deliveryId);

	public List<Delivery> getAllDeliveries();

	public List<Delivery> getAllDeliveriesByState(DeliveryState state);

	public Set<Delivery> getDeliveriesByDeliveryMan(int deliveryManId);

	public long numberOfDeliveriesByYear(int year);

	public List<Delivery> getDeliveriesTaskByDeliveryManId(int deliveryManId);

	public List<Delivery> getAccomplishedDeliveryByDeliveryManIdPerYear(int deliveryManId, int year);

	public List<Delivery> getAccomplishedDeliveryByDeliveryManIdPerYearAndMonth(int deliveryManId, int year, int month);

	public long getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(int deliveryManId, int month, int year);

	public long getNumberOfAccomplishedDeliveryByDeliveryManPerYear(int deliveryManId, int year);

	public Date getCurrentDate();

}
