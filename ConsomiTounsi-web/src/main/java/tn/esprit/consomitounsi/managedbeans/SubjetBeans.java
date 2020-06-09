package tn.esprit.consomitounsi.managedbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import tn.esprit.consomitounsi.entities.MarkSubject;
import tn.esprit.consomitounsi.entities.Response;
import tn.esprit.consomitounsi.entities.Subject;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.MarkReponseService;
import tn.esprit.consomitounsi.services.impl.MarkSubjectService;
import tn.esprit.consomitounsi.services.impl.ResponseService;
import tn.esprit.consomitounsi.services.impl.SpamService;
import tn.esprit.consomitounsi.services.impl.SubjectService;
import tn.esprit.consomitounsi.services.impl.UserServices;


@ManagedBean(name = "SubjectBean")
@SessionScoped
public class SubjetBeans implements Serializable {

	private static final long serialVersionUID = 1L;
	// subject
	private int idsubject;
	private String content;
	private String subjectitle;
	private String category;
	private Date subjectdate;
	private boolean resolved;
	private String image;
	private String quote;
	private String cite;
	private List<Subject> subs = new ArrayList<Subject>();
	private Subject subject;
	private boolean editing =false;
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;
	
	//PrettyTime p = new PrettyTime();

	// User
	private User user;
	private String postUserImage;
	// image upload
	private UploadedFile file;
	private String destination = "C:\\Users\\kimoz\\OneDrive\\Bureau\\Folder Of Folders\\!!!!!!Esprit2019_2020!!!!!!!\\2eme semestre\\DEV JEE\\consomi-tounsi\\consomitounsi\\ConsomiTounsi-web\\src\\main\\webapp\\resources\\uploads\\";

	// response
	private Response comment;
	private String ResponseDateAGO;
	private List<Response> resps = new ArrayList<Response>();

	// detail sub

	private String postTitle;
	private String postImage;
	private String postContent;
	private Date postDate;
	private String postUserName;
	private int postIdSubject;
	private String postQuote;
	private String postCite;
	private String postCategory;
	private String postDateAGO;
	private int postIdUser;
	private boolean postResolved;
	
	//spam
	private int idspam;
	private boolean spamed=false;

	
	
	public boolean isSpamed() {
		return spamed;
	}

	public void setSpamed(boolean spamed) {
		this.spamed = spamed;
	}

	@EJB
	SubjectService sub;
	@EJB
	ResponseService resp;
	@EJB
	UserServices userserv;
	@EJB
	MarkSubjectService marksub;
	@EJB
	MarkReponseService markresp;
	@EJB
	SpamService spam;

	
	
	
	public SpamService getSpam() {
		return spam;
	}

	public void setSpam(SpamService spam) {
		this.spam = spam;
	}

	public int getPostIdUser() {
		return postIdUser;
	}

