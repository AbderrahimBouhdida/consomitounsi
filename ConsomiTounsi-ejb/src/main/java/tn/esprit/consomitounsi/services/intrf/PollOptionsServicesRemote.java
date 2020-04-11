package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface PollOptionsServicesRemote {
	public int addVoteById(String pollOptionId);
}