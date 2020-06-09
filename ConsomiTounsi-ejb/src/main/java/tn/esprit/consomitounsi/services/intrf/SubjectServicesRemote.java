package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.entities.User;

@Remote
public interface SubjectServicesRemote {

	public int addSubject(Subject Sub);
	public void editSubjet(Subject Sub);
	public void deleteSubject(int id);
	public List<Subject> getAllSubjects();
	public Subject SearchSubjectById(int id);
	public boolean VerifSujet(User user, Subject subject); // hethi juste besh tshouf ken sujet mta el user heka yothoroulou el boutonet el kol else tothorlou juste page adia.
	public List<Subject> getlatestSubjects();
	public int getNbrSubjectByCat(String cat);	// for stat
	public List<Subject> getSubjectByCat(String cat);
	public void resolvedsub(Subject sub);
	//statistic mba3d
	
	
	/*public boolean SpamSubject(Subject src,int idUser);
	public int  verifSpamSubjectUser(int idUser, Subject src);*/
	
	
}
