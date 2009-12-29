/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;

/**
 *
 * @author Manuel
 */
public class Operations {

    /* Aplica un filtro de la mediana sobre la imagen original devolviendo una
     * imagen con el filtro aplicado.
     */
    public static PlanarImage medianFilter(PlanarImage original, int radius) {
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(original);
        parameters.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
        parameters.add(radius);

        return JAI.create("MedianFilter", parameters);
    }

    /* Aplica un escalado sobre la imagen original devolviendo una imagen de
     * anchura ancho_original * xScale y altura altura_original * yScale.
     */
    public static PlanarImage scale(PlanarImage original, float xScale, float yScale) {
        //parámetros para la operación
        ParameterBlock parameters = new ParameterBlock();
        parameters.addSource(original);
        parameters.add(xScale);
        parameters.add(yScale);

        return JAI.create("Scale", parameters);
    }
}
