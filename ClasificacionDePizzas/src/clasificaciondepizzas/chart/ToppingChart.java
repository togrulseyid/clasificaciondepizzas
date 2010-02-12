/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.chart;

import clasificaciondepizzas.classification.topping.ToppingAcceptableOverwipe;
import clasificaciondepizzas.classification.topping.ToppingAcceptableUnderwipe;
import clasificaciondepizzas.classification.topping.ToppingEvenSpread;
import clasificaciondepizzas.classification.topping.ToppingRejectOverwipe;
import clasificaciondepizzas.classification.topping.ToppingRejectUnderwipe;
import java.util.Hashtable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Alejandro
 */
public class ToppingChart {

    private static final String AOVER = "Acceptable Overwipe";
    private static final String AUNDER = "Acceptable Underwipe";
    private static final String ESPREAD = "Even Spread";
    private static final String ROVER = "Reject Overwipe";
    private static final String RUNDER = "Reject Underwipe";
    private static final String SAMPLE = "My Pizza";
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JFreeChart chart;

    public ToppingChart(double mh, double stdh, double ms, double stds) {


        ToppingAcceptableOverwipe aOver = new ToppingAcceptableOverwipe();
        ToppingAcceptableUnderwipe aUnder = new ToppingAcceptableUnderwipe();
        ToppingEvenSpread eSpread = new ToppingEvenSpread();
        ToppingRejectOverwipe rOver = new ToppingRejectOverwipe();
        ToppingRejectUnderwipe rUnder = new ToppingRejectUnderwipe();
        Hashtable<String, Double> aux;

        aux = aOver.getHash();
        dataset.setValue(aux.get("MH"), AOVER, "Mean of H colour component");
        dataset.setValue(aux.get("STDH"), AOVER, "Standard deviation of H colour component");
        dataset.setValue(aux.get("MS"), AOVER, "Mean of S colour component");
        dataset.setValue(aux.get("STDS"), AOVER, "Standard deviation of S colour component");

        aux = aUnder.getHash();
        dataset.setValue(aux.get("MH"), AUNDER, "Mean of H colour component");
        dataset.setValue(aux.get("STDH"), AUNDER, "Standard deviation of H colour component");
        dataset.setValue(aux.get("MS"), AUNDER, "Mean of S colour component");
        dataset.setValue(aux.get("STDS"), AUNDER, "Standard deviation of S colour component");


        aux = eSpread.getHash();
        dataset.setValue(aux.get("MH"), ESPREAD, "Mean of H colour component");
        dataset.setValue(aux.get("STDH"), ESPREAD, "Standard deviation of H colour component");
        dataset.setValue(aux.get("MS"), ESPREAD, "Mean of S colour component");
        dataset.setValue(aux.get("STDS"), ESPREAD, "Standard deviation of S colour component");


        aux = rOver.getHash();
        dataset.setValue(aux.get("MH"), ROVER, "Mean of H colour component");
        dataset.setValue(aux.get("STDH"), ROVER, "Standard deviation of H colour component");
        dataset.setValue(aux.get("MS"), ROVER, "Mean of S colour component");
        dataset.setValue(aux.get("STDS"), ROVER, "Standard deviation of S colour component");

        aux = rUnder.getHash();
        dataset.setValue(aux.get("MH"), RUNDER, "Mean of H colour component");
        dataset.setValue(aux.get("STDH"), RUNDER, "Standard deviation of H colour component");
        dataset.setValue(aux.get("MS"), RUNDER, "Mean of S colour component");
        dataset.setValue(aux.get("STDS"), RUNDER, "Standard deviation of S colour component");



        dataset.setValue(mh, SAMPLE, "Mean of H colour component");
        dataset.setValue(stdh, SAMPLE, "Standard deviation of H colour component");
        dataset.setValue(ms, SAMPLE, "Mean of S colour component");
        dataset.setValue(stds, SAMPLE, "Standard deviation of S colour component");

        chart = ChartFactory.createLineChart("Pizza Topping", "Statisticals Descriptors", "Categories", dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    public JFreeChart getChart() {

        return chart;
    }
}
