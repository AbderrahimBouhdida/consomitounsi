package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.MarkSubject;
import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.MarkSubjectRemote;


@LocalBean
@Stateless
public class MarkSubjectService implements MarkSubjectRemote {


	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<MarkSubject> getMostPopularSubjects() {
		TypedQuery<MarkSubject> query = em.createQuery("SELECT subject,Count(ms.user) from MarkSubject ms GROUP BY ms.subject ORDER BY Count(ms.user) DESC",MarkSubject.class);
		
		List<MarkSubject> listMostPopularSubjects  = new ArrayList<>();

		listMostPopularSubjects = query.getResultList();

		return listMostPopularSubjects;
	}
	
	@Override
	public List<Object[]> getMostPopularSubjects2() {
		javax.persistence.Query query = em.createQuery
				("SELECT subject,Count(ms.user) from MarkSubject ms GROUP BY ms.subject ORDER BY Count(ms.user) DESC");
		
		List<Object[]> listMostPopularSubjects  = new ArrayList<>();

		listMostPopularSubjects = query.getResultList();    

		return listMostPopularSubjects;
	}

	@Override
	public int countVoteOfUserOnSubject(int idSubject) {
		Subject sub = new Subject();
		sub = em.find(Subject.class, idSubject);
	
		
		List<MarkSubject> listVoteOfUserOnSubject  = new ArrayList<>();
		TypedQuery<MarkSubject> query = 
				em.createQuery("select ms from MarkSubject ms WHERE ms.subject=:subjectid", MarkSubject.class);
		query.setParameter("subjectid", sub);

		listVoteOfUserOnSubject = query.getResultList();
		
		if(listVoteOfUserOnSubject.size()>0) {
			return listVoteOfUserOnSubject.size();
		}
	
		return 0;
	}
	
	
	
	@Override
	public boolean PushDownSubjet(int idSubject, int idUser) {

		MarkSubject ListMarksSub = null;
		User userr = new User();
		userr.setIdUser(idUser);
		Subject subject = new Subject();
		subject.setIdsubject(idSubject);

		TypedQuery<MarkSubject> query = 
				em.createQuery("select ms from MarkSubject ms WHERE ms.user=:userid and ms.subject=:subjectid", MarkSubject.class); 
		query.setParameter("userid", userr);
		query.setParameter("subjectid", subject);

		try {	
			ListMarksSub = query.getSingleResult();
			ListMarksSub.setUpdown(0);
			System.out.println("Vote Updated! !");
			return true;
		}

		catch (NoResultException e) {
			// TODO: handle exception
			MarkSubject newSubj = new MarkSubject();
			User user = em.find(User.class, idUser);
			Subject sub = em.find(Subject.class, idSubject);
			newSubj.setUser(user);
			newSubj.setSubject(sub);
			newSubj.setUpdown(0);
			em.persist(newSubj);
			System.out.println("new vote !");
			return false;
		}



		
	}

	@Override
	public boolean PushUpSubjet(int idSubject, int idUser) {


		MarkSubject ListMarksSub = null;
		User userr = new User();
		userr.setIdUser(idUser);
		Subject subject = new Subject();
		subject.setIdsubject(idSubject);

		TypedQuery<MarkSubject> query = 
				em.createQuery("select ms from MarkSubject ms WHERE ms.user=:userid and ms.subject=:subjectid", MarkSubject.class); 
		query.setParameter("userid", userr);
		query.setParameter("subjectid", subject);

		try {	
			ListMarksSub = query.getSingleResult();
			ListMarksSub.setUpdown(1);
			System.out.println("Vote Updated! !");
			return true;
		}

		catch (NoResultException e) {
			// TODO: handle exception
			MarkSubject newSubj = new MarkSubject();
			User user = em.find(User.class, idUser);
			Subject sub = em.find(Subject.class, idSubject);
			newSubj.setUser(user);
			newSubj.setSubject(sub);
			newSubj.setUpdown(1);
			em.persist(newSubj);
			System.out.println("new vote !");
			return false;
		}

	}



	@Override
	public boolean verifVoteSubjectUser(Subject sub, User user) {

		List<MarkSubject> ListMarksSub  = new ArrayList<>();
		TypedQuery<MarkSubject> query = 
				em.createQuery("select s from marksubject s WHERE s.user=:userid and s.subject=:subjectid", MarkSubject.class); 
		query.setParameter("userid", user);
		query.setParameter("subjectid", sub);
		ListMarksSub = query.getResultList();

		if(ListMarksSub.size()>0) {

			return true; // hethi Update 
		}

		else {
			return false; // hedhi  lel add 
		}

	}



	@Override
	public int verifState(Subject sub, User user) {
		// TODO Auto-generated method stub
		return 0;
	}




	
	


}


