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

import tn.esprit.consomitounsi.entities.Ads;
import tn.esprit.consomitounsi.services.intrf.AdsRemote;



@Path("Ads")
@RequestScoped
public class AdsRessource {
	@EJB
	AdsRemote ads;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAds()
	{
	return Response.ok(ads.findAllAds()).build();
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addAds(Ads ades) {
		return Response.ok(this.ads.addAds(ades)).build();

	}
	
	@DELETE
	@Path("{idAds}")
	public Response deleteAds(@PathParam(value = "idAds") int idAds) {
		
		 
		ads.removeAds(idAds);
		return Response.status(Status.OK).entity("ad deleted").build();
			

	}
	@POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editproductads(Ads a) {
    	ads.updateAds(a);
        return Response.ok(ads.findAdsById(a.getIdAds())).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }

	
}
