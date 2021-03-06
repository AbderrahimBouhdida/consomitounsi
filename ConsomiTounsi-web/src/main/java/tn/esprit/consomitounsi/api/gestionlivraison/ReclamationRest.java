package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.entities.gestionlivraison.Decision;
import tn.esprit.consomitounsi.entities.gestionlivraison.Reclamation;
import tn.esprit.consomitounsi.entities.gestionlivraison.ReclamationState;
import tn.esprit.consomitounsi.sec.JWTTokenNeeded;
import tn.esprit.consomitounsi.sec.Session;
import tn.esprit.consomitounsi.services.intrf.CategoryRemote;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;
import tn.esprit.consomitounsi.services.intrf.ProductRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.ReclamationServiceRemote;

@JWTTokenNeeded(roles = {Roles.ADMIN, Roles.USER})
@Path("gestionlivraison/Reclamations")
public class ReclamationRest {

	@EJB
	ReclamationServiceRemote rsr;

	@EJB
	IUserServicesRemote usr;

	@EJB
	ProductRemote psr;

	@EJB
	CategoryRemote csr;

	private static final String ACCEPTED = "accepted";
	private static final String REJECTED = "rejected";
	private static final String PENDING = "pending";
	private static final String REPAIR = "repair";
	private static final String REPAYMENT = "repayment";
	private static final String EXCHANGE = "exchange";
	private static final String DECISION = "decision";

//	@JWTTokenNeeded(roles = Roles.USER)
//	@POST
//	@Path("add")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response addReclamation(@HeaderParam("Authorization") String token, Reclamation reclamation) {
//
//		User us = usr.findUserById(Session.getUserId(token));
//		reclamation.setUser(us);
//		reclamation.setProduct(psr.findProductById(reclamation.getProduct().getBarecode()));
//		rsr.addReclamation(reclamation);
//		return Response.ok("reclamation added successfully").build();
//	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllReclamations() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.reclamations(rsr.getAllReclamations())).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllReclamationsByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.reclamations(rsr.getAllReclamationsByYear(year))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllReclamationsByMonth(@PathParam(value = "year") int year,
			@PathParam(value = "month") int month) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.reclamations(rsr.getAllReclamationsByMonth(year, month))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllReclamationsByUser(@PathParam(value = "userId") int id) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.reclamations(rsr.getReclamationByUserId(id))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all/state/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllReclamationsByState(@PathParam(value = "state") String stat) {
		IShowmethod show = new IShowmethod();
		ReclamationState state = null;
		if (stat.equalsIgnoreCase(ACCEPTED)) {
			state = ReclamationState.ACCEPTED;
		} else if (stat.equalsIgnoreCase(REJECTED)) {
			state = ReclamationState.REJECTED;
		} else if (stat.equalsIgnoreCase(PENDING)) {
			state = ReclamationState.PENDING;
		}
		return Response.ok(show.reclamations(rsr.getReclamationByState(state))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("all/decision/{decision}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllReclamationsByDecision(@PathParam(value = "decision") String dec) {
		IShowmethod show = new IShowmethod();
		Decision decision = null;
		if (dec.equalsIgnoreCase(REPAIR)) {
			decision = Decision.REPAIR;
		} else if (dec.equalsIgnoreCase(REPAYMENT)) {
			decision = Decision.REPAYMENT;
		} else if (dec.equalsIgnoreCase(EXCHANGE)) {
			decision = Decision.EXCHANGE;
		}
		return Response.ok(show.reclamations(rsr.getReclamationByDecision(decision))).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@PUT
	@Path("response/{reclamationId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response esponse(@PathParam(value = "reclamationId") int id, JsonObject obj) {

		if (rsr.reclamationExist(id)) {
			ReclamationState state = null;
			if (obj.getString("state").equalsIgnoreCase(ACCEPTED)) {
				state = ReclamationState.ACCEPTED;
			} else if (obj.getString("state").equalsIgnoreCase(REJECTED)) {
				state = ReclamationState.REJECTED;
			}

			Decision decision = null;
			if (obj.getString(DECISION).equalsIgnoreCase(REPAIR)) {
				decision = Decision.REPAIR;
			} else if (obj.getString(DECISION).equalsIgnoreCase(REPAYMENT)) {
				decision = Decision.REPAYMENT;
			} else if (obj.getString(DECISION).equalsIgnoreCase(EXCHANGE)) {
				decision = Decision.EXCHANGE;
			}

			Reclamation reclamation = new Reclamation();

			reclamation.setId(id);
			try {
				reclamation.setDecision(decision);
				reclamation.setState(state);
				reclamation.setResponseDescription(obj.getString("responseDescription"));
				rsr.updateReclamation(reclamation);
				return Response.ok("response added successfully").build();
			} catch (Exception e) {

				return Response.ok("Veuillez remplir tous les champs [ decision, state, responseDescription]").build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@DELETE
	@Path("delete/{id}")
	public Response deleteReclamation(@PathParam(value = "id") int id) {
		if (rsr.reclamationExist(id)) {
			rsr.deleteReclamationById(id);
			return Response.ok("reclamation deleted successfully").build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		statistics.put("Number of all reclamations ", rsr.numberOfReclamationByYear(year));
		statistics.put("January ", rsr.numberOfReclamationPerMonth(1, year));
		statistics.put("February ", rsr.numberOfReclamationPerMonth(2, year));
		statistics.put("March ", rsr.numberOfReclamationPerMonth(3, year));
		statistics.put("April ", rsr.numberOfReclamationPerMonth(4, year));
		statistics.put("May", rsr.numberOfReclamationPerMonth(5, year));
		statistics.put("June", rsr.numberOfReclamationPerMonth(6, year));
		statistics.put("July", rsr.numberOfReclamationPerMonth(7, year));
		statistics.put("August ", rsr.numberOfReclamationPerMonth(8, year));
		statistics.put("September ", rsr.numberOfReclamationPerMonth(9, year));
		statistics.put("October ", rsr.numberOfReclamationPerMonth(10, year));
		statistics.put("November ", rsr.numberOfReclamationPerMonth(11, year));
		statistics.put("December ", rsr.numberOfReclamationPerMonth(12, year));

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/state/{state}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response state(@PathParam(value = "year") int year, @PathParam(value = "state") String stat) {
		Map<String, Long> statistics = new HashMap<>();
		ReclamationState state = null;
		if (stat.equalsIgnoreCase(ACCEPTED)) {
			state = ReclamationState.ACCEPTED;
		} else if (stat.equalsIgnoreCase(REJECTED)) {
			state = ReclamationState.REJECTED;
		} else if (stat.equalsIgnoreCase(PENDING)) {
			state = ReclamationState.PENDING;
		}
		statistics.put("Number of all reclamations", rsr.numberOfReclamationByStatePerYear(state, year));
		statistics.put("January", rsr.numberOfReclamationByStatePerMonth(state, 1, year));
		statistics.put("February", rsr.numberOfReclamationByStatePerMonth(state, 2, year));
		statistics.put("March", rsr.numberOfReclamationByStatePerMonth(state, 3, year));
		statistics.put("April", rsr.numberOfReclamationByStatePerMonth(state, 4, year));
		statistics.put("May", rsr.numberOfReclamationByStatePerMonth(state, 5, year));
		statistics.put("June", rsr.numberOfReclamationByStatePerMonth(state, 6, year));
		statistics.put("July", rsr.numberOfReclamationByStatePerMonth(state, 7, year));
		statistics.put("August", rsr.numberOfReclamationByStatePerMonth(state, 8, year));
		statistics.put("September", rsr.numberOfReclamationByStatePerMonth(state, 9, year));
		statistics.put("October", rsr.numberOfReclamationByStatePerMonth(state, 10, year));
		statistics.put("November", rsr.numberOfReclamationByStatePerMonth(state, 11, year));
		statistics.put("December", rsr.numberOfReclamationByStatePerMonth(state, 12, year));

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/decision/{decision}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response decision(@PathParam(value = "year") int year, @PathParam(value = "decision") String dec) {
		Map<String, Long> statistics = new HashMap<>();
		Decision decision = null;
		if (dec.equalsIgnoreCase(REPAIR)) {
			decision = Decision.REPAIR;
		} else if (dec.equalsIgnoreCase(REPAYMENT)) {
			decision = Decision.REPAYMENT;
		} else if (dec.equalsIgnoreCase(EXCHANGE)) {
			decision = Decision.EXCHANGE;
		}
		statistics.put("Number of all reclamations", rsr.numberOfReclamationByDecisionByYear(decision, year));
		statistics.put("January", rsr.numberOfReclamationByDecisionPerMonth(decision, 1, year));
		statistics.put("February", rsr.numberOfReclamationByDecisionPerMonth(decision, 2, year));
		statistics.put("March", rsr.numberOfReclamationByDecisionPerMonth(decision, 3, year));
		statistics.put("April", rsr.numberOfReclamationByDecisionPerMonth(decision, 4, year));
		statistics.put("May", rsr.numberOfReclamationByDecisionPerMonth(decision, 5, year));
		statistics.put("June", rsr.numberOfReclamationByDecisionPerMonth(decision, 6, year));
		statistics.put("July", rsr.numberOfReclamationByDecisionPerMonth(decision, 7, year));
		statistics.put("August", rsr.numberOfReclamationByDecisionPerMonth(decision, 8, year));
		statistics.put("September", rsr.numberOfReclamationByDecisionPerMonth(decision, 9, year));
		statistics.put("October", rsr.numberOfReclamationByDecisionPerMonth(decision, 10, year));
		statistics.put("November", rsr.numberOfReclamationByDecisionPerMonth(decision, 11, year));
		statistics.put("December", rsr.numberOfReclamationByDecisionPerMonth(decision, 12, year));

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/products/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byProductPerYear(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {

			if (rsr.numberOfReclamationByProductByYear(prod.getBarecode(), year) != 0) {
				statistics.put(prod.getNameProduct(), rsr.numberOfReclamationByProductByYear(prod.getBarecode(), year));
			}

		}

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/products/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byProductPerMonth(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {
		Map<String, Long> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {
			if (rsr.numberOfReclamationByProductPerMonth(prod.getBarecode(), month, year) != 0) {
				statistics.put(prod.getNameProduct(),
						rsr.numberOfReclamationByProductPerMonth(prod.getBarecode(), month, year));
			}

		}

		return Response.ok(statistics).build();

	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/categories/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byCategoriesPerYear(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		List<Category> categories = csr.findAllCategory();
		for (Category cat : categories) {

			if (rsr.numberOfReclamationByCategoryByYear(cat.getIdCategory(), year) != 0) {
				statistics.put(cat.getNom(), rsr.numberOfReclamationByCategoryByYear(cat.getIdCategory(), year));
			}

		}

		return Response.ok(statistics).build();
	}

	@JWTTokenNeeded(roles = Roles.ADMIN)
	@GET
	@Path("statistics/categories/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byCategoriesPerMonth(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {
		Map<String, Long> statistics = new HashMap<>();
		List<Category> categories = csr.findAllCategory();
		for (Category cat : categories) {

			if (rsr.numberOfReclamationByCategoryPerMonth(cat.getIdCategory(), month, year) != 0) {
				statistics.put(cat.getNom(),
						rsr.numberOfReclamationByCategoryPerMonth(cat.getIdCategory(), month, year));
			}

		}

		return Response.ok(statistics).build();
	}

}
