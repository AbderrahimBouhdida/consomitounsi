package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
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
//import org.primefaces.model.chart.Axis;
//import org.primefaces.model.chart.AxisType;
//import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.charts.bar.BarChartModel;
//import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import tn.esprit.consomitounsi.entities.Donation;
import tn.esprit.consomitounsi.entities.Donationtype;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.DonationServices;
import tn.esprit.consomitounsi.services.impl.Topdonators;

@ManagedBean(name = "donationbean")
@SessionScoped
public class DonationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int iddon;
	private String name;
	@Enumerated(EnumType.STRING)
	private Donationtype type;
	private String description;
	private String image;
	private Integer donationIdToBeUpdated;

	private String destination = "F:\\REV\\NIDS\\J2EE\\workspace\\pidevnids\\pidevnids-web\\src\\main\\webapp\\resources\\uploads\\";
	private UploadedFile file;
	
	//private BarChartModel barModel;
	//stop here later
	private BarChartModel barModel;

	@EJB
	DonationServices donationservice;

	public int getIddon() {
		return iddon;
	}

	public void setIddon(int iddon) {
		this.iddon = iddon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Donationtype getType() {
		return type;
	}

	public void setType(Donationtype type) {
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

	public DonationServices getDonationservice() {
		return donationservice;
	}

	public void setDonationservice(DonationServices donationservice) {
		this.donationservice = donationservice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getDonationIdToBeUpdated() {
		return donationIdToBeUpdated;
	}

	public void setDonationIdToBeUpdated(Integer donationIdToBeUpdated) {
		this.donationIdToBeUpdated = donationIdToBeUpdated;
	}

	public void addDonation() throws IOException {

		upload();
		System.out.println("file" + file.getFileName() + "input" + file.getInputstream().toString());

		TransferTile(file.getFileName(), file.getInputstream());
		donationservice.addDonation(new Donation(iddon, name, type, description, file.getFileName()));
	}

	// donation list
	private List<Donation> dons;

	public List<Donation> getDonations() {
		dons = donationservice.findAllDonation();
		return dons;
	}

	// employee remove
	public void removeDonation(int id) {
		donationservice.removeDonation(id);
	}

	// displayEmploye

	public void displayDonation(Donation don) {
		this.setName(don.getName());
		this.setType(don.getType());
		this.setDescription(don.getDescription());
		this.setImage(don.getImage());
		this.setDonationIdToBeUpdated(don.getIddon());
	}

	// Donation update

	public void updateDonation() throws IOException {
		upload();
		TransferTile(file.getFileName(), file.getInputstream());
		donationservice.updateDonationv2(new Donation(donationIdToBeUpdated, name, type, description, file.getFileName()));
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
	
	
	
//	public BarChartModel getBarModel() {
//	//	System.out.println("trigered ! ");
//		createBarModel();
//		return barModel;
//	}
	
	public BarChartModel getBarModel() {
		 createBarModel();   
		// System.out.println("trigered !");
		return barModel;
	}
	
	public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Number of donates");
         
        List<Number> values = new ArrayList<>();


       
        
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
      
        //data.setLabels(labels);
        //barModel.setData(data);
        
        List<Object[]> l = donationservice.topdonators();
		List<Topdonators> result = new ArrayList<>(l.size());
		for (Object[] row : l) {
		    result.add(new Topdonators((User) row[0],
		                            (long) row[1]
		                          ));
		}
      
		for (final Topdonators donator : result) {

	       System.out.println("user"+donator.getUsr().getIdUser()+"has donated "+donator.getIdusr()+"times !");
	         
	        
	          
		//	 out += "user"+Integer.toString(donator.getUsr().getIdUser())+" has donated "+Long.toString(donator.getIdusr())+"times !"+"\n";
					 
		//	topdonatorchart.set(donator.getUsr().getUsername().toString(), donator.getIdusr());
	       values.add(donator.getIdusr());
	       labels.add(donator.getUsr().getUsername().toString());
	}
		
        barDataSet.setData(values);
		data.setLabels(labels);
		barModel.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Top donators");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        barModel.setOptions(options);
    }
	
	
//	private void createBarModel() {
//        barModel = initBarModel();
// 
//        barModel.setTitle("Top donators");
//        barModel.setLegendPosition("ne");
// 
//        Axis xAxis = barModel.getAxis(AxisType.X);
//        xAxis.setLabel("id of user");
// 
//        Axis yAxis = barModel.getAxis(AxisType.Y);
//        yAxis.setLabel("Number of donations");
//        yAxis.setMin(0);
//        yAxis.setMax(10);
//    }
//	
//	private BarChartModel initBarModel() {
//		
//		
//        BarChartModel model = new BarChartModel();
// 
//        ChartSeries topdonatorchart = new ChartSeries();
//        topdonatorchart.setLabel("Pack Name");
//        
//          
//          
//          List<Object[]> l = donationservice.topdonators();
//  		List<Topdonators> result = new ArrayList<>(l.size());
//  		for (Object[] row : l) {
//  		    result.add(new Topdonators((User) row[0],
//  		                            (long) row[1]
//  		                          ));
//  		}
//          
//  		for (final Topdonators donator : result) {
//
//  	       //System.out.println("user"+donator.getUsr().getIdUser()+"has donated "+donator.getIdusr()+"times !");
//  	         
//  	        
//  	          
//  		//	 out += "user"+Integer.toString(donator.getUsr().getIdUser())+" has donated "+Long.toString(donator.getIdusr())+"times !"+"\n";
//  					 
//  			topdonatorchart.set(donator.getUsr().getUsername().toString(), donator.getIdusr());
//  	}
//
//
//
//
//
//        model.addSeries(topdonatorchart);
//     
// 
//        return model;
//    }

}
