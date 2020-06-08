package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Poll;
import tn.esprit.consomitounsi.entities.PollOption;


@Remote
public interface PollServicesRemote {
	public void addPoll(Poll poll);
	public Poll findPollById(String pollId);
	public void removePoll(int id);
	public void updatePoll(Poll pollNewValues);
	public void addOptions(Poll pol, List<PollOption> options);
	 public List<Poll> findAllPol();
}