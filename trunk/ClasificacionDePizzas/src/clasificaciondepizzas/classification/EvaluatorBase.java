/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.classification;

import java.util.Hashtable;
import clasificaciondepizzas.classification.base.FlowingPizza;
import clasificaciondepizzas.classification.base.PoorAlignment;
import clasificaciondepizzas.classification.base.PoorPressing;
import clasificaciondepizzas.classification.base.Standard;

/**
 *
 * @author Alejandro
 */
public class EvaluatorBase {

    private int pr;
    private int pe;
    private int pa1;
    private int pa2;
    private FlowingPizza flowing;
    private PoorAlignment alignment;
    private PoorPressing pressing;
    private Standard standard;
    private Hashtable<String, Double> flowingMap;
    private Hashtable<String, Double> alignmentMap;
    private Hashtable<String, Double> pressingMap;
    private Hashtable<String, Double> standardMap;
    private double[] result;
    private String[] nomb;

    public EvaluatorBase(int pr, int pe, int pa1, int pa2) {
        this.pr = pr;
        this.pe = pe;
        this.pa1 = pa1;
        this.pa2 = pa2;
        flowing = new FlowingPizza();
        alignment = new PoorAlignment();
        pressing = new PoorPressing();
        standard = new Standard();
        flowingMap = flowing.getHash();
        alignmentMap = alignment.getHash();
        pressingMap = pressing.getHash();
        standardMap = standard.getHash();
        result = new double[4];
        nomb = new String[4];
        nomb[0] = "Base flotante";
        nomb[1] = "Base con falta de alineamiento";
        nomb[2] = "Base con falta de prensado";
        nomb[3] = "Base standard";
    }

    public String pizzaCalculate(double roundness, double excentricity, double areaRatio, double aspectRatio) {

        int i;
        int pointer = 0;
        double min = Double.MAX_VALUE;
        result[0] = minError(flowingMap.get("R"), roundness) * pr + minError(flowingMap.get("E") * pe, excentricity) + minError(flowingMap.get("A1") * pa1, areaRatio) + minError(flowingMap.get("A2"), aspectRatio) * pa2;

        result[1] = minError(alignmentMap.get("R"), roundness) * pr + minError(alignmentMap.get("E") * pe, excentricity) + minError(alignmentMap.get("A1") * pa1, areaRatio) + minError(alignmentMap.get("A2"), aspectRatio) * pa2;

        result[2] = minError(pressingMap.get("R"), roundness) * pr + minError(pressingMap.get("E") * pe, excentricity) + minError(pressingMap.get("A1") * pa1, areaRatio) + minError(pressingMap.get("A2"), aspectRatio) * pa2;

        result[3] = minError(standardMap.get("R"), roundness) * pr + minError(standardMap.get("E") * pe, excentricity) + minError(standardMap.get("A1") * pa1, areaRatio) + minError(standardMap.get("A2"), aspectRatio) * pa2;

        for (i = 0; i < result.length; i++) {
            if (result[i] < min) {
                pointer = i;
                min = result[i];
            }
        }

        return nomb[pointer];
    }

    private double minError(double dat, double mean) {

        return Math.abs(dat - mean);
    }
}

