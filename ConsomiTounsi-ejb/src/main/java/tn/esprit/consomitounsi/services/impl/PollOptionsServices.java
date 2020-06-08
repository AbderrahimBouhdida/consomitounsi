package tn.esprit.consomitounsi.services.impl;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Poll;
import tn.esprit.consomitounsi.entities.PollOption;
import tn.esprit.consomitounsi.services.intrf.PollOptionsServicesRemote;


@Stateless
@LocalBean
public class PollOptionsServices implements PollOptionsServicesRemote{
	@PersistenceContext
	EntityManager em;

	@Override
	public int addVoteById(String pollOptionId) {
		PollOption po = em.find(PollOption.class, Integer.valueOf(pollOptionId));
		po.setVotes(po.getVotes()+1);
		return po.getVotes();
	}

	@Override
	public void updatePoll(Poll pollNewValues) {
		// TODO Auto-generated method stub
		
	}
}