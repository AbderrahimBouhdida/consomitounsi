package tn.esprit.consomitounsi.api;


import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.consomitounsi.entities.Poll;
import tn.esprit.consomitounsi.services.intrf.PollOptionsServicesRemote;
import tn.esprit.consomitounsi.services.intrf.PollServicesRemote;


@Path("/poll")
@SessionScoped
public class PollRest {
	@EJB
	PollServicesRemote psserv;
	@EJB
	PollOptionsServicesRemote os;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPoll(Poll poll) {
		
		psserv.addPoll(poll);

		return Response.ok(poll).build();
	}
	
	@GET
	@Path("{poll_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPoll(@PathParam("poll_id") String pollId) {

		Poll poll = psserv.findPollById(pollId);
		
		if (poll == null) {
			throw new NotFoundException("Poll with ID " + pollId + " not found");
		}

		return Response.ok(poll).build();

	}
	
	@GET
	@Path("{poll_id}/{vote_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doVote(@PathParam("poll_id") String pollId, @PathParam("vote_id") String voteId) {

		Poll poll = psserv.findPollById(pollId);
		
		if (poll == null) {
			throw new NotFoundException("Poll with ID " + pollId + " not found");
		}
		int votes = os.addVoteById(String.valueOf(voteId));
		poll.getOptions().forEach(opt -> {
			if(opt.getId()==Integer.valueOf(voteId))
				opt.setVotes(votes);
		});
		return Response.ok(poll).build();

	}
	
}

