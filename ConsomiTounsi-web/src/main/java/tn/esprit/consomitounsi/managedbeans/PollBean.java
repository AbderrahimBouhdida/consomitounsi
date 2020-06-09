package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import tn.esprit.consomitounsi.entities.Poll;
import tn.esprit.consomitounsi.entities.PollOption;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.services.impl.PollServices;



@ManagedBean(name = "pollBean")
@SessionScoped
public class PollBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String text;
	private String text2;
	private String text3;
	private Integer votes;
	private Integer votes2;
	private Integer votes3;
	private Date createdAt;
	private String question;
	private PieChartModel pieModel;
	private List<Poll> poll;
	@EJB
	PollServices pollService;
	

	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public String getText3() {
		return text3;
	}
	public void setText3(String text3) {
		this.text3 = text3;
	}
	public Integer getVotes2() {
		return votes2;
	}
	public void setVotes2(Integer votes2) {
		this.votes2 = votes2;
	}
	public Integer getVotes3() {
		return votes3;
	}
	public void setVotes3(Integer votes3) {
		this.votes3 = votes3;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getVotes() {
		return votes;
	}
	public void setVotes(Integer votes) {
		this.votes = votes;
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
	public List<Poll> getPoll() {
		return pollService.findAllPol();
	}
	public void setPoll(List<Poll> poll) {
		this.poll = poll;
	}
	public PollServices getPollService() {
		return pollService;
	}
	public void setPollService(PollServices pollService) {
		this.pollService = pollService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	 public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	public PieChartModel getPieModel() {
		 createPieModel();   
		 System.out.println("trigered !");
		return pieModel;
	}
	 
	public void addPoll() {
		Poll poll = new Poll();
		poll.setCreatedAt(createdAt);
		poll.setQuestion(question);
		
		List<PollOption> pollOption =new ArrayList<PollOption>();
		PollOption PollOp = new  PollOption();
		PollOp.setText(text);
		PollOp.setVotes(votes);
		pollOption.add(PollOp);
		
		PollOption PollOpt = new  PollOption();
		PollOp.setText(text2);
		PollOp.setVotes(votes2);
		pollOption.add(PollOpt);
		
		PollOption PollOpti = new  PollOption();
		PollOp.setText(text3);
		PollOp.setVotes(votes3);
		pollOption.add(PollOpti);
		
		poll.setOptions(pollOption);
		pollService.addPoll(poll);
		}
	
	 public Product findAllPol() {
			pollService.findAllPol();
			return null;
		 }
		 
	public void removePoll() {
		System.out.println(id);
		pollService.removePoll(id);
		
	}
	
	public void updatePoll() {
		Poll poll = new Poll();
		poll.setCreatedAt(createdAt);
		//poll.setOptions(options);
		poll.setQuestion(question);
		
	}

	public PieChartModel pcm(Poll poll) {
		List<PollOption> pos= poll.getOptions();
		pieModel = new PieChartModel();
        ChartData data = new ChartData();
        int sum=0;
        for (PollOption po : pos) {
			sum=sum+po.getVotes();
		}
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        for (PollOption po : pos) {
			values.add(po.getVotes()*100/sum);
		}
        dataSet.setData(values);
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
        
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for (PollOption po : pos) {
			labels.add(po.getText());
		}
        data.setLabels(labels);
         
        pieModel.setData(data);
        
        return pieModel;
	}
	        
	 private void createPieModel() {
	        pieModel = new PieChartModel();
	        ChartData data = new ChartData();
	         
	        PieChartDataSet dataSet = new PieChartDataSet();
	        List<Number> values = new ArrayList<>();
	        values.add(300);
	        values.add(50);
	        values.add(100);
	        dataSet.setData(values);
	         
	        List<String> bgColors = new ArrayList<>();
	        bgColors.add("rgb(255, 99, 132)");
	        bgColors.add("rgb(54, 162, 235)");
	        bgColors.add("rgb(255, 205, 86)");
	        dataSet.setBackgroundColor(bgColors);
	         
	        data.addChartDataSet(dataSet);
	        List<String> labels = new ArrayList<>();
	        labels.add("Red");
	        labels.add("Blue");
	        labels.add("Yellow");
	        data.setLabels(labels);
	         
	        pieModel.setData(data);
	    }

}
