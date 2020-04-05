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

public class IShowmethod {

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

	public List<Map<String, Object>> Bonuss(List<Bonus> bonnus) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Bonus bon : bonnus) {

			results.add(bonus(bon));
		}
		return results;
	}

	public List<Map<String, Object>> Exchanges(List<Exchange> exchanges) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Exchange ex : exchanges) {

			results.add(exchange(ex));
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
		obj.put("contract", del.getContract().toString());

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

		obj.put("description", del.getDescription());
		obj.put("deliveredDate", del.getDeliveredDate() != null ? dateFormat.format(del.getDeliveredDate()) : "");
		obj.put("cost", del.getCost());
		obj.put("deliveryMan",
				del.getDeliveryMan() != null
						? "[id: " + String.valueOf(del.getDeliveryMan().getIdUser()) + ", firtsname: "
								+ del.getDeliveryMan().getFirstName() + ", lastname: "
								+ del.getDeliveryMan().getLastName() + "]"
						: "");

		return obj;

	}

	public Map<String, Object> bonus(Bonus bon) {
		Map<String, Object> bonus = new HashMap<>();
		bonus.put("id", bon.getId());
		bonus.put("amount", bon.getAmount());
		bonus.put("date", bon.getDate());
		bonus.put("deliveryMan", "[id: " + String.valueOf(bon.getDeliveryMan().getIdUser()) + ", firtsname: "
				+ bon.getDeliveryMan().getFirstName() + ", lastname: " + bon.getDeliveryMan().getLastName() + "]");
		return bonus;
	}

	public Map<String, Object> reclamation(Reclamation rec) {
		Map<String, Object> reclamation = new HashMap<>();
		reclamation.put("id", rec.getId());
		reclamation.put("description", rec.getDescription());
		reclamation.put("created", rec.getCreated());
		reclamation.put("answered", rec.getAnswered());
		reclamation.put("decision", rec.getDecision());
		reclamation.put("state", rec.getState());
		reclamation.put("product", rec.getProduct().getNameProduct());
		reclamation.put("responseDescription", rec.getResponseDescription());
		reclamation.put("user", "[id: " + String.valueOf(rec.getUser().getIdUser()) + ", firtsname: "
				+ rec.getUser().getFirstName() + ", lastname: " + rec.getUser().getLastName() + "]");
		return reclamation;
	}

	public Map<String, Object> exchange(Exchange ex) {
		Map<String, Object> exchange = new HashMap<>();
		exchange.put("code", ex.getCode());
		exchange.put("created", ex.getCreated());
		exchange.put("doneOn", ex.getDoneOn());
		exchange.put("product", ex.getProduct().getNameProduct());
		return exchange;
	}

}
