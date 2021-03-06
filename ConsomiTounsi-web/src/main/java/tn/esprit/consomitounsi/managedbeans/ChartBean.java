package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;







@ManagedBean(name = "chartbean")
public class ChartBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChartModel barModel2;
	
	

	
	
	 public BarChartModel getBarModel2() {
		 createBarModel2();   
		 System.out.println("trigered !");
		return barModel2;
	}




	public void createBarModel2() {
	        barModel2 = new BarChartModel();
	        ChartData data = new ChartData();
	         
	        BarChartDataSet barDataSet = new BarChartDataSet();
	        barDataSet.setLabel("My First Dataset");
	        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
	        barDataSet.setBorderColor("rgb(255, 99, 132)");
	        barDataSet.setBorderWidth(1);
	        List<Number> values = new ArrayList<>();
	        values.add(65);
	        values.add(59);
	        values.add(80);
	        values.add(81);
	        values.add(56);
	        values.add(55);
	        values.add(40);
	        barDataSet.setData(values);
	         
	        BarChartDataSet barDataSet2 = new BarChartDataSet();
	        barDataSet2.setLabel("My Second Dataset");
	        barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
	        barDataSet2.setBorderColor("rgb(255, 159, 64)");
	        barDataSet2.setBorderWidth(1);
	        List<Number> values2 = new ArrayList<>();
	        values2.add(85);
	        values2.add(69);
	        values2.add(20);
	        values2.add(51);
	        values2.add(76);
	        values2.add(75);
	        values2.add(10);
	        barDataSet2.setData(values2);
	 
	        data.addChartDataSet(barDataSet);
	        data.addChartDataSet(barDataSet2);
	         
	        List<String> labels = new ArrayList<>();
	        labels.add("January");
	        labels.add("February");
	        labels.add("March");
	        labels.add("April");
	        labels.add("May");
	        labels.add("June");
	        labels.add("July");
	        data.setLabels(labels);
	        barModel2.setData(data);
	         
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
	        title.setText("Bar Chart");
	        options.setTitle(title);
	         
	        barModel2.setOptions(options);
	    }



}