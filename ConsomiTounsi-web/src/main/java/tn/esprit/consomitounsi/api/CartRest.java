package tn.esprit.consomitounsi.api;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;



@Path("/cart")
@ApplicationScoped
public class CartRest {
	@EJB
	ICartServicesRemote cartservice;
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCart (Cart cr) {
		cartservice.addCart(cr);
		return Response.ok("added").build();
	}
	
	@Path("/getActive")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActiveCart(User user) {
		Cart cr = cartservice.findActiveCartByUserId(user);
		if(cr ==null)
			return Response.serverError().build();
		System.out.println(cr.getIdCart());
		return Response.ok(cr).build();
	}
	@Path("/addProd/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@PathParam(value = "id")int id,Product pid) {
		User us = new User();
		us.setIdUser(id);
		cartservice.addProdCart(us, pid);
		return Response.ok("added").build();
	}
	@Path("/get")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProds(User user) {
		List<Product> prods = cartservice.getCurrUserProds(user);
		System.out.println("heerrr"+prods.size());
		return Response.ok(prods).build();
	}
}
