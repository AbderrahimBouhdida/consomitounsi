package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.HashMap;
import java.util.Map;

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
import javax.ws.rs.core.Response.Status;

import tn.esprit.consomitounsi.entities.gestionlivraison.Repayment;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.ReclamationServiceRemote;

@Path("gestionlivraison/Repayments")
public class RepaymentRest {
	@EJB
	ReclamationServiceRemote rsr;

	@EJB
	IUserServicesRemote usr;

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRepayments(Repayment rep) {

		rep.setUser(usr.findUserById(rep.getUser().getIdUser()));
		rsr.addRepayment(rep);
		return Response.ok("repayment added successfully").build();
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllRepayments() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repayments(rsr.getAllRepayment())).build();

	}

	@GET
	@Path("all/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllRepaymentsByState(@PathParam(value = "state") boolean state) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repayments(rsr.getAllRepaymentByState(state))).build();

	}

	@GET
	@Path("all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllRepaymentsByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repayments(rsr.getAllRepaymentByYear(year))).build();

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showRepaymentById(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();
		if (rsr.RepaymentExist(id)) {
			return Response.ok(show.repayment(rsr.getRepaymentById(id))).build();
		}
		return Response.status(Status.NOT_FOUND).build();

	}

	@PUT
	@Path("{id}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRepayment(@PathParam(value = "id") int id, Repayment r) {
		if (rsr.RepaymentExist(id)) {
			r.setUser(usr.findUserById(r.getUser().getIdUser()));
			rsr.updateRepayment(r);

			return Response.ok("repayment updated successfully").build();
		}

		return Response.ok("check the entries").build();
	}

	@PUT
	@Path("{id}/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateRepayment(@PathParam(value = "id") int id, JsonObject obj) {

		try {
			String description = obj.getString("description");
			rsr.validateRepayment(id, description);
			return Response.ok("repayment validated successfully").build();
		} catch (NullPointerException e) {
			return Response.ok("Please fill all fields [ description]").build();
		}

	}

	@DELETE
	@Path("{id}/delete")
	public Response deleteRepayment(@PathParam(value = "id") int id) {
		if (rsr.RepaymentExist(id)) {
			rsr.deleteRepaymentById(id);

			return Response.ok("repayment deleted successfully").build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@GET
	@Path("statistics/all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		statistics.put("Number of all repayments", rsr.numberOfRepaymentPerYear(year));
		statistics.put("January", rsr.numberOfRepaymentPerMonth(1, year));
		statistics.put("February", rsr.numberOfRepaymentPerMonth(2, year));
		statistics.put("March", rsr.numberOfRepaymentPerMonth(3, year));
		statistics.put("April", rsr.numberOfRepaymentPerMonth(4, year));
		statistics.put("May", rsr.numberOfRepaymentPerMonth(5, year));
		statistics.put("June", rsr.numberOfRepaymentPerMonth(6, year));
		statistics.put("July", rsr.numberOfRepaymentPerMonth(7, year));
		statistics.put("August", rsr.numberOfRepaymentPerMonth(8, year));
		statistics.put("September", rsr.numberOfRepaymentPerMonth(9, year));
		statistics.put("October", rsr.numberOfRepaymentPerMonth(10, year));
		statistics.put("November", rsr.numberOfRepaymentPerMonth(11, year));
		statistics.put("December", rsr.numberOfRepaymentPerMonth(12, year));

		return Response.ok(statistics).build();

	}

}
