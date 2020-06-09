package tn.esprit.consomitounsi.services.intrf;

import javax.ejb.Remote;


@Remote

public interface SpamSubjectRemote {
	
	public void AddSpamSubject(int idSubject,int idUser);
	public int  CountSpamSujet(int idSubject);
	public boolean  VerifSpam(int idUser,int idSubject);
	
}
