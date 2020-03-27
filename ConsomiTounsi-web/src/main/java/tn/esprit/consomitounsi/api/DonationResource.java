package tn.esprit.consomitounsi.api;

//import java.util.ArrayList;
//import java.util.List;

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
//import javax.ws.rs.core.Response.Status;


import tn.esprit.consomitounsi.entities.Donation;
import tn.esprit.consomitounsi.services.intrf.DonationServiceRemote;



@Path("/Donation")
public class DonationResource {
	
	@EJB
    DonationServiceRemote dons;
	
	
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adddonation(Donation don) {
    	dons.addDonation(don);
        return Response.ok(dons.findAllDonation()).build();
    }
    
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editdonation(Donation don) {
    	dons.updateDonation(don);
        return Response.ok(dons.findDonationById(don.getIddon())).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }
    
    
    @DELETE
    @Path("/del/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletedonationby(@PathParam(value ="id") String id) {
    	dons.removeDonation(Integer.parseInt(id));
    	return Response.ok(dons.findAllDonation()).build();
    }
    
    @POST
    @Path("/donby/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response finddonationbyid(@PathParam(value ="id") String id) {
        return Response.ok(dons.findDonationById(Integer.parseInt(id))).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }
    
    @GET
    @Path("/alldonations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findalldonations() {
    	return Response.ok(dons.findAllDonation()).build();
    	
    }
    
    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addCart(Cart cart) {
//        return null;
        
    }