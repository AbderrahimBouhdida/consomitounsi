package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.MarkrResponse;
import tn.esprit.consomitounsi.entities.Response;
import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.services.intrf.ResponseServicesRemote;




@LocalBean
@Stateless
public class ResponseService implements ResponseServicesRemote {


	SubjectService sub;
	
	@PersistenceContext
	EntityManager em;
	
	
	@Override
	public int AddResponse(Response response) {
		em.persist(response);
		return response.getIdResponse();	
		}

	@Override
	public void EditResponse(Response response) {
		 em.merge(response);
	}
	
	@Override
	public void DeleteResponse(Subject subj,int idresponse) {
		
		Subject tmp = subj;
		System.out.println("ok2");

		List<Response> tmpList = tmp.getResponses();
		List<Response> tmpList1 = new ArrayList<Response>();
		System.out.println("ok3");
		for (Response response : tmpList) {
			if(response.getIdResponse() != idresponse) {
					
			tmpList1.add(response);}
		}
		System.out.println("ok4");
		subj.setResponses(tmpList1);
		System.out.println(tmpList1);
		em.merge(subj);
		em.remove(em.find(Response.class, idresponse));
        System.out.println("Response"+idresponse+" is deleted");		
	}		
	
	
	@Override
	public List<Response> getAllResponsesBySubjectId(int idSubject) {
		
		Subject subject = new Subject();
		subject.setIdsubject(idSubject);
		TypedQuery<Response> query = 
				em.createQuery("select r from response r WHERE r.subject=:subjectid ", Response.class); 
				query.setParameter("subjectid", subject); 
				List<Response> responses  = new ArrayList<>();
				try {
					responses = query.getResultList(); 
				}
				catch (Exception e) {
					System.out.println("Erreur : " + e);
				}
				return responses;
	
	}
	
	@Override
	public int countResponseOfSubject(int idSubject) {
		Subject sub = new Subject();
		sub = em.find(Subject.class, idSubject);
		
		List<MarkrResponse> ListVoteResp  = new ArrayList<>();
		TypedQuery<MarkrResponse> query = 
				em.createQuery("select r from Response r WHERE r.subject=:subjectid", MarkrResponse.class);
		query.setParameter("subjectid", sub);
		ListVoteResp = query.getResultList();
		
		if(ListVoteResp.size()>0) {
			return ListVoteResp.size();
		}
	
		return 0;
	}

	

	@Override
	public Response ChercherReponse( int id) {
		
		 Response response = em.find(Response.class, id);
			return response;

	}

/*	@Override
	public int VerifResponse(User user, Response response) {
		// TODO Auto-generated method stub
		return 0;
	}*/

	

	@Override
	public void DeleteResponsesOfSubject(int idSubject) {
		
		
		List<Response> responses  = new ArrayList<>();
		Subject sub = new Subject();
		sub = em.find(Subject.class, idSubject);
		
		try {
		TypedQuery<Response> query = 
				em.createQuery("select r from Response r WHERE r.subject=:subjectid ", Response.class); 
				query.setParameter("subjectid", sub); 
				responses = query.getResultList(); 

					for (Response r : responses) {
						em.remove(r);
					}
				}
		
				catch (Exception e) {
					System.out.println("Erreur : " + e);
				}
		
	}

	@Override
	public List<Response> getResponsesOfAllSubs() {
		
        List<Response> resps = em.createQuery("select r from Response r", Response.class).getResultList();

		
		return resps;
	}
	
public List<Response> getlatestResponses() {
		
	
		TypedQuery<Response> query = 
				em.createQuery("select r from Response r ORDER BY dateResponse DESC", Response.class); 
		
				List<Response> Responses  = new ArrayList<>();
				try {
					Responses = query.getResultList(); 
				}
				catch (Exception e) {
					System.out.println("Erreur : " + e);
				}
				System.out.println("get all subject by cat " +Responses);

				return Responses;
				
	}


}
