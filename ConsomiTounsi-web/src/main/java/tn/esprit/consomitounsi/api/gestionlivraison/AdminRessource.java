package tn.esprit.consomitounsi.api.gestionlivraison;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.gestionlivraison.Bonus;
import tn.esprit.consomitounsi.entities.gestionlivraison.Contract;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryState;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@Path("gestionlivraison/admin")
@RequestScoped
public class AdminRessource {

	@EJB
	DeliveryServiceRemote dmsr;

	// deliveryMan Side
	@GET
	@Path("deliveryMen")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMen() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getAllDeliveryMen())).build();
	}

	@GET
	@Path("deliveryMen/base/{base}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMenByBase(@PathParam(value = "base") String base) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByBase(base))).build();

	}

	@GET
	@Path("deliveryMen/availabilities/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMenByAvailabilities(@PathParam(value = "day") String day) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByDay(day))).build();

	}

	@POST
	@Path("deliveryMen/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDeliveryMan(JsonObject obj) {
		// addDeliveryMan
		DeliveryMan del = new DeliveryMan();
		del.setFirstName(obj.getString("firstName"));
		del.setLastName(obj.getString("lastName"));
		del.setBase(obj.getString("base"));

		del.setDob(dmsr.getCurrentDate());

		del.setEmail(obj.getString("email"));
		del.setImg(obj.getString("img"));
		del.setPassword(obj.getString("password"));
		del.setPhone(obj.getString("phone"));
		del.setRole(obj.getInt("role"));

		del.setTransportation(obj.getString("transportation"));

		del.setUsername(obj.getString("username"));
		del.setAddress(obj.getString("address"));
		int deliveryManId = dmsr.addDeliveryMan(del);

		// add contract

		Contract cont = new Contract();
		cont.setStartOn(dmsr.getCurrentDate());
		cont.setSalary(obj.getInt("salary"));
		cont.setDescription(obj.getString("description"));
		dmsr.addContract(deliveryManId, cont);

		return Response.ok("deliveryMan added succesfully with his contract").build();
	}

	@PUT
	@Path("deliveryMan/{id}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam(value = "id") int id, JsonObject obj) {

		DeliveryMan deli = new DeliveryMan();
		deli.setIdUser(id);
		try {
			deli.setTransportation(obj.getString("transportation"));
		} catch (NullPointerException e) {
			deli.setTransportation("");
			;
		}
		try {
			deli.setAddress(obj.getString("address"));
		} catch (NullPointerException e) {
			deli.setAddress("");
			;
		}
		try {
			deli.setAvailabilities(obj.getString("availabilities"));
		} catch (NullPointerException e) {
			deli.setAvailabilities("");
			;
		}
		try {
			deli.setPassword(obj.getString("password"));
		} catch (NullPointerException e) {
			deli.setPassword("");
			;
		}
		try {
			deli.setPhone(obj.getString("phone"));
		} catch (NullPointerException e) {
			deli.setPhone("");
			;
		}

		try {
			deli.setBase(obj.getString("base"));
		} catch (NullPointerException e) {
			deli.setBase("");
			;
		}

		dmsr.updateDeliveryMan(deli);
		return Response.ok("deliveryMan updated successfully").build();

	}

	@DELETE
	@Path("deliveryMan/{id}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteDeliveryMan(@PathParam(value = "id") int deliveryManId) {
		DeliveryMan del = dmsr.getDeliveryManById(deliveryManId);

		// delete deliveryMan contract
		dmsr.deleteContractById(del.getContract().getReference());

		// delete all deliveryMan Bonus
		Set<Bonus> bonus = del.getBonus();
		for (Bonus bon : bonus) {
			dmsr.deleteBonusById(bon.getId());
		}

		// delete all deliveryMan deliveries

		Set<Delivery> deliveries = del.getDeliveries();
		for (Delivery deli : deliveries) {
			dmsr.deleteDeliveryById(deli.getId());
		}

		// delete deliveryMan

		dmsr.deleteDeliveryManById(deliveryManId);
		return Response.ok("deliveryMan deleted successfully").build();
	}

	@POST
	@Path("deliveryMan/{id}/bonus/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response giveBonusToDeliveryMan(@PathParam(value = "id") int id, JsonObject bon) throws ParseException {

		Bonus bonus = new Bonus();

		bonus.setDate(dmsr.getCurrentDate());

		bonus.setAmount(bon.getJsonNumber("amount").doubleValue());
		dmsr.addBonus(id, bonus);
		return Response.ok("Bonus added successfully to deliveryMan").build();

	}

	@GET
	@Path("deliveryMen/bonus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonus() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.Bonuss(dmsr.getAllBonus())).build();
	}

	@GET
	@Path("deliveryMen/bonus/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonusByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.Bonuss(dmsr.getAllBonusByYear(year))).build();
	}

	@GET
	@Path("deliveryMan/{id}/bonus/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonusByDeliveryMan(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();
		List<Bonus> bonus = new ArrayList<>();
		bonus.addAll(dmsr.getAllBonusByDeliveryManId(id));
		return Response.ok(show.Bonuss(bonus)).build();
	}

	// Delivery Side

	@GET
	@Path("deliveries")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveries() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveries(dmsr.getAllDeliveries())).build();
	}

	@GET
	@Path("deliveries/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeliveriesByState(@PathParam(value = "state") String state) {

		IShowmethod show = new IShowmethod();

		DeliveryState name = null;
		if (state.toLowerCase().equals("pending")) {
			name = DeliveryState.Pending;
		} else if (state.toLowerCase().equals("registered")) {
			name = DeliveryState.Registered;
		} else if (state.toLowerCase().equals("done")) {
			name = DeliveryState.Done;
		}

		return Response.ok(show.deliveries(dmsr.getAllDeliveriesByState(name))).build();
	}

	@GET
	@Path("deliveries/registered/available/{base}/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response availableDeliveryMan(@PathParam(value = "base") String base, @PathParam(value = "day") String day) {

		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByBaseAndDay(base, day))).build();
	}

	@POST
	@Path("deliveries/registered/assign/{idDelivery}/{idDeliveryMan}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignDelivery(@PathParam(value = "idDelivery") int deliveryId,
			@PathParam(value = "idDeliveryMan") int deliveryManId) {
		dmsr.assignDeliveryToDeliveryMan(deliveryManId, deliveryId);

		return Response.ok("delivery assigned succesfully to deliveryMan").build();
	}

	@GET
	@Path("Statistics/deliveryMen/base")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statsDeliveryMenByBase() {
		Map<String, Integer> number = new HashMap<>();
		number.put("Tunis", dmsr.getDeliveryMenByBase("Tunis").size());
		number.put("Ariana", dmsr.getDeliveryMenByBase("Ariana").size());
		number.put("Other", dmsr.getDeliveryMenByBase("Other").size());
		return Response.ok(number).build();
	}

	@GET
	@Path("Statistics/deliveryMen/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statsDeliveryMenNUmber() {
		return Response.ok(dmsr.numberOfActiveDeliveryMen()).build();
	}

	@GET
	@Path("Statistics/delivery/year/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statsDeliveryNUmberPerYear(@PathParam(value = "year") int year) {
		return Response.ok(dmsr.NumberOfDeliveriesByYear(year)).build();
	}

	@GET
	@Path("Statistics/deliveryMen/deliveries/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statistics(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {

		List<DeliveryMan> deliveryMen = dmsr.getAllDeliveryMen();
		Map<String, Integer> results = new HashMap<>();

		for (DeliveryMan del : deliveryMen) {
			String name = " " + del.getIdUser() + " " + del.getFirstName() + " " + del.getLastName() + " ";
			int number = dmsr.getAccomplishedDeliveryByDeliveryManIdPerYearAndMonth(del.getIdUser(), year, month)
					.size();

			results.put(name, number);
		}

		return Response.ok(results).build();
	}

}
