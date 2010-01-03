/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.RenderingHints;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.BorderExtender;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;

/**
 *
 * @author Manuel
 */
public class Operations {

    /* Aplica un filtro de la mediana sobre la imagen image devolviendo una
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

    /* Aplica un escalado sobre la imagen image devolviendo una imagen de
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

    /* Aplica un umbralizado sobre la imagen image devolviendo una imagen
     * binarizada para un valor de umbral threshold.
     */
    public static PlanarImage threshold(PlanarImage image, double threshold) {
        //matriz para convertir a escala de grises
        final double[][] rgbToGray = {{ 1./3, 1./3, 1./3, 0 }};

        //se convierte la imagen RGB (3 canales) a una imagen en escala de grises (1 canal)
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);
        parameters.add(rgbToGray);

        image = JAI.create("BandCombine", parameters);

        //se binariza la imagen en escala de grises en función del valor threshold
        //parámetros de la operación
        parameters = new ParameterBlock();
        parameters.addSource(image);
        parameters.add(threshold);

        return JAI.create("Binarize", parameters);
    }

    /* Aplica un detector de bordes sobre la imagen image devolviendo una
     * imagen con el detector aplicado.
     */
    public static PlanarImage edge(PlanarImage image) {
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(image);

        return JAI.create("GradientMagnitude", parameters);
    }
}
