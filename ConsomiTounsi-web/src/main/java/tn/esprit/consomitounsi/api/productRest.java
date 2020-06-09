package tn.esprit.consomitounsi.api;


import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.services.impl.ProductService;
import tn.esprit.consomitounsi.services.intrf.ProductRemote;



@Path("/prod")
@SessionScoped
public class productRest {
	@EJB
	ProductRemote product;
	@PersistenceContext
	EntityManager em;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct()
	{
	return Response.ok(product.findAllProduct()).build();
	}
	/*@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addProduct(Product products) {
		return Response.ok(this.product.addProduct(products)).build();
	}
		*/
		
		@POST
	    @Path("/add")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addProduct2(Product products) {
			product.addProduct(products);
	        return Response.ok(product.findAllProduct()).build();
	    }

	
	@DELETE
	@Path("{Barecode}")
	public Response deleteP(@PathParam(value = "Barecode") int Barecode) {
		product.removeProduct(Barecode);
		return Response.status(Status.OK).entity("Product deleted").build();
	}
//	
//	@POST
//    @Path("/edit")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response editproduct(Product prod) {
//    	product.updateProduct(prod);
//        return Response.ok(product.findProductById(prod.getBarecode())).build();
//    	//return Response.ok(cols.findAllCollection()).build();
//    }

}