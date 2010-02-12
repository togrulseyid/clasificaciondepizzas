/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.classification;

import java.util.Hashtable;
import clasificaciondepizzas.classification.topping.ToppingAcceptableOverwipe;
import clasificaciondepizzas.classification.topping.ToppingAcceptableUnderwipe;
import clasificaciondepizzas.classification.topping.ToppingEvenSpread;
import clasificaciondepizzas.classification.topping.ToppingRejectOverwipe;
import clasificaciondepizzas.classification.topping.ToppingRejectUnderwipe;

/**
 *
 * @author Alejandro
 */
public class EvaluatorTopping {

    private ToppingRejectUnderwipe rUnderwipe;
    private ToppingAcceptableUnderwipe aUnderwipe;
    private ToppingEvenSpread eSpread;
    private ToppingRejectOverwipe rOverwipe;
    private ToppingAcceptableOverwipe aOverwipe;
    private Hashtable<String, Double> rUnderMap;
    private Hashtable<String, Double> aUnderMap;
    private Hashtable<String, Double> eSpreadMap;
    private Hashtable<String, Double> rOverMap;
    private Hashtable<String, Double> aOverMap;
    double[] result;
    String[] nomb;
    int PMH;
    int PSTDH;
    int PMS;
    int PSTDS;

    public EvaluatorTopping(int PMH, int PSTDH, int PMS, int PSTDS) {
        this.PMH = PMH;
        this.PSTDH = PSTDH;
        this.PMS = PMS;
        this.PSTDS = PSTDS;
        rUnderwipe = new ToppingRejectUnderwipe();
        aUnderwipe = new ToppingAcceptableUnderwipe();
        eSpread = new ToppingEvenSpread();
        rOverwipe = new ToppingRejectOverwipe();
        aOverwipe = new ToppingAcceptableOverwipe();
        rUnderMap = rUnderwipe.getHash();
        aUnderMap = aUnderwipe.getHash();
        eSpreadMap = eSpread.getHash();
        rOverMap = rOverwipe.getHash();
        aOverMap = aOverwipe.getHash();

        result = new double[5];
        nomb = new String[5];
        nomb[0] = "Desechable por escasez de ingredientes";
        nomb[1] = "Aceptable con escasez de ingredientes";
        nomb[2] = "Ingredientes irregularmente esparcidos";
        nomb[3] = "Aceptable con abundancia de ingredientes";
        nomb[4] = "Desechable con abundancia de ingredientes";
    }

    public String pizzaCalculate(double meanH, double standardDeviationH, double meanS, double standardDeviationS) {

        int i;
        int pointer = 0;
        double min = Double.MAX_VALUE;
        result[0] = minError(rUnderMap.get("MH"), meanH) * PMH + minError(rUnderMap.get("STDH") * PSTDH, standardDeviationH) + minError(rUnderMap.get("MS"), meanS) * PMS + minError(rUnderMap.get("STDS"), standardDeviationS) * PSTDS;

        result[1] = minError(aUnderMap.get("MH"), meanH) * PMH + minError(aUnderMap.get("STDH") * PSTDH, standardDeviationH) + minError(aUnderMap.get("MS"), meanS) * PMS + minError(aUnderMap.get("STDS"), standardDeviationS) * PSTDS;

        result[2] = minError(eSpreadMap.get("MH"), meanH) * PMH + minError(eSpreadMap.get("STDH") * PSTDH, standardDeviationH) + minError(eSpreadMap.get("MS"), meanS) * PMS + minError(eSpreadMap.get("STDS"), standardDeviationS) * PSTDS;

        result[3] = minError(aOverMap.get("MH"), meanH) * PMH + minError(aOverMap.get("STDH") * PSTDH, standardDeviationH) + minError(aOverMap.get("MS"), meanS) * PMS + minError(aOverMap.get("STDS"), standardDeviationS) * PSTDS;

        result[4] = minError(rOverMap.get("MH"), meanH) * PMH + minError(rOverMap.get("STDH") * PSTDH, standardDeviationH) + minError(rOverMap.get("MS"), meanS) * PMS + minError(rOverMap.get("STDS"), standardDeviationS) * PSTDS;


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
