package tn.esprit.consomitounsi.services.impl.gestionlivraison;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.gestionlivraison.Bonus;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryState;
import tn.esprit.consomitounsi.services.impl.SecUtils;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@Stateless
@LocalBean
public class DeliveryService implements DeliveryServiceRemote {

	@PersistenceContext
	EntityManager em;
	SecUtils sec = new SecUtils();

	@Override
	public int addDeliveryMan(DeliveryMan deliveryMan) {
		try {
			deliveryMan.setSalt(SecUtils.getSalt());
			String pass = deliveryMan.getPassword();
			String secPass = sec.getSecurePassword(pass, deliveryMan.getSalt());
			deliveryMan.setPassword(secPass);
			em.persist(deliveryMan);
		} catch (NoSuchAlgorithmException e) {
			Logger logger = Logger.getGlobal();
			logger.log(Level.INFO, "Got an exception.", e);
		}

		return deliveryMan.getIdUser();
		
	}

	@Override
	public void addBonus(int deliveryManId, Bonus bonus) {
		DeliveryMan deliveryMan = em.find(DeliveryMan.class, deliveryManId);
		bonus.setDeliveryMan(deliveryMan);
		em.persist(bonus);
	}

	@Override
	public List<Bonus> getAllBonusByDeliveryManId(int deliveryManId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Bonus> query = em.createQuery("select bon from Bonus bon where bon.deliveryMan=:del", Bonus.class);
		query.setParameter("del", del);

		return query.getResultList();
	}

	@Override
	public List<Bonus> getAllBonusByYear(int year) {
		TypedQuery<Bonus> query = em
				.createQuery("Select b" + " from Bonus b " + "where EXTRACT(YEAR FROM b.date)=:year", Bonus.class);

		query.setParameter("year", year);

		return query.getResultList();
	}

	@Override
	public List<DeliveryMan> getDeliveryMenByBaseAndDay(String base, String day) {
		TypedQuery<DeliveryMan> query = em.createQuery(
				"Select d from DeliveryMan d where d.base LIKE :pattern  AND d.availabilities LIKE :patternd  ",
				DeliveryMan.class);

		query.setParameter("pattern", "%" + base + "%");
		query.setParameter("patternd", "%" + day + "%");

		return query.getResultList();
	}

	@Override
	public List<DeliveryMan> getDeliveryMenByDay(String day) {
		TypedQuery<DeliveryMan> query = em
				.createQuery("Select d from DeliveryMan d where d.availabilities LIKE :patternd  ", DeliveryMan.class);

		query.setParameter("patternd", "%" + day + "%");

		return query.getResultList();
	}

	@Override
	public List<DeliveryMan> getDeliveryMenByBase(String base) {
		TypedQuery<DeliveryMan> query = em.createQuery("Select d from DeliveryMan d where d.base  LIKE :pattern ",
				DeliveryMan.class);

		query.setParameter("pattern", "%" + base + "%");

		return query.getResultList();
	}

	@Override
	public List<DeliveryMan> getAllDeliveryMen() {

		return em.createQuery("Select del from DeliveryMan del", DeliveryMan.class).getResultList();
	}

	@Override
	public DeliveryMan getDeliveryManById(int deliveryManId) {

		return em.createQuery("Select del from DeliveryMan del where del.idUser=:deliveryManId", DeliveryMan.class)
				.setParameter("deliveryManId", deliveryManId).getSingleResult();

	}

	@Override
	public void updateDeliveryMan(DeliveryMan deliverMan) {
		DeliveryMan del = em.find(DeliveryMan.class, deliverMan.getIdUser());
		if (!(deliverMan.getTransportation().equals("")) && (deliverMan.getTransportation() != null)) {
			del.setTransportation(deliverMan.getTransportation());
		}
		if (!(deliverMan.getPassword().equals("")) && (deliverMan.getPassword() != null)) {
			del.setPassword(deliverMan.getPassword());
		}
		if (!(deliverMan.getPhone().equals("")) && (deliverMan.getPhone() != null)) {
			del.setPhone(deliverMan.getPhone());
		}

		if (!(deliverMan.getAddress().equals("")) && (deliverMan.getAddress() != null)) {
			del.setAddress(deliverMan.getAddress());
		}
		if (!(deliverMan.getAvailabilities().equals("")) && (deliverMan.getAvailabilities() != null)) {
			del.setAvailabilities(deliverMan.getAvailabilities());
		}

		if (!(deliverMan.getBase().equals("")) && (deliverMan.getBase() != null)) {
			del.setBase(deliverMan.getBase());
		}

	}

	@Override
	public void deleteDeliveryManById(int deliveryManId) {
		em.remove(em.find(DeliveryMan.class, deliveryManId));

	}

	@Override
	public void deleteBonusById(int bonusId) {
		em.remove(em.find(Bonus.class, bonusId));

	}

	@Override
	public void addDelivery(Delivery delivery) {

		Date date = getCurrentDate();
		delivery.setDeliveryDate(date);

		delivery.setDeliveryState(DeliveryState.REGISTERED);
		em.persist(delivery);

	}

