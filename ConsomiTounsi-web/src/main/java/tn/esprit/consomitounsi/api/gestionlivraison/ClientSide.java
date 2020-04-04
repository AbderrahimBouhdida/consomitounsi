package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.CartProduct;
import tn.esprit.consomitounsi.entities.gestionlivraison.Address;
import tn.esprit.consomitounsi.entities.gestionlivraison.Delivery;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.DeliveryServiceRemote;

@Path("gestionlivraison/client/{userId}")
@RequestScoped
public class ClientSide {

	@EJB
	DeliveryServiceRemote dmsr;

	@EJB
	ICartServicesRemote csr;

	@EJB
	IUserServicesRemote userservice;

	@POST
	@Path("cart")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCart(@PathParam(value = "userId") int userId, Cart cart) {

		cart.setCurrent(true);
		cart.setUser(userservice.findUserById(userId));

		csr.addCart(cart);
		return Response.ok("cart added sucessfully").build();
	}

	@POST
	@Path("cart/items/{idProd}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@PathParam(value = "userId") int idUser, @PathParam(value = "idProd") int idProd,
			JsonObject obj) {
		int qty = obj.getInt("qty");
		csr.addProdCart(idUser, idProd, qty);
		return Response.ok("item added successfully").build();
	}

	@POST
	@Path("deliveryDetails")
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
		List<CartProduct> items = csr.findActiveCartByUserId(userId).getProducts();
		List<String> itemsList = new ArrayList<String>();
		for (CartProduct item : items) {
			totalWeight += (item.getProduct().getWeight()) * item.getQuantity();
			itemsList.add(item.getQuantity() + "x " + item.getProduct().getNameProduct() + " " + item.getProduct().getWeight()
					+ "kg " + " ");
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
	@Path("deliveryDetails/add")
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
