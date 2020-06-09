package tn.esprit.consomitounsi.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.SubjectServicesRemote;



@Stateless
@LocalBean
public class SubjectService implements SubjectServicesRemote{
	

	@PersistenceContext
	EntityManager em;
	
	
	
	@Override
	public int addSubject(Subject Sub) {
		em.persist(Sub);
		return Sub.getIdsubject();	
		}


	@Override
	public void editSubjet(Subject sub) {
		// Subject sub = em.find(Subject.class, Sub.getIdsubject());
	       // sub.setSubjectitle(Sub.getSubjectitle());
		em.merge(sub);
	}
	
	

	@Override
	public void deleteSubject(int idSubject) {
		em.remove(em.find(Subject.class, idSubject));
        System.out.println("Subject deleted");		
	}


	@Override
	public Subject SearchSubjectById(int id) {
        Subject sub = em.find(Subject.class, id);
        
		return sub;
	}

	@Override
    public List<Subject> getAllSubjects() {
        List<Subject> subs = em.createQuery("select s from Subject s", Subject.class).getResultList();
        return subs;
    }
	
	@Override
	public void resolvedsub(Subject sub) {
		sub.setResolved(true);
		em.merge(sub);
		

	}


	@Override
	public boolean VerifSujet(User user, Subject subject) { 
		// hethi juste besh tshouf ken sujet mta el user heka yothoroulou el boutonet el kol else tothorlou juste page adia.
		//itha ken par exp l'user 11 wel sujet 7 passé en param  affichili page eli feha buttons ta l update w fazet kek teb3in user w sujet heka mteou
		
		TypedQuery<Subject> query = 
				em.createQuery("select s from Subject s WHERE s.user=:userid and s.subject=:subjectid", Subject.class); 
				query.setParameter("userid", user);
				query.setParameter("subjectid", subject);
				
				List<Subject> subjects  = new ArrayList<>();
				
				subjects = query.getResultList(); 
				
				if(subjects.size()>0)
				{
					return false; 
					
				}else {
				
				
				return true; 
				
				}
	}
	


	@Override
	public List<Subject> getlatestSubjects() {
		
		//Subject subject = new Subject();
		//subject.setCategory(cat);
		TypedQuery<Subject> query = 
				em.createQuery("SELECT s from Subject s ORDER BY subjectdate DESC", Subject.class); 
		
				List<Subject> Subjects  = new ArrayList<>();
				try {
					Subjects = query.getResultList(); 
				}
				catch (Exception e) {
					System.out.println("Erreur : " + e);
				}
				System.out.println("get all subject by cat " +Subjects);

				return Subjects;
				
	}


	@Override
	public int getNbrSubjectByCat(String cat) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<Subject> getSubjectByCat(String cat) {
		
		Subject subject = new Subject();
		subject.setCategory(cat);
		TypedQuery<Subject> query = 
				em.createQuery("SELECT s from Subject s Where s.category=:category ", Subject.class); 
				query.setParameter("category", cat); 

				List<Subject> Subjects  = new ArrayList<>();
				try {
					Subjects = query.getResultList(); 
				}
				catch (Exception e) {
					System.out.println("Erreur : " + e);
				}
				System.out.println("get cats" +Subjects);

				return Subjects;
	
	}
	
}
