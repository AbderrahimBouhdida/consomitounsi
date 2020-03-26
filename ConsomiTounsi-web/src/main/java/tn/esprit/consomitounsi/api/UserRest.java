package tn.esprit.consomitounsi.api;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.sec.LoginToken;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;



@Path("/login")
@RequestScoped
public class UserRest {
	@EJB
	IUserServicesRemote users;
	@EJB
	ICartServicesRemote carts;
	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adduser(User us) {
		users.addUser(us);
		return Response.ok(users.findAllUsers()).build();
	}
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doLogin(User user) {
		System.out.println(user.getUsername());
		User us = users.login(user.getUsername(), user.getPassword());
		if(us == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		System.out.println(!carts.isCartAvailaible(us));
		if(!carts.isCartAvailaible(us)) {
			Cart cr = new Cart(us,true);
			carts.addCart(cr);
			System.out.println("cart created !");
		}
		String token = LoginToken.createJWT("ConsomiTounsi", user.getUsername(), 0);
		return Response.ok(us).header("AUTHORIZATION", "Bearer " + token).build();	
	}
	
	
}
