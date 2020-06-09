package tn.esprit.consomitounsi.services.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Spam;
import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.SpamSubjectRemote;



@LocalBean
@Stateless
public class SpamService implements SpamSubjectRemote {

	@PersistenceContext
	EntityManager em;

	@Override
	public void AddSpamSubject(int idSubject,int idUser) {
		
		
		boolean test = VerifSpam(idUser, idSubject);
		int count = CountSpamSujet(idSubject);
		
		if(test==false) {
			
		try {
			Spam newSpam = new Spam();
			User user = em.find(User.class, idUser);
			Subject sub = em.find(Subject.class, idSubject);
			newSpam.setUser(user);
			newSpam.setSubject(sub);
			em.persist(newSpam);
			System.out.println("new Spam ! ");
			System.out.println(count);
			if(count>=2) {
				EmailService email = new EmailService();
				String body ="TEST AKRAM";
				try {
					System.out.println("sending email ");
					email.sendEmail("service@consomitounsi.tn", "spam", sub.getUser().getEmail(), body);
					System.out.println("new Spam ! ");

				}
				catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		catch (NoResultException e) {
			System.out.println("fama moshkla fel mail  ! ");
		}
		
		}
	}
	
		
	
	
	
	
	

	@Override
	public int CountSpamSujet(int idSubject) {
		
		 
		    Subject subject = new Subject();
			subject = em.find(Subject.class, idSubject);
		 
			List<Spam> ListSpamSub  = new ArrayList<>();
			TypedQuery<Spam> query = 
					em.createQuery("select sp from Spam sp WHERE sp.subject=:subjectid", Spam.class);
			query.setParameter("subjectid", subject);
			ListSpamSub = query.getResultList();
			
			if(ListSpamSub.size()>0) {
				return ListSpamSub.size();
			}
			
		
		return 0;
	}
	
	
	@Override
	public boolean VerifSpam(int idUser, int idSubject) {

		User user = new User();
		user.setIdUser(idUser);
		Subject subject = new Subject();
		subject.setIdsubject(idSubject);
		
		List<Spam> ListSpamSub  = new ArrayList<>();
		TypedQuery<Spam> query = 
				em.createQuery("select sp from Spam sp WHERE sp.user=:userid and sp.subject=:subjectid", Spam.class);
		query.setParameter("userid", user);
		query.setParameter("subjectid", subject);
		ListSpamSub = query.getResultList();
		

		if(ListSpamSub.size()>0) {

			return true; 
		}
		
		else {
			return false; 
		}

		
	}
	
	
}
