package tn.esprit.consomitounsi.services.intrf;

import javax.ejb.Remote;


@Remote
public interface MarkReponseRemote {
	
	public boolean VerifierVoteReponse(int idUser, int idResponse);
	public int countVote(int idResponse);
	public void likeResponse(int idUser,int idResponse);
	public void dislikeResponse(int idUser,int idResponse);

}
