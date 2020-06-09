package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;







@ManagedBean(name = "donutchart")
public class DonutChart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PieChartModel pieModel;
	
	

	
	
	 public PieChartModel getPieModel() {
		 createPieModel();   
		 System.out.println("trigered !");
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