/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificaciondepizzas.chart;

import clasificaciondepizzas.classification.base.FlowingPizza;
import clasificaciondepizzas.classification.base.PoorAlignment;
import clasificaciondepizzas.classification.base.PoorPressing;
import clasificaciondepizzas.classification.base.Standard;
import java.util.Hashtable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Alejandro
 */
public class BaseChart {



     private static final String FLOWING = "Flowing Pizza";
     private static final String ALIGNMENT = "Poor Alignment";
     private static final String PRESSING = "Poor Pressing";
     private static final String STANDARD = "Standard";
     private static final String SAMPLE = "My Pizza";

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    private JFreeChart chart;

     public BaseChart(double r, double e, double a1, double a2){


         FlowingPizza flowing = new FlowingPizza();
         PoorAlignment alignment = new PoorAlignment();
         PoorPressing pressing = new PoorPressing();
         Standard standard = new Standard();
         Hashtable<String,Double> aux;

         aux = flowing.getHash();
         dataset.setValue(aux.get("R"), FLOWING, "Roudness");
         dataset.setValue(aux.get("E"), FLOWING, "Eccentricity");
         dataset.setValue(aux.get("A1"), FLOWING, "Area Ratio");
         dataset.setValue(aux.get("A2"), FLOWING, "Aspect Ratio");

         aux = alignment.getHash();
         dataset.setValue(aux.get("R"), ALIGNMENT, "Roudness");
         dataset.setValue(aux.get("E"), ALIGNMENT, "Eccentricity");
         dataset.setValue(aux.get("A1"), ALIGNMENT, "Area Ratio");
         dataset.setValue(aux.get("A2"), ALIGNMENT, "Aspect Ratio");

         aux = pressing.getHash();
         dataset.setValue(aux.get("R"), PRESSING, "Roudness");
         dataset.setValue(aux.get("E"), PRESSING, "Eccentricity");
         dataset.setValue(aux.get("A1"), PRESSING, "Area Ratio");
         dataset.setValue(aux.get("A2"), PRESSING, "Aspect Ratio");

         aux = standard.getHash();
         dataset.setValue(aux.get("R"), STANDARD, "Roudness");
         dataset.setValue(aux.get("E"), STANDARD, "Eccentricity");
         dataset.setValue(aux.get("A1"), STANDARD, "Area Ratio");
         dataset.setValue(aux.get("A2"), STANDARD, "Aspect Ratio");

         dataset.setValue(r, SAMPLE, "Roudness");
         dataset.setValue(e, SAMPLE, "Eccentricity");
         dataset.setValue(a1, SAMPLE, "Area Ratio");
         dataset.setValue(a2, SAMPLE, "Aspect Ratio");

         chart = ChartFactory.createLineChart("Pizza Base Types", "Statisticals Descriptors","Categories", dataset, PlotOrientation.VERTICAL, true,true, false);
     }

    public JFreeChart getChart(){
        
        return chart;
    }



}
