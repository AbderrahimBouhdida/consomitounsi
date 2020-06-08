package tn.esprit.consomitounsi.services.impl;



import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Poll;
import tn.esprit.consomitounsi.entities.PollOption;
import tn.esprit.consomitounsi.services.intrf.PollServicesRemote;



@Stateless
@LocalBean
public class PollServices implements PollServicesRemote {
	@PersistenceContext
	EntityManager em;
	@Override
	public void addPoll(Poll poll) {
		
		em.persist(poll);
		
		
	}
	@Override
	public void addOptions(Poll pol, List<PollOption> options) {
		pol.setOptions(options);
	}

	@Override
	public Poll findPollById(String pollId) {
		Poll poll = em.find(Poll.class, Integer.valueOf(pollId));
		return poll;
	}
	@Override
	public List<Poll> findAllPol() {
		List<Poll> poll = em.createQuery("from Poll", Poll.class).getResultList();
		
        return poll;
	}
	
	@Override
	public void removePoll(int id) {
		// TODO Auto-generated method stub
		em.remove(em.find(Poll.class, id));
	     System.out.println("poll deleted");
		
	}
	@Override
	public void updatePoll(Poll pollNewValues) {
		// TODO Auto-generated method stub
		Poll poll = em.find(Poll.class, pollNewValues.getId());
		poll.setQuestion(pollNewValues.getQuestion());
		poll.setCreatedAt(pollNewValues.getCreatedAt());
		poll.setOptions(pollNewValues.getOptions());
		
	}
	

}