	public void setPostIdUser(int postIdUser) {
		this.postIdUser = postIdUser;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getResponseDateAGO() {
		return ResponseDateAGO;
	}

	public void setResponseDateAGO(String responseDateAGO) {
		ResponseDateAGO = responseDateAGO;
	}

	public String getPostDateAGO() {
		return postDateAGO;
	}

	public void setPostDateAGO(String postDateAGO) {
		this.postDateAGO = postDateAGO;
	}

	public String getPostUserImage() {
		return postUserImage;
	}

	public void setPostUserImage(String postUserImage) {
		this.postUserImage = postUserImage;
	}

	public Response getComment() {
		return comment;
	}
	
	

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void setComment(Response comment) {
		this.comment = comment;
	}

	public List<Response> getResps() {
		return resps;
	}

	public void setResps(List<Response> resps) {
		this.resps = resps;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostImage() {
		return postImage;
	}

	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}

	public int getIdsubject() {
		return idsubject;
	}

	public void setIdsubject(int idsubject) {
		this.idsubject = idsubject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubjectitle() {
		return subjectitle;
	}

	public void setSubjectitle(String subjectitle) {
		this.subjectitle = subjectitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getSubjectdate() {
		return subjectdate;
	}

	public void setSubjectdate(Date subjectdate) {
		this.subjectdate = subjectdate;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String getImage() {
		return image;
	}

	public boolean isPostResolved() {
		return postResolved;
	}

	public void setPostResolved(boolean postResolved) {
		this.postResolved = postResolved;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SubjectService getSub() {
		return sub;
	}

	public void setSub(SubjectService sub) {
		this.sub = sub;
	}

	public List<Subject> getSubs() {
		return subs;
	}

	public void setSubs(List<Subject> subs) {
		this.subs = subs;
	}

	// upload

	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public String getCite() {
		return cite;
	}

	public void setCite(String cite) {
		this.cite = cite;
	}

	public String getPostCite() {
		return postCite;
	}

	public void setPostCite(String postCite) {
		this.postCite = postCite;
	}

	public String getPostQuote() {
		return postQuote;
	}

	public void setPostQuote(String postQuote) {
		this.postQuote = postQuote;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public int getPostIdSubject() {
		return postIdSubject;
	}

	public void setPostIdSubject(int postIdSubject) {
		this.postIdSubject = postIdSubject;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getPostUserName() {
		return postUserName;
	}

	public void setPostUserName(String postUserName) {
		this.postUserName = postUserName;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {

		if (file != null) {
			FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void TransferTile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination + fileName));
			int reader = 0;
			byte[] bytes = new byte[(int) file.getSize()];
			while ((reader = in.read(bytes)) != -1) {
				out.write(bytes, 0, reader);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// Add Subject
	public String addSubject() {

		upload();
		try {

			System.out.println("file" + file.getFileName() + "input" + file.getInputstream().toString());
			TransferTile(file.getFileName(), file.getInputstream());
			Subject subj = new Subject(content, subjectitle, category, quote, cite, resolved, file.getFileName(), loginBean.getuser());

			subj.setResponses(new ArrayList<Response>());
			
			sub.addSubject(subj);
		} catch (Exception e) {
			System.out.println("he hehe hehehe");
		}
		return "";

	}

	// subject list

	public List<Subject> getAllSubjects() {
		subs = sub.getAllSubjects();

		return subs;

	}
	// get all subject order by date desc
	public List<Subject> getlatestSubjects() {
		subs = sub.getlatestSubjects();
		System.out.println("x"+ subs);

		return subs;

	}

	//list responses
	public List<Response> getAllResponses(int idSubject) {
		List<Response> responses = new ArrayList<>();

		Subject tmp = sub.SearchSubjectById(idSubject);
		responses = tmp.getResponses();
		return responses;
	}
	
		// get all responses order by date desc

	public List<Response> getlatestResponses() {
		List<Response> responses = new ArrayList<>();
		responses = resp.getlatestResponses();
		return responses;
	}

	// delete subject
	public void deleteSubject(int idSubject) {

		// resp.DeleteResponsesOfSubject(idSubject);
		sub.deleteSubject(idSubject);
	}
	// delete response
		public void deleteResponse(Response resps) {
			
		
			//sub.editSubjet(tmp);
			System.out.println("ok6");

			resp.DeleteResponse(subject, resps.getIdResponse());
				System.out.println("ok7");

			//resp.DeleteResponsesOfSubject(reeeeeeeeeee.getIdResponse());
		}

	// get nombre commentaire

	public int nbrComment(int idSubject) {
		System.out.println("id subject" + idSubject);
		Subject tmp = sub.SearchSubjectById(idSubject);
		int count = tmp.getResponses().size();
		return count;

	}
	// count Vote Of User On Subject

	public int countvote(int idSubject) {
		System.out.println("TEMSHI????");
		int count = marksub.countVoteOfUserOnSubject(idSubject);
		System.out.println("nombre de vote :" + count);

		return count;
	}
	
	//Count Like
	public int countLikeDislike(Response resp) {
		
		int count = markresp.countVote(resp.getIdResponse());
		return count;
	}
	//push up
	public String pushup(int subjectId) { // tansesh besh t3adilha el user bel session
		System.out.println("pushup not attig");

		marksub.PushUpSubjet(subjectId,10);
		System.out.println("pushup worksss");

	return "post?faces-redirect=true";

	}
	
	//push down
	
	public String pushdown(int subjectId) {
		System.out.println("pushdown not attig");

		marksub.PushDownSubjet(subjectId, 10);
		System.out.println("pushdown worksss");

		return "post?faces-redirect=true";

		}

	//dislike
	public String dislikeResponsejsf(Response resp ) { // tansesh besh t3adilha el user bel session
		markresp.dislikeResponse(11, resp.getIdResponse());
		System.out.println("dislikeeeeeee");

		return "post?faces-redirect=true";
		
	}
	//like
	public String likeResponsejsf(Response resp ) { // tansesh besh t3adilha el user bel session
		System.out.println("like");
		markresp.likeResponse(11, resp.getIdResponse());
		
		return "post?faces-redirect=true";
		
	}
	//get subjects by category
	
	public List<Subject> postsByCategory() {
		
		List<Subject> subjectsbycat = new ArrayList<>();
		subjectsbycat = sub.getSubjectByCat(postCategory);
		return subjectsbycat;

	}

	// get most popular subjects
	public List<MarkSubject> getMostPopularSubs() {

		Map<Subject, Long> ms = new HashMap<Subject, Long>();
		for (Object[] markSubject : marksub.getMostPopularSubjects2()) {
			ms.put((Subject) markSubject[0], (Long) markSubject[1]);
		}
		//////
		List<Entry<Subject, Long>> list = new LinkedList<Entry<Subject, Long>>(ms.entrySet());
		// sorting the list elements
		Collections.sort(list, new Comparator<Entry<Subject, Long>>() {

			@Override
			public int compare(Entry<Subject, Long> o1, Entry<Subject, Long> o2) {
				// TODO Auto-generated method stub
				return (int) (o2.getValue()-o1.getValue());
			}
		});
		Map<Subject, Long> sortedMap = new LinkedHashMap<Subject, Long>();  
		for (Entry<Subject, Long> entry : list)   
		{  
		sortedMap.put(entry.getKey(), entry.getValue());  
		}
		for (Map.Entry<Subject, Long> entry : sortedMap.entrySet()) {
			System.out.println(entry.getKey().getSubjectitle() + " || " + entry.getValue());
		}

		return new ArrayList(sortedMap.keySet());
	}

	public String addComent(int idSubject) {
		comment = new Response();
		comment.setContent(content);
		resp.AddResponse(comment);
		Subject tmp = sub.SearchSubjectById(idSubject);
		List<Response> tmpList = tmp.getResponses();
		tmpList.add(comment);
		tmp.setResponses(tmpList);
		sub.editSubjet(tmp);
		return "";
	}
	
	
	public void resolvedSubj() {
		System.out.println("what the hell?");

		 sub.resolvedsub(subject);
			System.out.println("not what the hell?");

	}

	
	public void spamsubj() {
		
		
		System.out.println("spam masaresh");

			spam.AddSpamSubject(subject.getIdsubject(), 11);
			System.out.println("Spam saarr!");
		spamed=true;

	
		
	}
	
	
	
	public String commentedit(Response resp) {

		return "post.jsf?faces-redirect=true";
	}

	public String postDetail(Subject sub) {
		System.out.println("meeeeeeeh");
		postTitle = sub.getSubjectitle();
		postImage = sub.getImage();
		postContent = sub.getContent();
		postDate = sub.getSubjectdate();
		postIdSubject = sub.getIdsubject();
		postQuote = sub.getQuote();
		postUserName = sub.getUser().getFirstName();
		postUserImage = sub.getUser().getImg();
		postCategory = sub.getCategory();
		//postDateAGO = p.format(postDate);
		postCite = sub.getCite();
		postResolved = sub.isResolved();
		subject = sub;
		System.out.println("resolveed"+postResolved);

		return "post.jsf?faces-redirect=true";
	}
	
	public String postDetailIndex(Subject sub) {
		System.out.println("meeeeeeeh");
		postTitle = sub.getSubjectitle();
		postImage = sub.getImage();
		postContent = sub.getContent();
		postDate = sub.getSubjectdate();
		postIdSubject = sub.getIdsubject();
		postQuote = sub.getQuote();
		postUserName = sub.getUser().getFirstName();
		postUserImage = sub.getUser().getImg();
		postCategory = "plants";
		//postDateAGO = p.format(postDate);
		postCite = sub.getCite();
		postIdUser = sub.getUser().getIdUser();
		return "/blog/post.jsf?faces-redirect=true";
	}
	public String postDetailMostPopSubs(Subject sub) {
		System.out.println("meeeeeeeh");
		postTitle = sub.getSubjectitle();
		postImage = sub.getImage();
		postContent = sub.getContent();
		postDate = sub.getSubjectdate();
		postIdSubject = sub.getIdsubject();
		postQuote = sub.getQuote();
		postUserName = sub.getUser().getFirstName();
		postUserImage = sub.getUser().getImg();
		postCategory = "plants";
		//postDateAGO = p.format(postDate);
		postCite = sub.getCite();
		postIdUser = sub.getUser().getIdUser();
		return "/blog/post.jsf?faces-redirect=true";
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public void editComment(Response respon) {
		
		if(editing) {
			resp.EditResponse(respon);
			System.out.println("edited ");
			content = "";
		}
		editing = !editing;
		
		
	}
	
	
	
}
