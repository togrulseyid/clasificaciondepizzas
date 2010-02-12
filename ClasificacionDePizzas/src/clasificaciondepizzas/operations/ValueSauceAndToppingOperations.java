/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.operations;

import java.awt.Transparency;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.renderable.ParameterBlock;
import java.text.NumberFormat;
import javax.media.jai.Histogram;
import javax.media.jai.IHSColorSpace;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 *
 * @author Manuel
 */
public class ValueSauceAndToppingOperations {

    private PlanarImage image;
    private PlanarImage hsv;
    private Histogram histogram;

    public ValueSauceAndToppingOperations(PlanarImage image) {
        this.image = image;
        toHSV();
        histogram();
    }

    private void toHSV() {
        IHSColorSpace ihs = IHSColorSpace.getInstance();
        ColorModel IHSColorModel = new ComponentColorModel(ihs,
                new int[]{8, 8, 8},
                false, false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        pb.add(IHSColorModel);
        hsv = JAI.create("ColorConvert", pb);
    }

    private void histogram() {
        // Get the band count.
        int numBands = hsv.getNumBands();

        // Allocate histogram memory.
        int[] numBins = new int[numBands];
        double[] lowValue = new double[numBands];
        double[] highValue = new double[numBands];
        for (int i = 0; i < numBands; i++) {
            numBins[i] = 256;
            lowValue[i] = 0.0;
            highValue[i] = 256.0;
        }

        // Create the Histogram object.
        histogram = new Histogram(numBins, lowValue, highValue);

        // Create the histogram op.
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(hsv);
        parameters.add(null); //región de interés
        parameters.add(1); //sampleado
        parameters.add(1); //período
        parameters.add(numBins); //número de valores en cada canal
        parameters.add(lowValue); //valor menor en cada canal
        parameters.add(highValue); //valor mayor en cada canal
        PlanarImage histImage = JAI.create("Histogram", parameters);

        histogram = (Histogram) histImage.getProperty("histogram");
    }

    public double meanOfHue() {
        return histogram.getMean()[0];
    }

    public double standardDeviationOfHue() {
        return histogram.getStandardDeviation()[0];
    }

    public double meanOfSaturation() {
        return histogram.getMean()[1];
    }

    public double standardDeviationOfSaturation() {
        return histogram.getStandardDeviation()[1];
    }
}
