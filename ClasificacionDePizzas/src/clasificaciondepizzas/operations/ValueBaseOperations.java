/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificaciondepizzas.operations;

import java.text.NumberFormat;
import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;

/**
 *
 * @author Manuel
 */
public class ValueBaseOperations {

    private TiledImage image;
    private int area;
    private int height;
    private int width;
    private int minDiameter;
    private int maxDiameter;
    private int offsetX;
    private int offsetY;

    public ValueBaseOperations(PlanarImage image) {
        this.image = new TiledImage(image, true);
        area();
        height();
        width();
        diameters();
    }

    /*
     * Calcula el área encerrada entre los bordes exteriores de la imagen.
     */
    private void area() {
        for (int i = 0; i < image.getHeight(); i++) {
            int numEdges = 0; // bordes encontrados durante el recorrido horizontal
            int blacksInside = 0; // número de píxeles negros en el interior del borde más exterior (no cuentan los píxeles de los agujeros)

            for (int j = 0; j < image.getWidth(); j++) {
                // si el píxel actual es blanco
                if (image.getSample(j, i, 0) != 0) {
                    // se recorre la ristra de píxeles blancos
                    while (image.getSample(j, i, 0) != 0 && j < image.getWidth()) {
                        j++; // se desplaza j hasta el siguiente píxel negro
                    }

                    numEdges++; // una ristra de píxeles blancos se considera un borde
                    j--; // se adecúa el valor para cuadrar con la actualización del bucle

                    /*
                     * para evitar contar píxeles negros de agujeros solo se cuentan
                     * aquellos que vienen precedidos de un número impar de bordes
                     */
                } else if (image.getSample(j, i, 0) == 0 && numEdges % 2 != 0) {
                    blacksInside++;
                }
            }

            /*
             * solo se suman al área los píxeles negros interiores de una línea
             * bien formada (aquella cuyo número de bordes sea par)
             */
            if (numEdges % 2 == 0) {
                area += blacksInside;
            }
        }
    }

    /*
     * Calcula la altura y el desplazamiento del borde superior respecto al
     * límite superior de la imagen.
     */
    private void height() {
        offsetX = Integer.MAX_VALUE;

        /*
         * se recorre la matriz horizontalmente y se cuenta el primer píxel
         * blanco de cada fila
         */
        for (int i = 0; i < image.getHeight(); i++) {
            boolean edge = false;

            for (int j = 0; j < image.getWidth() && !edge; j++) {
                if (image.getSample(j, i, 0) != 0) {
                    edge = true; // tras el primer píxel blanco de la fila para
                    height++;

                    // distancia del punto del borde más cercano a la izquierda
                    if (j < offsetX) {
                        offsetX = j;
                    }
                }
            }
        }

        height -= 2; // se restan los bordes superior e inferior
    }

    /*
     * Calcula la anchura de una imagen a la que se le han detectado los bordes
     * previamente.
     */
    private void width() {
        offsetY = Integer.MAX_VALUE;

        /*
         * se recorre la matriz verticalmente y se cuenta el primer píxel
         * blanco de cada columna
         */
        for (int j = 0; j < image.getWidth(); j++) {
            boolean edge = false;

            for (int i = 0; i < image.getHeight() && !edge; i++) {
                if (image.getSample(j, i, 0) != 0) {
                    edge = true;
                    width++;

                    // distancia del punto del borde más cercano a arriba
                    if (i < offsetY) {
                        offsetY = i;
                    }
                }
            }
        }

        width -= 2; // se restan los bordes izquierdo y derecho
    }

    private void diameters() {
        int centerX = Math.round(width / 2) + offsetX;
        int centerY = Math.round(height / 2) + offsetY;

        //calculo del radio maximo
        int diameterVertical = 0;
        int diameterHorizontal = 0;
        boolean edge = false;

        //radio vertical de centro hacia borde superior
        for (int j = centerY; j >= 0 && !edge; j--) {

            if (image.getSample(centerX, j, 0) != 0) {
                edge = true;
            } else {
                diameterVertical++;
            }
        }

        edge = false;

        /*
         * diámetro vertical
         * (radio vertical de centro hacia borde superior +
         *  radio vertical de centro hacia borde inferior)
         */
        for (int j = centerY + 1; j < image.getHeight() && !edge; j++) {
            if (image.getSample(centerX, j, 0) != 0) {
                edge = true;
            } else {
                diameterVertical++;
            }
        }

        edge = false;

        //radio horizontal de centro hacia borde izquierdo
        for (int i = centerX; i > 0 && !edge; i--) {
            if (image.getSample(i, centerY, 0) != 0) {
                edge = true;
            } else {
                diameterHorizontal++;
            }
        }

        edge = false;

        /*
         * diámetro horizontal
         * (radio horizontal de centro hacia borde izquiero +
         *  radio horizontal de centro hacia borde derecho)
         */
        for (int i = centerX + 1; i < image.getWidth() && !edge; i++) {
            if (image.getSample(i, centerY, 0) != 0) {
                edge = true;
            } else {
                diameterHorizontal++;
            }
        }

        if (diameterHorizontal > diameterVertical) {
            minDiameter = diameterVertical;
            maxDiameter = diameterHorizontal;
        } else {
            minDiameter = diameterHorizontal;
            maxDiameter = diameterVertical;
        }
    }

    /*
     * Devuelve el areaRatio de la imagen image
     */
    public double areaRatio() {
        return (double) area / ((double) maxDiameter * (double) minDiameter);
    }

    /*
     * Devuelve el aspectRatio de la imagen image
     */
    public double aspectRatio() {
        return (double) maxDiameter / (double) minDiameter;
    }

    /*
     * Devuelve la excentricidad de la imagen image
     */
    public double eccentricity() {
        return Math.sqrt(1 - Math.pow((double) minDiameter / 2., 2) / Math.pow((double) maxDiameter / 2., 2));
    }

    /*
     * Devuelve la redondez de la imagen image
     */
    public double roudness() {
        return (double) (4 * area) / (Math.pow((double) maxDiameter / 2., 2) * Math.PI);
    }
}
