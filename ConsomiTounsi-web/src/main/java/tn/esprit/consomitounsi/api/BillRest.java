package tn.esprit.consomitounsi.api;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tn.esprit.consomitounsi.entities.Bill;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.BillServices;

@Path("bill")
public class BillRest {
	@EJB
	BillServices bs;
	
	@POST
	@Path("/buy")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBill(Bill bill) {
		bs.addBill(bill);
		return Response.ok("added").build();
	}
	
	@GET
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBills(User user) {
		return Response.ok(bs.findBillsByUser(user)).build();
	}
}
