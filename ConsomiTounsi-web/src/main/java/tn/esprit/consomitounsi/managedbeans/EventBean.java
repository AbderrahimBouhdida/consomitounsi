package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import tn.esprit.consomitounsi.entities.Event;
import tn.esprit.consomitounsi.entities.Eventtype;
import tn.esprit.consomitounsi.services.impl.EventServices;
import tn.esprit.consomitounsi.services.impl.Eventbytype;


@ManagedBean(name = "eventbean")
@SessionScoped
public class EventBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int idevent;
	private String name;	
	@Enumerated(EnumType.STRING)
	private Eventtype type;
	private String description;	
	private String image;
	private int participantsnumber;	
	private Date date;
	private Integer eventIdToBeUpdated;
	
	private String destination = "F:\\REV\\NIDS\\J2EE\\workspace\\pidevnids\\pidevnids-web\\src\\main\\webapp\\resources\\uploads\\";
	private UploadedFile file;
	
	private PieChartModel pieModel;
	
	@EJB
	EventServices eventservice;
	
	public int getIdevent() {
		return idevent;
	}
	public void setIdevent(int idevent) {
		this.idevent = idevent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Eventtype getType() {
		return type;
	}
	public void setType(Eventtype type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getParticipantsnumber() {
		return participantsnumber;
	}
	public void setParticipantsnumber(int participantsnumber) {
		this.participantsnumber = participantsnumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Integer getEventIdToBeUpdated() {
		return eventIdToBeUpdated;
	}

	public void setEventIdToBeUpdated(Integer eventnIdToBeUpdated) {
		this.eventIdToBeUpdated = eventnIdToBeUpdated;
	}
	
	public void addEvent() throws IOException {
		upload();
		System.out.println("file" + file.getFileName() + "input" + file.getInputstream().toString());

		TransferTile(file.getFileName(), file.getInputstream());
		eventservice.addEvent(new Event(idevent,name,type,description,file.getFileName(),participantsnumber,date));
	}
	
	
	



	// Event list
	private List<Event> events;
	public List<Event> getEvents() {
		events = eventservice.findAllEvent();
	return events;
	} 
	
	//Event remove
	public void removeEvent(int id)
	{
		eventservice.removeEvent(id);
	}
	
	//display Event
	
	public void displayEvent(Event env)
	{
	this.setName(env.getName());
	this.setType(env.getType());
	this.setDescription(env.getDescription());
	this.setImage(env.getImage());
	this.setParticipantsnumber(env.getParticipantsnumber());
	this.setDate(env.getDate());
	this.setEventIdToBeUpdated(env.getIdevent());
	}
	
	//Event update
	

	public void updateEvent() throws IOException
	{ 
		if (file.getSize() != 0) {
			upload();
			TransferTile(file.getFileName(), file.getInputstream());
			eventservice.updateEventv2(new Event(eventIdToBeUpdated,name,type,description,file.getFileName(),participantsnumber,date));	
        }
		//fix me later
//		upload();
//		TransferTile(file.getFileName(), file.getInputstream());
//		eventservice.updateEventv2(new Event(eventIdToBeUpdated,name,type,description,file.getFileName(),participantsnumber,date));	
	} 
	
	// upload

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
		
		
		public PieChartModel getPieModel() {
			 createPieModel();   
			return pieModel;
		}
		
		
		private void createPieModel() {
	        pieModel = new PieChartModel();
	        ChartData data = new ChartData();
	         
	        PieChartDataSet dataSet = new PieChartDataSet();
	        List<Number> values = new ArrayList<>();
	        //values.add(300);
	        //values.add(50);
	        //values.add(100);
	        
	        
	         
	        List<String> bgColors = new ArrayList<>();
	        bgColors.add("rgb(255, 99, 132)");
	        bgColors.add("rgb(54, 162, 235)");
	        bgColors.add("rgb(255, 205, 86)");
	        dataSet.setBackgroundColor(bgColors);
	         
	        data.addChartDataSet(dataSet);
	        List<String> labels = new ArrayList<>();
	        //labels.add("Red");
	        //labels.add("Blue");
	        //labels.add("Yellow");
        
	        
	        
	        List<Object[]> l = eventservice.eventsbytype();
			List<Eventbytype> result = new ArrayList<>(l.size());
			for (Object[] row : l) {
			    result.add(new Eventbytype( row[0].toString(),
			                            (long) row[1]
			                          ));
			}
	      
			for (final Eventbytype eve : result) {

		       System.out.println("type"+eve.gettype()+"exist"+eve.getcount()+"times !");
		         
		        
		     
		       values.add(eve.getcount());
		       labels.add(eve.gettype().toString());
		}
			data.setLabels(labels);
			dataSet.setData(values);
	        pieModel.setData(data);
	    }


	
	

}
