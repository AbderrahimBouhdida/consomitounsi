package tn.esprit.consomitounsi.services.intrf;



import javax.ejb.Remote;



@Remote
public interface PollOptionsServicesRemote {
	public int addVoteById(String pollOptionId);
}