package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Response;
import tn.esprit.consomitounsi.entities.Subject;



@Remote
public interface ResponseServicesRemote {
	public int AddResponse(Response response);
	public void EditResponse(Response response);
	public List<Response> getAllResponsesBySubjectId(int idSubject);
	public List<Response> getResponsesOfAllSubs();

	public Response ChercherReponse(int id);
	//public int VerifResponse (User user,Response response);
	public void DeleteResponse(Subject sub,int idresponse);
	public void DeleteResponsesOfSubject(int idSubjet); // besh nestamel el fonction hethi wa9teli besh nfasakh sujet nfasakh maah les reponses mteou lkol
	public int countResponseOfSubject(int idSubject);
	public List<Response> getlatestResponses();


}
