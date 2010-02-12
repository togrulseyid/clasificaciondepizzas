/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.models;

/**
 *
 * @author Manuel
 */
public class BaseModel {

    private double areaRatio;
    private double aspectRatio;
    private double eccentricity;
    private double roundness;
    private boolean modified;

    public BaseModel() {
        areaRatio = 0;
        aspectRatio = 0;
        eccentricity = 0;
        roundness = 0;
        modified = false;
    }

    public BaseModel(double roundness, double eccentricity, double areaRatio, double aspectRatio) {
        this.areaRatio = areaRatio;
        this.aspectRatio = aspectRatio;
        this.eccentricity = eccentricity;
        this.roundness = roundness;
        modified = false;
    }

    /**
     * Get the value of areaRatio
     *
     * @return the value of areaRatio
     */
    public double getAreaRatio() {
        return areaRatio;
    }

    /**
     * Set the value of areaRatio
     *
     * @param areaRatio new value of areaRatio
     */
    public void setAreaRatio(double areaRatio) {
        this.areaRatio = areaRatio;
    }
    
    /**
     * Get the value of aspectRatio
     *
     * @return the value of aspectRatio
     */
    public double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * Set the value of aspectRatio
     *
     * @param aspectRatio new value of aspectRatio
     */
    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Get the value of eccentricity
     *
     * @return the value of eccentricity
     */
    public double getEccentricity() {
        return eccentricity;
    }

    /**
     * Set the value of eccentricity
     *
     * @param eccentricity new value of eccentricity
     */
    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
    }

    /**
     * Get the value of roundness
     *
     * @return the value of roundness
     */
    public double getRoundness() {
        return roundness;
    }

    /**
     * Set the value of roundness
     *
     * @param roundness new value of roundness
     */
    public void setRoundness(double roundness) {
        this.roundness = roundness;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified() {
        modified = true;
    }
}
