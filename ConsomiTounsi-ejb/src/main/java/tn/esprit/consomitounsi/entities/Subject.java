package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;


@Entity
public class Subject implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsubject;
	private String content;
	private String subjectitle;
	private String category;
	private String quote;
	private String cite;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date subjectdate = new java.sql.Date(new java.util.Date().getTime());
	
	private boolean  resolved;
	private String image;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.REMOVE})
	private List<Response> responses;
	
	@ManyToOne
	private User user;

	public Subject(String content, String subjectitle, String category, String quote, String cite,
			boolean resolved, String image, User user) {
		super();
		this.content = content;
		this.subjectitle = subjectitle;
		this.category = category;
		this.quote = quote;
		this.cite = cite;
		this.resolved = resolved;
		this.image = image;
		this.user = user;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + idsubject;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + (resolved ? 1231 : 1237);
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
		result = prime * result + ((subjectdate == null) ? 0 : subjectdate.hashCode());
		result = prime * result + ((subjectitle == null) ? 0 : subjectitle.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (idsubject != other.idsubject)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (resolved != other.resolved)
			return false;
		if (responses == null) {
			if (other.responses != null)
				return false;
		} else if (!responses.equals(other.responses))
			return false;
		if (subjectdate == null) {
			if (other.subjectdate != null)
				return false;
		} else if (!subjectdate.equals(other.subjectdate))
			return false;
		if (subjectitle == null) {
			if (other.subjectitle != null)
				return false;
		} else if (!subjectitle.equals(other.subjectitle))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


	public List<Response> getResponses() {
		return responses;
	}


	public void setResponses(List<Response> responses) {
		this.responses = responses;
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




	public void setImage(String image) {
		this.image = image;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}

	
	
	
	
	
	public String getQuote() {
		return quote;
	}


	public void setQuote(String quote) {
		this.quote = quote;
	}


	
	
	public String getCite() {
		return cite;
	}


	public void setCite(String cite) {
		this.cite = cite;
	}


	public Subject() {
	}
	
	
	
	
	

}
