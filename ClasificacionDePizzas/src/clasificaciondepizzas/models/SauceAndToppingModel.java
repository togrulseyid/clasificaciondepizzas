/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.models;

import javax.media.jai.PlanarImage;

/**
 *
 * @author Manuel
 */
public class SauceAndToppingModel {

    private PlanarImage originalImage;
    private double meanOfHue;
    private double standardDeviationOfHue;
    private double meanOfSaturation;
    private double standardDeviationOfSaturation;
    private boolean modified;

    public SauceAndToppingModel() {
        meanOfHue = 0;
        standardDeviationOfHue = 0;
        meanOfSaturation = 0;
        standardDeviationOfSaturation = 0;
        modified = false;
    }

    public double getMeanOfHue() {
        return meanOfHue;
    }

    public void setMeanOfHue(double meanOfHue) {
        this.meanOfHue = meanOfHue;
    }

    public double getMeanOfSaturation() {
        return meanOfSaturation;
    }

    public void setMeanOfSaturation(double meanOfSaturation) {
        this.meanOfSaturation = meanOfSaturation;
    }

    public double getStandardDeviationOfHue() {
        return standardDeviationOfHue;
    }

    public void setStandardDeviationOfHue(double standardDeviationOfHue) {
        this.standardDeviationOfHue = standardDeviationOfHue;
    }

    public double getStandardDeviationOfSaturation() {
        return standardDeviationOfSaturation;
    }

    public void setStandardDeviationOfSaturation(double standardDeviationOfSaturation) {
        this.standardDeviationOfSaturation = standardDeviationOfSaturation;
    }

    public PlanarImage getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(PlanarImage originalImage) {
        this.originalImage = originalImage;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified() {
        modified = true;
    }
}
