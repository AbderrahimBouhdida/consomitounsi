package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.CartProduct;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.entities.gestionlivraison.Address;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryState;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@Path("gestionlivraison/deliveries")
public class DeliveryRest {

	@EJB
	DeliveryServiceRemote dmsr;

	@EJB
	IUserServicesRemote userservice;

	@EJB
	ICartServicesRemote csr;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveries() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveries(dmsr.getAllDeliveries())).build();
	}

	@GET
	@Path("{state}")
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
	@Path("registered/available/{base}/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response availableDeliveryMan(@PathParam(value = "base") String base, @PathParam(value = "day") String day) {

		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByBaseAndDay(base, day))).build();
	}

	@POST
	@Path("registered/assign/{idDelivery}/{idDeliveryMan}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignDelivery(@PathParam(value = "idDelivery") int deliveryId,
			@PathParam(value = "idDeliveryMan") int deliveryManId) {
		dmsr.assignDeliveryToDeliveryMan(deliveryManId, deliveryId);

		return Response.ok("delivery assigned succesfully to deliveryMan").build();
	}

	@GET
	@Path("Statistics/delivery/year/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statsDeliveryNUmberPerYear(@PathParam(value = "year") int year) {
		return Response.ok(dmsr.NumberOfDeliveriesByYear(year)).build();
	}

	// client side

	@POST
	@Path("{userId}/deliveryDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response showDeliveryDetail(@PathParam(value = "userId") int userId, JsonObject obj) {

		Address address = new Address();

		address.setCity(obj.getString("city"));
		address.setName(obj.getString("name"));
		address.setNumber(obj.getString("number"));
		address.setRegion(obj.getString("region"));
		address.setSurname(obj.getString("surname"));

		int reduction = obj.getInt("reduction");

		Date date = dmsr.getCurrentDate();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		Calendar start = new GregorianCalendar();
		start.setTime(calendar.getTime());
		start.add(Calendar.DATE, 1);

		Calendar end = new GregorianCalendar();
		end.setTime(calendar.getTime());
		if (calendar.get(Calendar.DAY_OF_WEEK) <= 3) {

			end.add(Calendar.DATE, 3);
		} else {
			end.add(Calendar.DATE, 5);
		}

		String beginning = start.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " "
				+ start.get(Calendar.DAY_OF_MONTH) + " "
				+ start.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

		String endDate = end.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " "
				+ end.get(Calendar.DAY_OF_MONTH) + " "
				+ end.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

		// total weight
		double totalWeight = 0.0d;
		User us = userservice.findUserById(userId);
		List<CartProduct> items = csr.findActiveCartByUserId(us).getProducts();
		List<String> itemsList = new ArrayList<String>();
		for (CartProduct item : items) {
			totalWeight += (item.getProduct().getWeight()) * item.getQuantity();
			itemsList.add(item.getQuantity() + "x " + item.getProduct().getNameProduct() + " "
					+ item.getProduct().getWeight() + "kg " + " ");
		}

		double cost = dmsr.shippingCost(address.getRegion(), totalWeight, reduction);

		String description = "Shipment of " + items.size() + " products : " + " " + itemsList + " "
				+ "Delivered between " + beginning + " and " + endDate + " " + " COST : " + cost + "DT";

		Delivery delivery = new Delivery();
		delivery.setAddressInformation(address);
		delivery.setCost(cost);
		delivery.setDescription(description);

		return Response.ok(delivery).build();
	}

	@POST
	@Path("{userId}/deliveryDetails/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDelivery(@PathParam(value = "userId") int userId, JsonObject obj) {

		Delivery deliveryDetails = (Delivery) showDeliveryDetail(userId, obj).getEntity();

		Delivery delivery = new Delivery();

		delivery.setAddressInformation(deliveryDetails.getAddressInformation());
		delivery.setCost(deliveryDetails.getCost());
		delivery.setDescription(deliveryDetails.getDescription());

		dmsr.addDelivery(delivery);

		return Response.ok("delivery added successfully").build();
	}
	
	

}
