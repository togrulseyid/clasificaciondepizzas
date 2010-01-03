/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClasificacionDePizzasEdgeDetectorBox.java
 *
 * Created on 03-ene-2010, 3:06:38
 */
package clasificaciondepizzas;

import com.sun.media.jai.widget.DisplayJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JOptionPane;
import tools.Operations;

/**
 *
 * @author Manuel
 */
public class ClasificacionDePizzasEdgeDetectorBox extends javax.swing.JDialog {

    /** Creates new form ClasificacionDePizzasEdgeDetectorBox */
    public ClasificacionDePizzasEdgeDetectorBox(java.awt.Frame parent) {
        super(parent);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        edgeImageScrollPane = new javax.swing.JScrollPane();
        acceptButton = new javax.swing.JToggleButton();
        cancelButton = new javax.swing.JToggleButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(clasificaciondepizzas.ClasificacionDePizzasApp.class).getContext().getResourceMap(ClasificacionDePizzasEdgeDetectorBox.class);
        setTitle(resourceMap.getString("edgeDetectorBox.title")); // NOI18N
        setModal(true);
        setName("edgeDetectorBox"); // NOI18N
        setResizable(false);

        edgeImageScrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("edgeImageScrollPane.viewportBorder.title"))); // NOI18N
        edgeImageScrollPane.setName("edgeImageScrollPane"); // NOI18N
        edgeImageScrollPane.setPreferredSize(new java.awt.Dimension(18, 32));

        acceptButton.setText(resourceMap.getString("acceptButton.text")); // NOI18N
        acceptButton.setName("acceptButton"); // NOI18N

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(edgeImageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(acceptButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {acceptButton, cancelButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acceptButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(edgeImageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton acceptButton;
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JScrollPane edgeImageScrollPane;
    // End of variables declaration//GEN-END:variables
    protected PlanarImage image;
    protected PlanarImage thumbnail;

    void setImage(PlanarImage image) {
        this.image = image;
        thumbnail = this.image;
        PlanarImage previewImage; //previsualización en miniatura con el filtro aplicado

        // si la imagen supera el tamaño del panel con desplazamiento se escala a un tamaño menor
        if (image.getWidth() > edgeImageScrollPane.getWidth() || image.getHeight() > edgeImageScrollPane.getHeight()) {
            // si la imagen supera la anchura del panel con desplazamiento se escala a una anchura menor
            if (image.getWidth() > image.getHeight()) {
                float ratio = (float) edgeImageScrollPane.getWidth() / (float) image.getWidth();
                thumbnail = Operations.scale(image, ratio, ratio);
            } // si la imagen supera la altura del panel con desplazamiento se escala a una altura menor
            else {
                float ratio = (float) edgeImageScrollPane.getHeight() / (float) image.getHeight();
                thumbnail = Operations.scale(image, ratio, ratio);
            }
        }

        try {
            // previsualización en miniatura del detector de bordes
            previewImage = Operations.edge(thumbnail);
            DisplayJAI display = new DisplayJAI(previewImage);
            edgeImageScrollPane.setViewportView(display);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible aplicar el detector de bordes.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible previsualizar la imagen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}