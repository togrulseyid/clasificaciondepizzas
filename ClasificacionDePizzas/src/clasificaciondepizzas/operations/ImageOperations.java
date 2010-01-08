/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.operations;

import clasificaciondepizzas.operations.canny.Canny;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.BorderExtender;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;

/**
 *
 * @author Manuel
 */
public class ImageOperations {

    /*
     * Aplica un filtro de la mediana sobre la imagen image devolviendo una
     * imagen con el filtro aplicado.
     */
    public static PlanarImage medianFilter(PlanarImage image, int radius) {
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);
        parameters.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
        parameters.add(radius);

        RenderingHints hint = new RenderingHints(JAI.KEY_BORDER_EXTENDER,
                BorderExtender.createInstance(BorderExtender.BORDER_COPY));

        return JAI.create("MedianFilter", parameters, hint);
    }

    /*
     * Aplica un escalado sobre la imagen image devolviendo una imagen de
     * anchura ancho_original * xScale y altura altura_original * yScale.
     */
    public static PlanarImage scale(PlanarImage image, float xScale, float yScale) {
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);
        parameters.add(xScale);
        parameters.add(yScale);

        return JAI.create("Scale", parameters);
    }

    /*
     * Aplica un umbralizado sobre la imagen image devolviendo una imagen
     * binarizada para un valor de umbral threshold.
     */
    public static PlanarImage threshold(PlanarImage image, double threshold) {
        //se binariza la imagen en escala de grises en función del valor threshold
        //parámetros de la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters = new ParameterBlock();
        parameters.addSource(toGray(image));
        parameters.add(threshold);

        return JAI.create("Binarize", parameters);
    }

    /*
     * Aplica el detector de bordes de Sobel sobre la imagen image devolviendo
     * una imagen con el detector aplicado.
     */
    public static PlanarImage sobelEdge(PlanarImage image) {
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);

        return JAI.create("GradientMagnitude", parameters);
    }

    /*
     * Aplica el detector de bordes de Canny sobre la imagen image devolviendo
     * una imagen con el detector aplicado.
     */
    public static PlanarImage cannyEdge(PlanarImage image) {
        int offset = 0;
        int size = 7;
        float theta = (float) 0.45;
        int lowthresh = 10;
        int highthresh = 145;
        float scale = 1;
        int width = image.getWidth();
        int height = image.getHeight();

        int picsize = image.getWidth() * image.getHeight();
        int[] data = new int[picsize];

        Canny canny = new Canny();

        byte[] rgbArray = (byte[]) image.getData().getDataElements(0, 0, width, height, null);
        data = canny.apply_canny(rgbArray, width, height, size, theta, lowthresh, highthresh, scale, offset);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getWritableTile(0, 0).setDataElements(0, 0, width, height, data);

        ParameterBlock paramBand = new ParameterBlock();
        paramBand = new ParameterBlock();
        paramBand.addSource(toGray(bufferedImage));
        paramBand.add(1.0);
        return JAI.create("Binarize", paramBand);
    }

    /*
     * Transforma una imagen de 1, 3 o 4 canales de tipo PlanarImage a una
     * imagen en escala de grises.
     */
    private static PlanarImage toGray(PlanarImage image) {
        //matriz para convertir a escala de grises
        double[][] grayToGray = {{1}};
        double[][] rgbToGray = {{0.299, 0.587, 0.114, 0}};
        double[][] alphaToGray = {{0.299, 0.587, 0.114, 0, 0}};

        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);

        switch (image.getNumBands()) {
            case 1:
                parameters.add(grayToGray);
                break;
            case 4:
                parameters.add(alphaToGray);
                break;
            default:
                parameters.add(rgbToGray);
        }

        return JAI.create("BandCombine", parameters);
    }

    /*
     * Transforma una imagen de 1, 3 o 4 canales de tipo BufferedImage a una
     * imagen en escala de grises.
     */
    private static PlanarImage toGray(BufferedImage image) {
        //matriz para convertir a escala de grises
        double[][] grayToGray = {{1}};
        double[][] rgbToGray = {{0.299, 0.587, 0.114, 0}};
        double[][] alphaToGray = {{0.299, 0.587, 0.114, 0, 0}};

        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);

        switch (image.getColorModel().getNumComponents()) {
            case 1:
                parameters.add(grayToGray);
                break;
            case 4:
                parameters.add(alphaToGray);
                break;
            default:
                parameters.add(rgbToGray);
        }

        return JAI.create("BandCombine", parameters);
    }
}
