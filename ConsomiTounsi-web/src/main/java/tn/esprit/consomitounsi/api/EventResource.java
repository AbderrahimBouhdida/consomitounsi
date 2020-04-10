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


import tn.esprit.consomitounsi.entities.Event;
import tn.esprit.consomitounsi.services.intrf.EventServiceRemote;



@Path("/Event")
public class EventResource {
	
	@EJB
	EventServiceRemote events;
	
	
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addevent(Event eve) {
    	events.addEvent(eve);
        return Response.ok(events.findAllEvent()).build();
    }
    
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editevent(Event eve) {
    	events.updateEvent(eve);
        return Response.ok(events.findEventById(eve.getIdevent())).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }
    
    @PUT
    @Path("/editv2")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editeventv2(Event eve) {
    	events.updateEventv2(eve);
        return Response.ok(events.findEventById(eve.getIdevent())).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }
    
    
    @DELETE
    @Path("/del/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteeventby(@PathParam(value ="id") String id) {
    	events.removeEvent(Integer.parseInt(id));
    	return Response.ok(events.findAllEvent()).build();
    }
    
    @POST
    @Path("/eventby/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findeventbyid(@PathParam(value ="id") String id) {
        return Response.ok(events.findEventById(Integer.parseInt(id))).build();
    	//return Response.ok(cols.findAllCollection()).build();
    }
    
    @GET
    @Path("/allevents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findallevents() {
    	return Response.ok(events.findAllEvent()).build();
    	
    }
    
    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addCart(Cart cart) {
//        return null;
        
    }