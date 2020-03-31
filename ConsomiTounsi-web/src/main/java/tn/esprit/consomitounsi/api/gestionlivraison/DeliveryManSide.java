package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryMan;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@Path("gestionlivraison/deliveryman/{id}")
@RequestScoped
public class DeliveryManSide {

	@EJB
	DeliveryServiceRemote dmsr;

	@GET
	@Path("tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showJobList(@PathParam(value = "id") int deliveryManId) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getDeliveriesTaskByDeliveryManId(deliveryManId))).build();

	}

	@GET
	@Path("deliveries/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showDeliveries(@PathParam(value = "id") int deliveryManId, @PathParam(value = "year") int year) {

		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveries(dmsr.getAccomplishedDeliveryByDeliveryManIdPerYear(deliveryManId, year)))
				.build();
	}

	@GET
	@Path("deliveries/{year}/{month}")
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
	@Path("tasks/{idDelivery}/done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeDeliveryState(@PathParam(value = "idDelivery") int deliveryId) {
		dmsr.validateDelivery(deliveryId);
		return Response.ok("delivery state changed to done ").build();

	}

	@GET
	@Path("profile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showProfile(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();

		return Response.ok(show.deliveryMan(dmsr.getDeliveryManById(id))).build();

	}

	@PUT
	@Path("Profile/update")
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

	@GET
	@Path("Statistics/{year}")
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
