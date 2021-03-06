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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.gestionlivraison.Bonus;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.sec.JWTTokenNeeded;
import tn.esprit.consomitounsi.sec.Session;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@JWTTokenNeeded(roles = { Roles.ADMIN, Roles.DELEVERYMAN })
@Path("gestionlivraison/deliveryMen")
public class DeliveryManRest {

	@EJB
	DeliveryServiceRemote dmsr;
	@EJB
	IUserServicesRemote users;

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMen() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getAllDeliveryMen())).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("base/{base}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMenByBase(@PathParam(value = "base") String base) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByBase(base))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("availabilities/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveryMenByAvailabilities(@PathParam(value = "day") String day) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByDay(day))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDeliveryMan(DeliveryMan del) {

		InputValidation input = new InputValidation();

		if (input.isEmail(del.getEmail()) && input.isPassword(del.getPassword())
				&& input.isPhoneNumber(del.getPhone())) {
			if (users.userExist(del)) {
				return Response.status(Response.Status.CONFLICT)
						.entity("User already exist. Check the username or the email").build();
			}
			del.setDob(dmsr.getCurrentDate());

			del.setRole(Roles.DELEVERYMAN);

			dmsr.addDeliveryMan(del);
			return Response.ok("deliveryMan added succesfully ").build();
		}

		return Response.ok("please fill correctly all the fiels").status(Status.NOT_ACCEPTABLE).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@DELETE
	@Path("{id}/delete")
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

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@POST
	@Path("{id}/bonus/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response giveBonusToDeliveryMan(@PathParam(value = "id") int id, JsonObject bon) {

		Bonus bonus = new Bonus();

		bonus.setDate(dmsr.getCurrentDate());

		if (bon.getJsonNumber("amount").doubleValue() < 0) {
			Response.ok("PLease enter a correct amount").status(Status.NOT_ACCEPTABLE).build();
		}
		bonus.setAmount(bon.getJsonNumber("amount").doubleValue());
		dmsr.addBonus(id, bonus);
		return Response.ok("Bonus added successfully to deliveryMan").build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("bonus/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonus() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.bonuss(dmsr.getAllBonus())).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("bonus/all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonusByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.bonuss(dmsr.getAllBonusByYear(year))).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("bonus/list/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllBonusByDeliveryMan(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();
		List<Bonus> bonus = new ArrayList<>();
		bonus.addAll(dmsr.getAllBonusByDeliveryManId(id));
		return Response.ok(show.bonuss(bonus)).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
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

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("Statistics/deliveryMen/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statsDeliveryMenNUmber() {
		return Response.ok(dmsr.numberOfActiveDeliveryMen()).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
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

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("{id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showJobListD(@PathParam(value = "id") int deliveryManId) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getDeliveriesTaskByDeliveryManId(deliveryManId))).build();

	}

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@GET
	@Path("tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showJobList(@PathParam(value = "id") int deliveryManId) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getDeliveriesTaskByDeliveryManId(deliveryManId))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("{id}/deliveries/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showDeliveries(@PathParam(value = "id") int deliveryManId, @PathParam(value = "year") int year) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getAccomplishedDeliveryByDeliveryManIdPerYear(deliveryManId, year)))
				.build();
	}

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@GET
	@Path("deliveries/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showDeliveriesD(@HeaderParam("Authorization") String token, @PathParam(value = "year") int year) {

		IShowmethod show = new IShowmethod();

		return Response
				.ok(show.deliveries(dmsr.getAccomplishedDeliveryByDeliveryManIdPerYear(Session.getUserId(token), year)))
				.build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
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

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@GET
	@Path("deliveries/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showDeliveriesperD(@HeaderParam("Authorization") String token, @PathParam(value = "year") int year,
			@PathParam(value = "month") int month) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(
				dmsr.getAccomplishedDeliveryByDeliveryManIdPerYearAndMonth(Session.getUserId(token), year, month)))
				.build();
	}

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@PUT
	@Path("tasks/{idDelivery}/done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeDeliveryStateD(@HeaderParam("Authorization") String token,
			@PathParam(value = "idDelivery") int deliveryId) {
		Delivery del = dmsr.getDeliveryById(deliveryId);
		if (del.getDeliveryMan().getIdUser() == Session.getUserId(token)) {
			dmsr.validateDelivery(deliveryId);
			return Response.ok("delivery state changed to done ").build();
		}
		return Response.status(Status.FORBIDDEN).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("{id}/profile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showProfile(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveryMan(dmsr.getDeliveryManById(id))).build();

	}

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@GET
	@Path("profile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showProfiled(@HeaderParam("Authorization") String token) {
		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveryMan(dmsr.getDeliveryManById(Session.getUserId(token)))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@PUT
	@Path("{id}/Profile/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam(value = "id") int id, DeliveryMan del) {

		del.setIdUser(id);
		if (del.getPassword() != null) {
			InputValidation input = new InputValidation();
			if (!input.isPassword(del.getPassword())) {
				return Response.ok("enter a correct password").status(Status.NOT_ACCEPTABLE).build();
			}
		}

		dmsr.updateDeliveryMan(del);
		return Response.ok("deliveryMan updated successfully").build();

	}

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@PUT
	@Path("profile/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProfileD(@HeaderParam("Authorization") String token, DeliveryMan del) {

		del.setIdUser(Session.getUserId(token));

		if (del.getPassword() != null) {
			InputValidation input = new InputValidation();
			if (!input.isPassword(del.getPassword())) {
				return Response.ok("enter a correct password").status(Status.NOT_ACCEPTABLE).build();
			}
		}
		dmsr.updateDeliveryMan(del);
		return Response.ok("deliveryMan updated successfully").build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("{id}/Statistics/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statisticsh(@PathParam(value = "id") int id, @PathParam(value = "year") int year) {

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

	@JWTTokenNeeded(roles = Roles.DELEVERYMAN)
	@GET
	@Path("Statistics/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statisticsD(@HeaderParam("Authorization") String token, @PathParam(value = "year") int year) {

		int id = Session.getUserId(token);
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
