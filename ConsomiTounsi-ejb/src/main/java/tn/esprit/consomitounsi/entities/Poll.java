package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
public class Poll implements Serializable {
    
    /**
	 * 
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Date createdAt;
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PollOption> options = new ArrayList<>();

    public Poll() {
        super();
        this.createdAt = Calendar.getInstance().getTime();
    }
    /*public void addOption(PollOption option) {
        option.setPoll(this);
        options.add(option);
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PollOption> getOptions() {
        return options;
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Poll [id=" + id + ", createdAt=" + createdAt + ", question=" + question + ", options=" + options + "]";
    }


} 