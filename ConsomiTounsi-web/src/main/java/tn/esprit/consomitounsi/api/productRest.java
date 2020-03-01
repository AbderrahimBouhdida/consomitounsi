package tn.esprit.consomitounsi.api;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.services.impl.ProductService;



@Path("/prod")
@SessionScoped
public class productRest {
	@EJB
	ProductService products;
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProd(Product prod) {
		products.addProduct(prod);
		return Response.ok("addeed").build();
	}
}
