package tn.esprit.consomitounsi.services.impl.gestionlivraison;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.entities.Roles;
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

	@Override
	public int addDeliveryMan(DeliveryMan deliveryMan) {
		try {
			SecUtils sec = new SecUtils();
			deliveryMan.setSalt(SecUtils.getSalt());
			String pass = deliveryMan.getPassword();
			String secPass = sec.getSecurePassword(pass, deliveryMan.getSalt());
			deliveryMan.setPassword(secPass);
			deliveryMan.setValid(false);
			deliveryMan.setDob(getCurrentDate());
			deliveryMan.setRole(Roles.DELEVERYMAN);
			em.persist(deliveryMan);
			String tok = deliveryMan.getIdUser() + "." + sec.generateToken(15);
			deliveryMan.setVerifToken(tok);

//			EmailService email = new EmailService();
//			String body = "Please click on the link below to activate your account\n"
//					+ "http://localhost:9080/ConsomiTounsi-web/api/user/verify?token=" + tok;
//			email.sendEmail("service@consomitounsi.tn", "Verification", deliveryMan.getEmail(), body);
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
		bonus.setDate(getCurrentDate());
		em.persist(bonus);
	}

	@Override
	public List<Bonus> getAllBonusByDeliveryManId(int deliveryManId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Bonus> query = em.createQuery(
				"select bon from Bonus bon where bon.deliveryMan=:del order by bon.date desc", Bonus.class);
		query.setParameter("del", del);

		return query.getResultList();
	}

	@Override
	public Bonus getActualBonusByDeliveryManId(int deliveryManId, int month) {

		try {
			DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
			TypedQuery<Bonus> query = em.createQuery(
					"select bon from Bonus bon where bon.deliveryMan=:del and EXTRACT(MONTH FROM bon.date)=:month ",
					Bonus.class);
			query.setParameter("del", del);
			query.setParameter("month", month);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public List<Bonus> getAllBonusByYear(int year) {
		TypedQuery<Bonus> query = em.createQuery(
				"Select b" + " from Bonus b " + "where EXTRACT(YEAR FROM b.date)=:year order by b.date desc",
				Bonus.class);

		query.setParameter("year", year);

		return query.getResultList();
	}

	@Override
	public List<DeliveryMan> getDeliveryMenByBaseAndDay(String base, String day) {
		List<DeliveryMan> results = new ArrayList<>();
		List<DeliveryMan> deliverymen = this.getAllDeliveryMen();
		for (DeliveryMan del : deliverymen) {
			if (del.getAvailabilities().contains(day) && del.getBase().contains(base)) {
				results.add(del);
			}
		}

		return results;
	}

	@Override
	public List<DeliveryMan> getDeliveryMenByDay(String day) {
		List<DeliveryMan> results = new ArrayList<>();
		List<DeliveryMan> deliverymen = this.getAllDeliveryMen();
		for (DeliveryMan del : deliverymen) {
			if (del.getAvailabilities().contains(day)) {
				results.add(del);
			}
		}

		return results;

	}

	@Override
	public List<DeliveryMan> getDeliveryMenByBase(String base) {
		List<DeliveryMan> results = new ArrayList<>();
		List<DeliveryMan> deliverymen = this.getAllDeliveryMen();
		for (DeliveryMan del : deliverymen) {
			if (del.getBase().contains(base)) {
				results.add(del);
			}
		}

		return results;
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
		Optional.ofNullable(deliverMan.getPassword()).ifPresent(p -> {

			if (!p.equals("")) {

				try {
					SecUtils sec = new SecUtils();
					del.setSalt(SecUtils.getSalt());
					String pass = p;
					String secPass = sec.getSecurePassword(pass, del.getSalt());
					del.setPassword(secPass);

				} catch (NoSuchAlgorithmException e) {
					Logger logger = Logger.getGlobal();
					logger.log(Level.INFO, "Got an exception.", e);
				}
			}
		});
		Optional.ofNullable(deliverMan.getAddress()).ifPresent(a -> {
			if (!a.equals("")) {
				del.setAddress(a);
			}
		});
		Optional.ofNullable(deliverMan.getFirstName()).ifPresent(f -> {
			if (!f.equals("")) {
				del.setFirstName(f);
			}
		});
		Optional.ofNullable(deliverMan.getLastName()).ifPresent(l -> {
			if (!l.equals("")) {
				del.setLastName(l);
			}
		});
		Optional.ofNullable(deliverMan.getPhone()).ifPresent(p -> {
			if (!p.equals("")) {
				del.setPhone(p);
			}
		});
		Optional.ofNullable(deliverMan.getImg()).ifPresent(i -> {
			if (!i.equals("")) {
				del.setImg(i);
			}
		});
		Optional.ofNullable(deliverMan.getAvailabilities()).ifPresent(a -> {
			if (!a.isEmpty()) {
				del.setAvailabilities(a);
			}
		});
		Optional.ofNullable(deliverMan.getBase()).ifPresent(b -> {
			if (!b.isEmpty()) {
				del.setBase(b);
			}
		});
		Optional.ofNullable(deliverMan.getTransportation()).ifPresent(t -> {
			if (!t.equals("")) {
				del.setTransportation(t);
			}
		});
		Optional.ofNullable(deliverMan.getEmail()).ifPresent(e -> {
			if (!e.equals("")) {
				del.setEmail(e);
			}
		});

	}

	@Override
	public void deleteDeliveryManById(int deliveryManId) {
		em.remove(em.find(DeliveryMan.class, deliveryManId));

	}

	@Override
	public void deleteBonusById(int bonusId) {
		Bonus bon = em.find(Bonus.class, bonusId);
		if (bon != null) {
			em.remove(bon);
		}

	}

	@Override
	public void addDelivery(int billId, Delivery delivery) {

		Bill bill = em.find(Bill.class, billId);

		Date date = getCurrentDate();
		delivery.setDeliveryDate(date);
		delivery.setBill(bill);

		delivery.setDeliveryState(DeliveryState.REGISTERED);
		em.persist(delivery);

	}

	@Override
	public void assignDeliveryToDeliveryMan(int deliveryManId, int deliveryId, Date date) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		Delivery deli = em.find(Delivery.class, deliveryId);
		deli.setToDo(date);
		deli.setDeliveryState(DeliveryState.PENDING);
		deli.setDeliveryMan(del);

	}

	@Override
	public float shippingCost(String region, double weight) {

		float cost;
		List<String> tunis = new ArrayList<>();
		tunis.add("tunis");
		tunis.add("ariana");
		tunis.add("ben arous");
		if (tunis.contains(region.toLowerCase())) {
			if (weight <= 10) {
				cost = 10;
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
				cost = 20;
			}

			else if ((weight > 10) && (weight <= 70)) {
				cost = 40;
			}

			else {
				cost = 60;
			}
		}

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
		String que = "";

		if (state == DeliveryState.REGISTERED) {
			que = "Select del from Delivery del where del.deliveryState=:state order by del.deliveryState asc ";

		} else {
			que = "Select del from Delivery del where del.deliveryState=:state order by del.deliveryState desc ";
		}

		TypedQuery<Delivery> query = em.createQuery(que, Delivery.class);

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
	public List<Delivery> getDeliveriesByDeliveryMan(int deliveryManId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Delivery> query = em.createQuery("Select deli from Delivery deli where deli.deliveryMan=:del",
				Delivery.class);

		query.setParameter("del", del);

		return query.getResultList();

	}

	@Override
	public List<Delivery> getDeliveriesTaskByDeliveryManId(int deliveryManId) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Delivery> query = em.createQuery(
				"Select deli from Delivery deli where deli.deliveryMan=:del and deli.deliveryState=:pending  order by deli.toDo asc",
				Delivery.class);

		query.setParameter("del", del);
		query.setParameter("pending", DeliveryState.PENDING);
		return query.getResultList();
	}

	@Override
	public List<Delivery> getAccomplishedDeliveryByDeliveryManIdPerYear(int deliveryManId, int year) {
		DeliveryMan del = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Delivery> query = em.createQuery(
				"Select deli from Delivery deli where deli.deliveryMan=:del and deli.deliveryState=:done AND EXTRACT(YEAR FROM deli.deliveredDate)=:year order by deli.deliveredDate desc",
				Delivery.class);

		query.setParameter("del", del);
		query.setParameter("year", year);
		query.setParameter("done", DeliveryState.DONE);
		return query.getResultList();
	}

	@Override
	public long getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(int deliveryManId, int month, int year) {

		DeliveryMan deliveryMan = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Long> query = em.createQuery("Select count(del) from Delivery del "
				+ "where EXTRACT(MONTH FROM del.deliveredDate)=:month AND del.deliveryMan=:delive AND EXTRACT(YEAR FROM del.deliveredDate)=:year and del.deliveryState=:done",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("done", DeliveryState.DONE);
		query.setParameter("delive", deliveryMan);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public Date getCurrentDate() {
		return java.sql.Date.valueOf(LocalDate.now());
	}

	@Override
	public List<Bonus> getAllBonus() {
		return em.createQuery("Select bon from Bonus bon order by bon.date desc", Bonus.class).getResultList();
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

	@Override
	public long getNumberOfAccomplishedDeliveryByDeliveryMan(int deliveryManId) {
		DeliveryMan deliveryMan = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Long> query = em.createQuery("Select count(del)" + " from Delivery del "
				+ "where  del.deliveryMan=:deliveryMan and del.deliveryState=:done", Long.class);
		query.setParameter("done", DeliveryState.DONE);
		query.setParameter("deliveryMan", deliveryMan);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long getNumberOfUnDoneDeliveryByDeliveryMan(int deliveryManId) {
		DeliveryMan deliveryMan = em.find(DeliveryMan.class, deliveryManId);
		TypedQuery<Long> query = em.createQuery("Select count(del)" + " from Delivery del "
				+ "where  del.deliveryMan=:deliveryMan and del.deliveryState=:pending", Long.class);
		query.setParameter("pending", DeliveryState.PENDING);
		query.setParameter("deliveryMan", deliveryMan);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public Delivery getDeliveryById(int id) {

		return em.find(Delivery.class, id);
	}

	@Override
	public long numberOfDeliveriesByMonth(int year, int month) {
		TypedQuery<Long> query = em.createQuery("Select count(del) from Delivery del "
				+ "where EXTRACT(MONTH FROM del.deliveredDate)=:month AND EXTRACT(YEAR FROM del.deliveredDate)=:year and del.deliveryState=:done",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("done", DeliveryState.DONE);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public boolean isBonusAlready() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());

		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		TypedQuery<Bonus> query = em.createQuery(
				"select bon from Bonus bon where EXTRACT(MONTH FROM bon.date)=:month AND EXTRACT(YEAR FROM bon.date)=:year ",
				Bonus.class);
		query.setParameter("month", month);
		query.setParameter("year", year);

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}