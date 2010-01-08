/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Manuel
 */
public class BaseModel {

    private double areaRatio;
    private double aspectRatio;
    private double eccentricity;
    private double roundness;
    public static final String PROP_AREARATIO = "areaRatio";
    public static final String PROP_ASPECTRATIO = "aspectRatio";
    public static final String PROP_ECCENTRICITY = "eccentricity";
    public static final String PROP_ROUNDNESS = "roundness";

    public BaseModel() {
        areaRatio = 0;
        aspectRatio = 0;
        eccentricity = 0;
        roundness = 0;
    }

    public BaseModel(double roundness, double eccentricity, double areaRatio, double aspectRatio) {
        this.areaRatio = areaRatio;
        this.aspectRatio = aspectRatio;
        this.eccentricity = eccentricity;
        this.roundness = roundness;
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
        double oldAreaRatio = this.areaRatio;
        this.areaRatio = areaRatio;
        propertyChangeSupport.firePropertyChange(PROP_AREARATIO, oldAreaRatio, areaRatio);
    }
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
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
        double oldAspectRatio = this.aspectRatio;
        this.aspectRatio = aspectRatio;
        propertyChangeSupport.firePropertyChange(PROP_ASPECTRATIO, oldAspectRatio, aspectRatio);
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
        double oldEccentricity = this.eccentricity;
        this.eccentricity = eccentricity;
        propertyChangeSupport.firePropertyChange(PROP_ECCENTRICITY, oldEccentricity, eccentricity);
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
        double oldRoundness = this.roundness;
        this.roundness = roundness;
        propertyChangeSupport.firePropertyChange(PROP_ROUNDNESS, oldRoundness, roundness);
    }
}
