package tn.esprit.consomitounsi.api.gestionlivraison;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.esprit.consomitounsi.entities.gestionlivraison.Bonus;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.entities.gestionlivraison.Exchange;
import tn.esprit.consomitounsi.entities.gestionlivraison.Reclamation;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repair;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repayment;

public class IShowmethod {

	private static final String DESCRIPTION = "description";
	private static final String CREATED = "created";
	private static final String PRODUCT = "product";
	private static final String DONEON = "doneOn";

	public List<Map<String, Object>> deliveries(List<Delivery> deliveries) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Delivery del : deliveries) {

			results.add(delivery(del));
		}

		return results;
	}

	public List<Map<String, Object>> reclamations(List<Reclamation> reclamations) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Reclamation rec : reclamations) {

			results.add(reclamation(rec));
		}

		return results;
	}

	public List<Map<String, Object>> deliveryMen(List<DeliveryMan> deliveryMen) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (DeliveryMan del : deliveryMen) {

			results.add(deliveryMan(del));
		}
		return results;
	}

	public List<Map<String, Object>> bonuss(List<Bonus> bonnus) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Bonus bon : bonnus) {

			results.add(bonus(bon));
		}
		return results;
	}

	public List<Map<String, Object>> exchanges(List<Exchange> exchanges) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Exchange ex : exchanges) {

			results.add(exchange(ex));
		}
		return results;
	}

	public List<Map<String, Object>> repayments(List<Repayment> repayments) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Repayment r : repayments) {

			results.add(repayment(r));
		}
		return results;
	}

	public List<Map<String, Object>> repairs(List<Repair> repairs) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Repair r : repairs) {

			results.add(repair(r));
		}
		return results;
	}

	public Map<String, Object> deliveryMan(DeliveryMan del) {

		Map<String, Object> obj = new HashMap<>();
		obj.put("idUser", del.getIdUser());
		obj.put("lastName", del.getLastName());
		obj.put("firstName", del.getFirstName());
		obj.put("address", del.getAddress());
		obj.put("phone", del.getPhone());
		obj.put("username", del.getUsername());
		obj.put("password", del.getPassword());
		obj.put("base", del.getBase());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		obj.put("dob", del.getDob() != null ? dateFormat.format(del.getDob()) : "");

		obj.put("transportation", del.getTransportation());
		obj.put("availabilities", del.getAvailabilities());

		List<String> bonus = new ArrayList<>();
		del.getBonus().stream().forEach(d -> bonus.add(d.toString()));
		obj.put("Bonus", bonus);
		List<String> deliveries = new ArrayList<>();
		del.getDeliveries().stream().forEach(d -> deliveries.add(d.toString()));

		obj.put("deliveries", deliveries);

		return obj;
	}

	public Map<String, Object> delivery(Delivery del) {
		Map<String, Object> obj = new HashMap<>();
		obj.put("id", String.valueOf(del.getId()));
		obj.put("deliveryState", del.getDeliveryState());
		obj.put("addresseInformation", del.getAddressInformation());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		obj.put("deliveryDate", del.getDeliveryDate() != null ? dateFormat.format(del.getDeliveryDate()) : "");

		obj.put(DESCRIPTION, del.getDescription());
		obj.put("deliveredDate", del.getDeliveredDate() != null ? dateFormat.format(del.getDeliveredDate()) : "");
		obj.put("cost", del.getCost());
		obj.put("deliveryMan",
				del.getDeliveryMan() != null
						? del.getDeliveryMan().getFirstName() + " " + del.getDeliveryMan().getLastName()
						: "");

		return obj;

	}

	public Map<String, Object> bonus(Bonus bon) {
		Map<String, Object> bonus = new HashMap<>();
		bonus.put("id", bon.getId());
		bonus.put("amount", bon.getAmount());
		bonus.put("date", bon.getDate());
		bonus.put("deliveryMan", bon.getDeliveryMan().getFirstName() + " " + bon.getDeliveryMan().getLastName());
		return bonus;
	}

	public Map<String, Object> reclamation(Reclamation rec) {
		Map<String, Object> reclamation = new HashMap<>();
		reclamation.put("id", rec.getId());
		reclamation.put(DESCRIPTION, rec.getDescription());
		reclamation.put(CREATED, rec.getCreated());
		reclamation.put("answered", rec.getAnswered());
		reclamation.put("decision", rec.getDecision());
		reclamation.put("state", rec.getState());
		reclamation.put(PRODUCT, rec.getProduct().getNameProduct());
		reclamation.put("responseDescription", rec.getResponseDescription());
		reclamation.put("user", rec.getUser().getFirstName() + " " + rec.getUser().getLastName());
		return reclamation;
	}

	public Map<String, Object> exchange(Exchange ex) {
		Map<String, Object> exchange = new HashMap<>();
		exchange.put("code", ex.getCode());
		exchange.put(CREATED, ex.getCreated());
		exchange.put(DONEON, ex.getDoneOn());
		exchange.put("done", ex.isDone());
		exchange.put(PRODUCT, ex.getProduct().getNameProduct());
		return exchange;
	}

	public Map<String, Object> repayment(Repayment rep) {
		Map<String, Object> r = new HashMap<>();
		r.put("id", rep.getId());
		r.put("amount", rep.getAmount());
		r.put(CREATED, rep.getCreated());
		r.put(DONEON, rep.getDoneOn());
		r.put(DESCRIPTION, rep.getDescription());
		r.put("done", rep.isDone());
		r.put("User", rep.getUser().getFirstName() + " " + rep.getUser().getLastName());
		return r;
	}

	public Map<String, Object> repair(Repair rep) {
		Map<String, Object> r = new HashMap<>();
		r.put("id", rep.getId());
		r.put(CREATED, rep.getCreated());
		r.put(DONEON, rep.getDoneOn());
		r.put("cost", rep.getCost());
		r.put(DESCRIPTION, rep.getDescription());
		r.put("done", rep.isDone());
		r.put(PRODUCT, rep.getProduct().getNameProduct());
		r.put("User", rep.getUser().getFirstName() + " " + rep.getUser().getLastName());
		return r;
	}

}
