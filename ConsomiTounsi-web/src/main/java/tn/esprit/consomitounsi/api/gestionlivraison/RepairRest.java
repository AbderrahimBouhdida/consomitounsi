package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.HashMap;
import java.util.List;
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

import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repair;
import tn.esprit.consomitounsi.sec.JWTTokenNeeded;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.ProductRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.ReclamationServiceRemote;

@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
@Path("gestionlivraison/Repairs")
public class RepairRest {

	@EJB
	ReclamationServiceRemote rsr;

	@EJB
	ProductRemote psr;

	@EJB
	IUserServicesRemote usr;

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRepair(Repair r) {

		r.setUser(usr.findUserById(r.getUser().getIdUser()));

		r.setProduct(psr.findProductById(r.getProduct().getBarecode()));
		rsr.addRepair(r);
		return Response.ok("repair added successfully").build();
	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllRepairs() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repairs(rsr.getAllRepairs())).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("all/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllExchangesByState(@PathParam(value = "state") boolean state) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repairs(rsr.getAllRepairByState(state))).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllExchangesByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repairs(rsr.getAllRepairByYear(year))).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("all/product/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllExchangesByProduct(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.repairs(rsr.getRepairByProductId(id))).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showRepairById(@PathParam(value = "id") int id) {
		IShowmethod show = new IShowmethod();
		if (rsr.repairExist(id)) {
			return Response.ok(show.repair(rsr.getRepairById(id))).build();
		}
		return Response.status(Status.NOT_FOUND).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@PUT
	@Path("{id}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRepair(@PathParam(value = "code") int id, Repair r) {
		if (rsr.repairExist(id)) {
			r.setUser(usr.findUserById(r.getUser().getIdUser()));
			r.setProduct(psr.findProductById(r.getProduct().getBarecode()));
			rsr.updateRepair(r);

			return Response.ok("repair updated successfully").build();
		}

		return Response.ok("check the entries").build();
	}

	@JWTTokenNeeded(roles = Roles.SAV)
	@PUT
	@Path("{id}/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateRepair(@PathParam(value = "id") int id, JsonObject obj) {

		try {
			double cost = obj.getJsonNumber("cost").doubleValue();
			String description = obj.getString("description");
			rsr.validateRepair(id, cost, description);

			return Response.ok("repair validated successfully").build();
		} catch (NullPointerException e) {
			return Response.ok("Please fill all fields [ cost, description]").build();
		}
	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@DELETE
	@Path("{id}/delete")
	public Response deleteRepair(@PathParam(value = "id") int id) {
		if (rsr.repairExist(id)) {
			rsr.deleteRepairById(id);

			return Response.ok("repair deleted successfully").build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("statistics/all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		statistics.put("Number of all repairs", rsr.numberOfRepairByYear(year));
		statistics.put("January", rsr.numberOfRepairPerMonth(year, 1));
		statistics.put("February", rsr.numberOfRepairPerMonth(year, 2));
		statistics.put("March", rsr.numberOfRepairPerMonth(year, 3));
		statistics.put("April", rsr.numberOfRepairPerMonth(year, 4));
		statistics.put("May", rsr.numberOfRepairPerMonth(year, 5));
		statistics.put("June", rsr.numberOfRepairPerMonth(year, 6));
		statistics.put("July", rsr.numberOfRepairPerMonth(year, 7));
		statistics.put("August", rsr.numberOfRepairPerMonth(year, 8));
		statistics.put("September", rsr.numberOfRepairPerMonth(year, 9));
		statistics.put("October", rsr.numberOfRepairPerMonth(year, 10));
		statistics.put("November", rsr.numberOfRepairPerMonth(year, 11));
		statistics.put("December", rsr.numberOfRepairPerMonth(year, 12));

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("statistics/products/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byProductPerYear(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {

			if (rsr.numberOfRepairByProductPerYear(prod.getBarecode(), year) != 0) {
				statistics.put(prod.getNameProduct(), rsr.numberOfRepairByProductPerYear(prod.getBarecode(), year));
			}

		}

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("statistics/products/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byProductPerMonth(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {
		Map<String, Long> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {
			if (rsr.numberOfRepairByProductPerMonth(prod.getBarecode(), year, month) != 0) {
				statistics.put(prod.getNameProduct(),
						rsr.numberOfRepairByProductPerMonth(prod.getBarecode(), year, month));
			}

		}

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("statistics/cost/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byCostPerYear(@PathParam(value = "year") int year) {
		double cost = 0.0d;
		List<Repair> repairs = rsr.getAllRepairByYear(year);
		for (Repair r : repairs) {
			cost += r.getCost();

		}

		return Response.ok(cost).build();
	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("statistics/cost/product/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response costbyProductPerYear(@PathParam(value = "year") int year) {
		Map<String, Double> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {
			if (rsr.costOfRepairByProductPerYear(prod.getBarecode(), year) != 0.0d) {

				statistics.put(prod.getNameProduct(), rsr.costOfRepairByProductPerYear(prod.getBarecode(), year));
			}

		}

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = {Roles.ADMIN,Roles.SAV})
	@GET
	@Path("statistics/cost/product/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response costbyProducPerMontht(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {
		Map<String, Double> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {
			if (rsr.costOfRepairByProductPerMonth(prod.getBarecode(), month, year) != 0.0d) {

				statistics.put(prod.getNameProduct(),
						rsr.costOfRepairByProductPerMonth(prod.getBarecode(), month, year));
			}

		}

		return Response.ok(statistics).build();

	}

}
