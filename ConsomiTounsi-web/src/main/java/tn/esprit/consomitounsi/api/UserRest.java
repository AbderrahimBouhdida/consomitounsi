package tn.esprit.consomitounsi.api;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.sec.JWTTokenNeeded;
import tn.esprit.consomitounsi.sec.LoginToken;
import tn.esprit.consomitounsi.sec.Session;
import tn.esprit.consomitounsi.services.intrf.ICartServicesRemote;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;

@Path("/user")
@RequestScoped
public class UserRest {
	@EJB
	IUserServicesRemote users;
	@EJB
	ICartServicesRemote carts;

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adduser(User us) {
		if (users.userExist(us))
			return Response.status(Response.Status.BAD_REQUEST).entity("User exist").build();
		users.addUser(us);
		return Response.ok(us).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doLogin(User user) {
		System.out.println(user.getUsername());
		User us = users.login(user.getUsername(), user.getPassword());
		if (us == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		if (!us.isValid())
			return Response.ok("please verify your Email First!").build();
		System.out.println(!carts.isCartAvailaible(us));
		if (!carts.isCartAvailaible(us)) {
			Cart cr = new Cart();
			cr.setUser(us);
			cr.setCurrent(true);
			carts.addCart(cr);
			System.out.println("cart created !");
		}
		String token = LoginToken.createJWT("ConsomiTounsi",us.getIdUser(), us.getUsername(),us.getRole(), 0);
		return Response.ok(us).header("AUTHORIZATION", "Bearer " + token).build();	
	}
	@PUT
	@Path("/update")
	@JWTTokenNeeded
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modInfo(@HeaderParam("Authorization") String token, User user) {
		int curentId=Session.getUserId(token);
		System.out.println("real id = "+curentId);
		user.setIdUser(curentId);
		users.updateUser(user);
		return Response.ok(user).build();
	}
	@PUT
	@Path("/update/admin")
	@JWTTokenNeeded(roles = {Roles.ADMIN})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modInfoAdmin(User user) {
		users.updateUser(user);
		return Response.ok(user).build();
	}
	@PUT
	@Path("/delete/admin")
	@JWTTokenNeeded(roles = {Roles.ADMIN})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delUserAdmin(User user) {
		users.removeUser(user.getIdUser());
		return Response.ok("deleted").build();
	}
	@PUT
	@Path("/delete")
	@JWTTokenNeeded
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delUser(@HeaderParam("Authorization") String token) {
		int curentId=Session.getUserId(token);
		users.removeUser(curentId);
		return Response.ok("deleted").build();
	}
	@GET
	@Path("/verify")
	public Response verifyEmail(@QueryParam(value = "token") String token) {
		if (token == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		if (users.verifyEmail(token))
			return Response.ok("Verified").build();
		return Response.ok("Already verified").build();
	}
	@JWTTokenNeeded(roles = {Roles.MANAGER})
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		return Response.ok(users.findAllUsers()).build();
	}
	
}
