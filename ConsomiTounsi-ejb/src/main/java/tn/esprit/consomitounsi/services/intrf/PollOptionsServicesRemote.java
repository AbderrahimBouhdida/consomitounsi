package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Poll;
import tn.esprit.consomitounsi.entities.PollOption;
import tn.esprit.consomitounsi.entities.Product;

@Remote
public interface PollOptionsServicesRemote {
	public int addVoteById(String pollOptionId);
	public void updatePoll(Poll pollNewValues);
}