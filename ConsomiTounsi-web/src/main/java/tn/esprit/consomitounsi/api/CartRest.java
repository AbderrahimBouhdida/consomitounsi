package tn.esprit.consomitounsi.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
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

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.CartProduct;
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
	public Response addProduct(@PathParam(value = "id")int id,CartProduct pid) {
		User us = new User();
		boolean added;
		us.setIdUser(id);
		added = cartservice.addProdCart(us, pid);
		if(added)
			return Response.ok("added").build();
		return Response.ok("Item exists").build();
	}
	@Path("/get")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProds(User user) {
		List<CartProduct> prods = cartservice.getCurrUserProds(user);
		System.out.println("heerrr"+prods.size());
		return Response.ok(prods).build();
	}
	@Path("/del/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeProduct(@PathParam(value = "id")int id,CartProduct pid) {
		User us = new User();
		boolean removed;
		us.setIdUser(id);
		removed = cartservice.removeProd(us, pid);
		if(removed)
			return Response.ok("removed").build();
		return Response.ok("Item doesn't exists").build();
	}
	@Path("/mod/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modProduct(@PathParam(value = "id")int id,CartProduct pid) {
		User us = new User();
		boolean edited;
		us.setIdUser(id);
		edited = cartservice.modProd(us, pid);
		if(edited)
			return Response.ok("edited").build();
		return Response.ok("Item doesn't exists").build();
	}
	
	@Path("/email")
	@POST
	public Response testEmail() {
		try {
			cartservice.testEmail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok("sent").build();
	}
}
