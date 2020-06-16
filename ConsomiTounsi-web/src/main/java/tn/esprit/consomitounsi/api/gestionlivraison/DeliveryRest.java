package tn.esprit.consomitounsi.api.gestionlivraison;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Adress;
import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.entities.gestionlivraison.DeliveryState;
import tn.esprit.consomitounsi.sec.JWTTokenNeeded;
import tn.esprit.consomitounsi.sec.Session;
import tn.esprit.consomitounsi.services.intrf.IBillServiceRemote;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@JWTTokenNeeded(roles = { Roles.ADMIN, Roles.DELEVERYMAN, Roles.USER })
@Path("gestionlivraison/deliveries")
public class DeliveryRest {

	@EJB
	DeliveryServiceRemote dmsr;

	@EJB
	IUserServicesRemote userservice;

	@EJB
	ICartServicesRemote csr;
	
	@EJB
	IBillServiceRemote bsr;

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllDeliveries() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveries(dmsr.getAllDeliveries())).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeliveriesByState(@PathParam(value = "state") String state) {

		IShowmethod show = new IShowmethod();

		DeliveryState name = null;
		if (state.equalsIgnoreCase("pending")) {
			name = DeliveryState.PENDING;
		} else if (state.equalsIgnoreCase("registered")) {
			name = DeliveryState.REGISTERED;
		} else if (state.equalsIgnoreCase("done")) {
			name = DeliveryState.DONE;
		}

		return Response.ok(show.deliveries(dmsr.getAllDeliveriesByState(name))).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("registered/available/{base}/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response availableDeliveryMan(@PathParam(value = "base") String base, @PathParam(value = "day") String day) {

		IShowmethod show = new IShowmethod();
		return Response.ok(show.deliveryMen(dmsr.getDeliveryMenByBaseAndDay(base, day))).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@POST
	@Path("registered/assign/{idDelivery}/{idDeliveryMan}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignDelivery(@PathParam(value = "idDelivery") int deliveryId,
			@PathParam(value = "idDeliveryMan") int deliveryManId, String date) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dat = formatter.parse(date);
		dmsr.assignDeliveryToDeliveryMan(deliveryManId, deliveryId, dat);

		return Response.ok("delivery assigned succesfully to deliveryMan").build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("Statistics/delivery/year/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response statsDeliveryNUmberPerYear(@PathParam(value = "year") int year) {
		return Response.ok(dmsr.numberOfDeliveriesByYear(year)).build();
	}

	// client side
	@JWTTokenNeeded(roles = Roles.USER)
	@POST
	@Path("deliveryDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response showDeliveryDetail(@HeaderParam("Authorization") String token,  Adress address) {

		return Response.ok(dmsr.deliveryDetails(Session.getUserId(token), 0, address)).build();
		
	}

	@JWTTokenNeeded(roles = Roles.USER)
	@POST
	@Path("deliveryDetails/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDelivery(@HeaderParam("Authorization") String token, Adress address) {

		
		Delivery deliveryDetails = (Delivery) showDeliveryDetail(token, address).getEntity();

		Delivery delivery = new Delivery();
		
		Bill bill = new Bill();
		bill.setShipping(deliveryDetails.getBill().getShipping());
		
		int idBill = bsr.addBill(bill);
		delivery.setCost(deliveryDetails.getCost());
		delivery.setDescription(deliveryDetails.getDescription());
		delivery.setItems(deliveryDetails.getItems());

		dmsr.addDelivery(idBill,delivery);

		return Response.ok("delivery added successfully").build();
	}

}
