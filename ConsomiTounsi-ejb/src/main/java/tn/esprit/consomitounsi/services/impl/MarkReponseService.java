package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.MarkrResponse;
import tn.esprit.consomitounsi.entities.Response;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.MarkReponseRemote;


@LocalBean
@Stateless
public class MarkReponseService implements MarkReponseRemote {

	@PersistenceContext
	EntityManager em;

	
	@Override
	public void likeResponse(int idUser, int idResponse) {
		
		boolean test = VerifierVoteReponse(idUser,idResponse);
		int count = countVote(idResponse) + 1;

		if(test==false) {
			
			try {
		MarkrResponse markrep = new MarkrResponse();
		User user = em.find(User.class, idUser);
		Response resp = em.find(Response.class, idResponse);
		
		markrep.setUser(user);
		markrep.setReponse(resp);
		em.persist(markrep);
		
		System.out.println("new vote reponse ! ");
		System.out.println(count);

			}
		
		catch (NoResultException e) {
			System.out.println("fama moshkla ! ");
	
			}
			
		}
	}
	


	@Override
	public void dislikeResponse(int idUser, int idResponse) {
		
		int count = countVote(idResponse) - 1 ;
		
		MarkrResponse ListMarkResp = null;
		User userr = new User();
		userr.setIdUser(idUser);
		Response resp = new Response();
		resp.setIdResponse(idResponse);
		
		TypedQuery<MarkrResponse> query = 
				em.createQuery("select mr from MarkrResponse mr WHERE mr.user=:userid and mr.reponse=:responseid", MarkrResponse.class); 
				query.setParameter("userid", userr);
				query.setParameter("responseid", resp);
				
			try {
				ListMarkResp = query.getSingleResult();
				em.remove(ListMarkResp);
				
				System.out.println("Delete vote response Successful ! ");
				System.out.println(count);
			}
		
		catch (NoResultException e) {
			System.out.println("fama moshkla ! ");
	
			}
			
		
	}


	
	@Override
	public int countVote(int idResponse) {
		
		  Response resp = new Response();
			resp = em.find(Response.class, idResponse);
			
			List<MarkrResponse> ListVoteResp  = new ArrayList<>();
			TypedQuery<MarkrResponse> query = 
					em.createQuery("select vr from MarkrResponse vr WHERE vr.reponse=:responseid", MarkrResponse.class);
			query.setParameter("responseid", resp);
			ListVoteResp = query.getResultList();
			
			if(ListVoteResp.size()>0) {
				return ListVoteResp.size();
			}
		
		return 0;
	}


	
	@Override
	public boolean VerifierVoteReponse(int idUser, int idResponse) {
		
		User user = new User();
		user.setIdUser(idUser);
		
		Response response = new Response();
		response.setIdResponse(idResponse);

		List<MarkrResponse> ListVote  = new ArrayList<>();
		TypedQuery<MarkrResponse> query = 
				em.createQuery("select mr from MarkrResponse mr WHERE mr.user=:userid and mr.reponse=:responseid", MarkrResponse.class); 
				query.setParameter("userid", user);
				query.setParameter("responseid", response);
				ListVote = query.getResultList();
				
				if(ListVote.size()>0) {

					return true; 
				}
				
				else {
					return false; 
				}

		

	}



	

	
	
	
	
}
