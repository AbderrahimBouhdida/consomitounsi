package tn.esprit.consomitounsi.api.gestionlivraison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
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

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.gestionlivraison.Exchange;
import tn.esprit.consomitounsi.services.intrf.CategoryRemote;
import tn.esprit.consomitounsi.services.intrf.ProductRemote;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.ReclamationServiceRemote;

@Path("gestionlivraison/Exchanges")
public class ExchangeRest {

	@EJB
	ReclamationServiceRemote rsr;

	@EJB
	ProductRemote psr;

	@EJB
	CategoryRemote csr;

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addExchange(Exchange exchange) {

		exchange.setProduct(psr.findProductById(exchange.getProduct().getBarecode()));

		rsr.addExchange(exchange);
		return Response.ok("exchange added successfully").build();
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllExchanges() {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.exchanges(rsr.getAllExchanges())).build();

	}

	@GET
	@Path("all/state/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllExchangesByState(@PathParam(value = "state") boolean state) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.exchanges(rsr.getAllExchangesByState(state))).build();

	}

	@GET
	@Path("all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAllExchangesByYear(@PathParam(value = "year") int year) {
		IShowmethod show = new IShowmethod();
		return Response.ok(show.exchanges(rsr.getAllExchangesByYear(year))).build();

	}

	@GET
	@Path("{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showExchangesByCode(@PathParam(value = "code") String code) {
		IShowmethod show = new IShowmethod();
		if (rsr.exchangeExist(code.toUpperCase())) {
			return Response.ok(show.exchange(rsr.getExchangeByCode(code.toUpperCase()))).build();
		}
		return Response.status(Status.NOT_FOUND).build();

	}

	@GET
	@Path("{code}/check")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkCode(@PathParam(value = "code") String code) {
		return Response.ok(rsr.exchangeExist(code.toUpperCase())).build();

	}

	@PUT
	@Path("{code}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateExchange(@PathParam(value = "code") String code, Exchange ex) {
		if (rsr.exchangeExist(ex.getCode().toUpperCase())) {
			ex.setProduct(psr.findProductById(ex.getProduct().getBarecode()));
			rsr.updateExchange(ex);
			return Response.ok("exchange update successfully").build();
		}

		return Response.ok("check the entries").build();
	}

	@PUT
	@Path("{code}/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateExchange(@PathParam(value = "code") String code) {

		if(rsr.validateExchange(code)) {
			return Response.ok("validation Done").build();
		}
		
		return Response.ok("validation failed").build();
	}

	@DELETE
	@Path("{code}/delete")
	public Response deleteExchange(@PathParam(value = "code") String code) {
		if (rsr.checkExchangeCode(code.toUpperCase())) {
			rsr.deleteExchangeByCode(code);

			return Response.ok("exchange deleted successfully").build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@GET
	@Path("statistics/all/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		statistics.put("Number of all exchanges", rsr.numberOfExchangeByYear(year));
		statistics.put("January", rsr.numberOfExchangePerMonth(year, 1));
		statistics.put("February", rsr.numberOfExchangePerMonth(year, 2));
		statistics.put("March", rsr.numberOfExchangePerMonth(year, 3));
		statistics.put("April", rsr.numberOfExchangePerMonth(year, 4));
		statistics.put("May", rsr.numberOfExchangePerMonth(year, 5));
		statistics.put("June", rsr.numberOfExchangePerMonth(year, 6));
		statistics.put("July", rsr.numberOfExchangePerMonth(year, 7));
		statistics.put("August", rsr.numberOfExchangePerMonth(year, 8));
		statistics.put("September", rsr.numberOfExchangePerMonth(year, 9));
		statistics.put("October", rsr.numberOfExchangePerMonth(year, 10));
		statistics.put("November", rsr.numberOfExchangePerMonth(year, 11));
		statistics.put("December", rsr.numberOfExchangePerMonth(year, 12));

		return Response.ok(statistics).build();

	}

	@GET
	@Path("statistics/products/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byProductPerYear(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {

			if (rsr.numberOfExchangeByProductByYear(prod.getBarecode(), year) != 0) {
				statistics.put(prod.getNameProduct(), rsr.numberOfExchangeByProductByYear(prod.getBarecode(), year));
			}

		}

		return Response.ok(statistics).build();

	}

	@GET
	@Path("statistics/products/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byProductPerMonth(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {
		Map<String, Long> statistics = new HashMap<>();
		List<Product> products = psr.findAllProduct();
		for (Product prod : products) {
			if (rsr.numberOfExchangeByProductPerMonth(prod.getBarecode(), year, month) != 0) {
				statistics.put(prod.getNameProduct(),
						rsr.numberOfExchangeByProductPerMonth(prod.getBarecode(), year, month));
			}

		}

		return Response.ok(statistics).build();

	}

	@GET
	@Path("statistics/categories/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byCategoriesPerYear(@PathParam(value = "year") int year) {
		Map<String, Long> statistics = new HashMap<>();
		List<Category> categories = csr.findAllCategory();
		for (Category cat : categories) {

			if (rsr.numberOfExchangeByCategoryByYear(cat.getIdCategory(), year) != 0) {
				statistics.put(cat.getNom(), rsr.numberOfExchangeByCategoryByYear(cat.getIdCategory(), year));
			}

		}

		return Response.ok(statistics).build();
	}

	@GET
	@Path("statistics/categories/{year}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byCategoriesPerMonth(@PathParam(value = "year") int year, @PathParam(value = "month") int month) {
		Map<String, Long> statistics = new HashMap<>();
		List<Category> categories = csr.findAllCategory();
		for (Category cat : categories) {

			if (rsr.numberOfExchangeByCategoryPerMonth(cat.getIdCategory(), month, year) != 0) {
				statistics.put(cat.getNom(), rsr.numberOfExchangeByCategoryPerMonth(cat.getIdCategory(), month, year));
			}

		}

		return Response.ok(statistics).build();
	}

}
