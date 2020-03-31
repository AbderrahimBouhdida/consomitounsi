package tn.esprit.consomitounsi.api.gestionlivraison;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.esprit.consomitounsi.entities.gestionlivraison.Bonus;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;

public class IShowmethod {

	public List<Map<String, Object>> deliveries(List<Delivery> deliveries) {
		List<Map<String, Object>> results = new ArrayList<>();
		for (Delivery del : deliveries) {

			results.add(delivery(del));
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
						? String.valueOf(del.getDeliveryMan().getIdUser()) + " " + del.getDeliveryMan().getFirstName()
								+ " " + del.getDeliveryMan().getLastName()
						: "");

		return obj;

	}

	public Map<String, Object> bonus(Bonus bon) {
		Map<String, Object> bonus = new HashMap<>();
		bonus.put("id", bon.getId());
		bonus.put("amount", bon.getAmount());
		bonus.put("date", bon.getDate());
		bonus.put("deliveryMan", String.valueOf(bon.getDeliveryMan().getIdUser()) + " "
				+ bon.getDeliveryMan().getFirstName() + " " + bon.getDeliveryMan().getLastName());
		return bonus;
	}

}
