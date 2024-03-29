/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UmbralBox.java
 *
 * Created on 25-dic-2009, 14:09:43
 */
package clasificaciondepizzas;

import clasificaciondepizzas.models.BaseModel;
import clasificaciondepizzas.models.SauceAndToppingModel;
import clasificaciondepizzas.types.images.ImageType;
import com.sun.media.jai.widget.DisplayJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import clasificaciondepizzas.operations.ImageOperations;

/**
 *
 * @author Manuel
 */
public class ClasificacionDePizzasThresholdBox extends javax.swing.JDialog {

    /** Creates new form UmbralBox */
    public ClasificacionDePizzasThresholdBox(java.awt.Frame parent) {
        super(parent);
        initComponents();
    }

    public void showCannyEdgeDetectorBox() {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        cannyEdgeDetectorBox = new ClasificacionDePizzasCannyEdgeDetectorBox(mainFrame);
        cannyEdgeDetectorBox.setLocationRelativeTo(this);
        image = ImageOperations.threshold(image, thresholdSlider.getValue());
        cannyEdgeDetectorBox.setImage(image);
        cannyEdgeDetectorBox.setBaseModel(bm);

        ClasificacionDePizzasApp.getApplication().show(cannyEdgeDetectorBox);
    }

    public void showSobelEdgeDetectorBox() {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        morphologicalDilationBox = new ClasificacionDePizzasMorphologicalDilationBox(mainFrame);
        morphologicalDilationBox.setLocationRelativeTo(this);
        image = ImageOperations.threshold(image, thresholdSlider.getValue());
        switch(imageType) {
            case SAUCE:
                image = ImageOperations.invert(image);
        }
        morphologicalDilationBox.setImage(image);
        morphologicalDilationBox.setSauceAndToppingModel(stm);

        ClasificacionDePizzasApp.getApplication().show(morphologicalDilationBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        thresholdedImageScrollPane = new javax.swing.JScrollPane();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        thresholdSlider = new javax.swing.JSlider();
        levelTextField = new javax.swing.JTextField();
        levelLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(clasificaciondepizzas.ClasificacionDePizzasApp.class).getContext().getResourceMap(ClasificacionDePizzasThresholdBox.class);
        setTitle(resourceMap.getString("thresholdBox.title")); // NOI18N
        setModal(true);
        setName("thresholdBox"); // NOI18N
        setResizable(false);

        thresholdedImageScrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("thresholdedImageScrollPane.viewportBorder.title"))); // NOI18N
        thresholdedImageScrollPane.setName("thresholdedImageScrollPane"); // NOI18N

        acceptButton.setText(resourceMap.getString("acceptButton.text")); // NOI18N
        acceptButton.setName("acceptButton"); // NOI18N
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        thresholdSlider.setMajorTickSpacing(255);
        thresholdSlider.setMaximum(255);
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setPaintLabels(true);
        thresholdSlider.setSnapToTicks(true);
        thresholdSlider.setValue(128);
        thresholdSlider.setName("thresholdSlider"); // NOI18N
        thresholdSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thresholdSliderStateChanged(evt);
            }
        });

        levelTextField.setText(((Integer)thresholdSlider.getValue()).toString());
        levelTextField.setName("levelTextField"); // NOI18N
        levelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelTextFieldActionPerformed(evt);
            }
        });

        levelLabel.setText(resourceMap.getString("levelLabel.text")); // NOI18N
        levelLabel.setName("levelLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(thresholdedImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(thresholdSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cancelButton)
                            .addComponent(acceptButton))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(levelLabel))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {levelLabel, levelTextField});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {acceptButton, cancelButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(thresholdedImageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acceptButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(thresholdSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(levelLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void thresholdSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thresholdSliderStateChanged
        PlanarImage previewImage;

        levelTextField.setText(((Integer) thresholdSlider.getValue()).toString());

        try {
            // previsualización en miniatura del filtro del umbral
            previewImage = ImageOperations.threshold(thumbnail, thresholdSlider.getValue());
            DisplayJAI display = new DisplayJAI(previewImage);
            thresholdedImageScrollPane.setViewportView(display);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible aplicar el umbral.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible previsualizar la imagen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_thresholdSliderStateChanged

    private void levelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelTextFieldActionPerformed
        PlanarImage previewImage; //previsualización en miniatura con el filtro aplicado
        int value = Integer.parseInt(levelTextField.getText()); // valor del campo de texto

        // el control de desplazamiento se actualiza junto con el campo de texto
        thresholdSlider.setValue(value);

        try {
            // previsualización en miniatura del filtro del umbral
            previewImage = ImageOperations.threshold(thumbnail, thresholdSlider.getValue());
            DisplayJAI display = new DisplayJAI(previewImage);
            thresholdedImageScrollPane.setViewportView(display);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible aplicar el umbral.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible previsualizar la imagen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_levelTextFieldActionPerformed

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        setVisible(false);

        switch (imageType) {
            case BASE:
                showCannyEdgeDetectorBox();
                break;
            case SAUCE:
                // TODO Cambiar por showSobelEdgeDetectorBox();
                showSobelEdgeDetectorBox();
                break;
            case TOPPING:
                showSobelEdgeDetectorBox();
                break;
        }
        
        dispose();
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JTextField levelTextField;
    private javax.swing.JSlider thresholdSlider;
    private javax.swing.JScrollPane thresholdedImageScrollPane;
    // End of variables declaration//GEN-END:variables
    private ClasificacionDePizzasCannyEdgeDetectorBox cannyEdgeDetectorBox;
    private ClasificacionDePizzasSobelEdgeDetectorBox sobelEdgeDetectorBox;
    private ClasificacionDePizzasMorphologicalDilationBox morphologicalDilationBox;
    protected PlanarImage image;
    protected PlanarImage thumbnail;
    private BaseModel bm;
    private SauceAndToppingModel stm;
    private ImageType imageType;

    void setImage(PlanarImage image) {
        this.image = image;
        thumbnail = this.image;
        PlanarImage previewImage; //previsualización en miniatura con el filtro aplicado

        // si la imagen supera el tamaño del panel con desplazamiento se escala a un tamaño menor
        if (image.getWidth() > thresholdedImageScrollPane.getWidth() || image.getHeight() > thresholdedImageScrollPane.getHeight()) {
            // si la imagen supera la anchura del panel con desplazamiento se escala a una anchura menor
            if (image.getWidth() > image.getHeight()) {
                float ratio = (float) thresholdedImageScrollPane.getWidth() / (float) image.getWidth();
                thumbnail = ImageOperations.scale(image, ratio, ratio);
            } // si la imagen supera la altura del panel con desplazamiento se escala a una altura menor
            else {
                float ratio = (float) thresholdedImageScrollPane.getHeight() / (float) image.getHeight();
                thumbnail = ImageOperations.scale(image, ratio, ratio);
            }
        }

        try {
            // previsualización en miniatura del filtro del umbral
            previewImage = ImageOperations.threshold(thumbnail, thresholdSlider.getValue());
            DisplayJAI display = new DisplayJAI(previewImage);
            thresholdedImageScrollPane.setViewportView(display);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible aplicar el umbral.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "No es posible previsualizar la imagen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void setBaseModel(BaseModel bm) {
        this.bm = bm;
    }

    void setSauceAndToppingModel(SauceAndToppingModel stm) {
        this.stm = stm;
    }

    void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }
}
