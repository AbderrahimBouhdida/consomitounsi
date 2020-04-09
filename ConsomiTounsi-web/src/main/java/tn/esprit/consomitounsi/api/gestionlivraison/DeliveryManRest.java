package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
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
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@Path("gestionlivraison/deliveryMen")
public class DeliveryManRest {

	@EJB
	DeliveryServiceRemote dmsr;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMen() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getAllDeliveryMen())).build();
	}

	@GET
	@Path("base/{base}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMenByBase(@PathParam(value = "base") String base) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByBase(base))).build();

	}

	@GET
	@Path("availabilities/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMenByAvailabilities(@PathParam(value = "day") String day) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByDay(day))).build();

	}

	@POST
	@Path("add")
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

		return Response.ok("deliveryMan added succesfully ").build();
	}

	@DELETE
	@Path("deliveryMan/{id}/delete")
	public Response deleteDeliveryMan(@PathParam(value = "id") int deliveryManId) {
		DeliveryMan del = dmsr.getDeliveryManById(deliveryManId);

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
	public Response giveBonusToDeliveryMan(@PathParam(value = "id") int id, JsonObject bon) {

		Bonus bonus = new Bonus();

		bonus.setDate(dmsr.getCurrentDate());

		bonus.setAmount(bon.getJsonNumber("amount").doubleValue());
		dmsr.addBonus(id, bonus);
		return Response.ok("Bonus added successfully to deliveryMan").build();

	}

	@GET
	@Path("bonus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonus() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.bonuss(dmsr.getAllBonus())).build();
	}

	@GET
	@Path("bonus/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonusByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.bonuss(dmsr.getAllBonusByYear(year))).build();
	}

	@GET
	@Path("deliveryMan/{id}/bonus/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonusByDeliveryMan(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();
		List<Bonus> bonus = new ArrayList<>();
		bonus.addAll(dmsr.getAllBonusByDeliveryManId(id));
		return Response.ok(show.bonuss(bonus)).build();
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
	@Path("Statistics/deliveryMen/deliveries/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statisticsD(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {

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

	@GET
	@Path("{id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showJobList(@PathParam(value = "id") int deliveryManId) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getDeliveriesTaskByDeliveryManId(deliveryManId))).build();

	}

	@GET
	@Path("{id}/deliveries/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showDeliveries(@PathParam(value = "id") int deliveryManId, @PathParam(value = "year") int year) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getAccomplishedDeliveryByDeliveryManIdPerYear(deliveryManId, year)))
				.build();
	}

	@GET
	@Path("{id}/deliveries/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showDeliveriesper(@PathParam(value = "id") int deliveryManId, @PathParam(value = "year") int year,
			@PathParam(value = "month") int month) {

		IShowmethod show = new IShowmethod();

		return Response
				.ok(show.deliveries(
						dmsr.getAccomplishedDeliveryByDeliveryManIdPerYearAndMonth(deliveryManId, year, month)))
				.build();
	}

	@PUT
	@Path("{id}/tasks/{idDelivery}/done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeDeliveryState(@PathParam(value = "idDelivery") int deliveryId) {
		dmsr.validateDelivery(deliveryId);
		return Response.ok("delivery state changed to done ").build();

	}

	@GET
	@Path("{id}/profile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showProfile(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveryMan(dmsr.getDeliveryManById(id))).build();

	}

	@PUT
	@Path("{id}/Profile/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam(value = "id") int id, JsonObject obj) {

		DeliveryMan deli = new DeliveryMan();
		deli.setIdUser(id);
		try {
			deli.setTransportation(obj.getString("transportation"));
		} catch (NullPointerException e) {
			deli.setTransportation("");

		}
		try {
			deli.setAddress(obj.getString("address"));
		} catch (NullPointerException e) {
			deli.setAddress("");

		}
		try {
			deli.setAvailabilities(obj.getString("availabilities"));
		} catch (NullPointerException e) {
			deli.setAvailabilities("");

		}
		try {
			deli.setPassword(obj.getString("password"));
		} catch (NullPointerException e) {
			deli.setPassword("");

		}
		try {
			deli.setPhone(obj.getString("phone"));
		} catch (NullPointerException e) {
			deli.setPhone("");

		}

		try {
			deli.setBase(obj.getString("base"));
		} catch (NullPointerException e) {
			deli.setBase("");

		}

		dmsr.updateDeliveryMan(deli);
		return Response.ok("deliveryMan updated successfully").build();

	}

	@GET
	@Path("{id}/Statistics/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statistics(@PathParam(value = "id") int id, @PathParam(value = "year") int year) {

		Map<String, Long> statistics = new HashMap<>();
		statistics.put("Number of done deliveries", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerYear(id, year));
		statistics.put("January", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 1, year));
		statistics.put("February", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 2, year));
		statistics.put("March", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 3, year));
		statistics.put("April", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 4, year));
		statistics.put("May", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 5, year));
		statistics.put("June", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 6, year));
		statistics.put("July", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 7, year));
		statistics.put("August", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 8, year));
		statistics.put("September", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 9, year));
		statistics.put("October", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 10, year));
		statistics.put("November", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 11, year));
		statistics.put("December", dmsr.getNumberOfAccomplishedDeliveryByDeliveryManPerMonth(id, 12, year));

		return Response.ok(statistics).build();

	}

}
