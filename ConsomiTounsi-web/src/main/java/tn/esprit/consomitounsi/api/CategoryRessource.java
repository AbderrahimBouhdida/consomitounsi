package tn.esprit.consomitounsi.api;



import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.services.intrf.CategoryRemote;




@Path("Category")
@RequestScoped
public class CategoryRessource {
	@EJB
	CategoryRemote category;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategory()
	{
	return Response.ok(category.findAllCategory()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addCategory(Category categories) {
		
		return Response.ok(this.category.addCategory(categories)).build();

	}
	
	@DELETE
	@Path("{idCategory}")
	public Response deleteCategory(@PathParam(value = "idCategory") int idCategory) {
		 
		category.removeCategory(idCategory);
		return Response.status(Status.OK).entity("Category deleted").build();
	}

	@POST
    @Path("/editt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editcategory(Category cat) {
    	category.updateCategory(cat);
        return Response.ok(category.findCategoryById(cat.getIdCategory())).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }

}
