package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.MarkSubject;
import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.entities.User;


@Remote
public interface MarkSubjectRemote {
	public boolean PushDownSubjet(int idSubject , int idUser);
	public boolean PushUpSubjet(int idSubject, int idUser);
	public boolean verifVoteSubjectUser(Subject sub, User user);
	public int verifState(Subject sub, User user); //verifEtat : besh nshouf l'etat mta3 el updown w naref shnouwa  el bouton eli besh nhotou disabled weli enabled
	public int countVoteOfUserOnSubject(int idSubject);
	public List<MarkSubject> getMostPopularSubjects();
	public List<Object[]> getMostPopularSubjects2();
}