	@Override
	public void assignDeliveryToDeliveryMan(int deliveryManId, int deliveryId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		Delivery deli = em.find(Delivery.class, deliveryId);
		deli.setDeliveryState(DeliveryState.PENDING);
		if (del.getDeliveries() == null) {
			Set<Delivery> deliveries = new HashSet<>();
			deliveries.add(deli);
			del.setDeliveries(deliveries);
		} else {
			del.getDeliveries().add(deli);
		}
		deli.setDeliveryMan(del);

	}

	@Override
	public double shippingCost(String region, double weight, int reduction) {

		double cost;
		List<String> noFar = new ArrayList<>();
		noFar.add("Tunis");
		noFar.add("Ariana");
		if (noFar.contains(region)) {
			if (weight <= 10) {
				cost = 15;
			}

			else if ((weight > 10) && (weight <= 70)) {
				cost = 30;
			}

			else {
				cost = 40;
			}
		}

		else {
			if (weight <= 10) {
				cost = 25;
			}

			else if ((weight > 10) && (weight <= 70)) {
				cost = 45;
			}

			else {
				cost = 60;
			}
		}

		cost = cost - cost * reduction / 100;

		return cost;
	}

	@Override
	public void validateDelivery(int deliveryId) {
		Delivery deli = em.find(Delivery.class, deliveryId);
		deli.setDeliveryState(DeliveryState.DONE);
		deli.setDeliveredDate(getCurrentDate());

	}

	@Override
	public List<Delivery> getAllDeliveries() {

		return em.createQuery("Select del from Delivery del", Delivery.class).getResultList();
	}

	@Override
	public List<Delivery> getAllDeliveriesByState(DeliveryState state) {
		TypedQuery<Delivery> query = em.createQuery("Select del from Delivery del where del.deliveryState=:state",
				Delivery.class);

		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public long numberOfDeliveriesByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(del) from Delivery del where EXTRACT(YEAR FROM del.deliveredDate)=:year", Long.class);

		query.setParameter("year", year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfActiveDeliveryMen() {
		TypedQuery<Long> query = em.createQuery("select count(del) from DeliveryMan del", Long.class);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public Set<Delivery> getDeliveriesByDeliveryMan(int deliveryManId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		return del.getDeliveries();
	}

	@Override
	public List<Delivery> getDeliveriesTaskByDeliveryManId(int deliveryManId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Delivery> query = em.createQuery(
				"Select deli from Delivery deli where deli.deliveryMan=:del and deli.deliveryState=:pending",
				Delivery.class);

		query.setParameter("del", del);
		query.setParameter("pending", DeliveryState.PENDING);
		return query.getResultList();
	}

	@Override
	public List<Delivery> getAccomplishedDeliveryByDeliveryManIdPerYear(int deliveryManId, int year) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Delivery> query = em.createQuery(
				"Select deli from Delivery deli where deli.deliveryMan=:del and deli.deliveryState=:done AND EXTRACT(YEAR FROM deli.deliveredDate)=:year",
				Delivery.class);

		query.setParameter("del", del);
		query.setParameter("year", year);
		query.setParameter("done", DeliveryState.DONE);
		return query.getResultList();
	}

	@Override
	public long getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(int deliveryManId, int month, int year) {

		DeliveryMan deliveryMan = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Long> query = em.createQuery("Select count(del)" + " from Delivery del "
				+ "where EXTRACT(MONTH FROM del.deliveredDate)=:month AND del.deliveryMan=:deliveryMan AND EXTRACT(YEAR FROM del.deliveredDate)=:year and del.deliveryState=:done",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("done", DeliveryState.DONE);
		query.setParameter("deliveryMan", deliveryMan);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public Date getCurrentDate() {
		return java.sql.Date.valueOf(LocalDate.now());
	}

	@Override
	public List<Bonus> getAllBonus() {
		return em.createQuery("Select bon from Bonus bon", Bonus.class).getResultList();
	}

	@Override
	public List<Delivery> getAccomplishedDeliveryByDeliveryManIdPerYearAndMonth(int deliveryManId, int year,
			int month) {

		List<Delivery> perYear = getAccomplishedDeliveryByDeliveryManIdPerYear(deliveryManId, year);
		List<Delivery> perMonth = new ArrayList<>();
		for (Delivery del : perYear) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(del.getDeliveredDate());
			if (month == calendar.get(Calendar.MONTH) + 1) {
				perMonth.add(del);
			}
		}

		return perMonth;
	}

	@Override
	public void deleteDeliveryById(int deliveryId) {
		em.remove(em.find(Delivery.class, deliveryId));

	}

	@Override
	public long getNumberOfAccomplishedDeliveryByDeliveryManPerYear(int deliveryManId, int year) {
		DeliveryMan deliveryMan = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Long> query = em.createQuery("Select count(del)" + " from Delivery del "
				+ "where  del.deliveryMan=:deliveryMan AND EXTRACT(YEAR FROM del.deliveredDate)=:year and del.deliveryState=:done",
				Long.class);
		query.setParameter("year", year);
		query.setParameter("done", DeliveryState.DONE);
		query.setParameter("deliveryMan", deliveryMan);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

}