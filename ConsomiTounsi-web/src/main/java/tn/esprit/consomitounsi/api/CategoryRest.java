package tn.esprit.consomitounsi.api;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.services.intrf.CategoryRemote;



@Path("/category")
public class CategoryRest {
	@EJB
	CategoryRemote cats;
	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCategory(Category cat) {
		cats.addCategory(cat);
		return Response.ok("added").build();
	}
}
