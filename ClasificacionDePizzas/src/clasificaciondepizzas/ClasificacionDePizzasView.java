/*
 * ClasificacionDePizzasView.java
 */
package clasificaciondepizzas;

import clasificaciondepizzas.models.BaseModel;
import clasificaciondepizzas.models.SauceAndToppingModel;
import com.sun.media.jai.widget.DisplayJAI;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The application's main frame.
 */
public class ClasificacionDePizzasView extends FrameView {

    public ClasificacionDePizzasView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
            aboutBox = new ClasificacionDePizzasAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ClasificacionDePizzasApp.getApplication().show(aboutBox);
    }

    public void showMedianFilterBox() {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        medianFilterBox = new ClasificacionDePizzasMedianFilterBox(mainFrame);
        medianFilterBox.setLocationRelativeTo(mainFrame);
        medianFilterBox.setImage(baseImage);

        bm = new BaseModel();
        medianFilterBox.setBaseModel(bm);

        ClasificacionDePizzasApp.getApplication().show(medianFilterBox);
        appAreaRatioLabel.setText(String.valueOf(bm.getAreaRatio()));
        appAspectRatioLabel.setText(String.valueOf(bm.getAspectRatio()));
        appEccentricityLabel.setText(String.valueOf(bm.getEccentricity()));
        appRoundnessLabel.setText(String.valueOf(bm.getRoundness()));
        
        if(!appAreaRatioLabel.getText().equals("0.0") && !appAspectRatioLabel.getText().equals("0.0")
                && !appEccentricityLabel.getText().equals("0.0") && !appRoundnessLabel.getText().equals("0.0")) {
        classifyButton.setEnabled(true);            
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        baseImageScrollPane = new javax.swing.JScrollPane();
        areaRatioLabel = new javax.swing.JLabel();
        aspectRatioLabel = new javax.swing.JLabel();
        eccentricityLabel = new javax.swing.JLabel();
        roundnessLabel = new javax.swing.JLabel();
        appAreaRatioLabel = new javax.swing.JLabel();
        appAspectRatioLabel = new javax.swing.JLabel();
        appEccentricityLabel = new javax.swing.JLabel();
        appRoundnessLabel = new javax.swing.JLabel();
        classifyButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        fileMenuSeparator = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(clasificaciondepizzas.ClasificacionDePizzasApp.class).getContext().getResourceMap(ClasificacionDePizzasView.class);
        baseImageScrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("baseImageScrollPane.viewportBorder.title"))); // NOI18N
        baseImageScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        baseImageScrollPane.setName("baseImageScrollPane"); // NOI18N
        baseImageScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                baseImageScrollPaneMouseClicked(evt);
            }
        });

        areaRatioLabel.setText(resourceMap.getString("areaRatioLabel.text")); // NOI18N
        areaRatioLabel.setName("areaRatioLabel"); // NOI18N

        aspectRatioLabel.setText(resourceMap.getString("aspectRatioLabel.text")); // NOI18N
        aspectRatioLabel.setName("aspectRatioLabel"); // NOI18N

        eccentricityLabel.setText(resourceMap.getString("eccentricityLabel.text")); // NOI18N
        eccentricityLabel.setName("eccentricityLabel"); // NOI18N

        roundnessLabel.setText(resourceMap.getString("roundnessLabel.text")); // NOI18N
        roundnessLabel.setName("roundnessLabel"); // NOI18N

        appAreaRatioLabel.setText(resourceMap.getString("appAreaRatioLabel.text")); // NOI18N
        appAreaRatioLabel.setName("appAreaRatioLabel"); // NOI18N

        appAspectRatioLabel.setText(resourceMap.getString("appAspectRatioLabel.text")); // NOI18N
        appAspectRatioLabel.setName("appAspectRatioLabel"); // NOI18N

        appEccentricityLabel.setText(resourceMap.getString("appEccentricityLabel.text")); // NOI18N
        appEccentricityLabel.setName("appEccentricityLabel"); // NOI18N

        appRoundnessLabel.setText(resourceMap.getString("appRoundnessLabel.text")); // NOI18N
        appRoundnessLabel.setName("appRoundnessLabel"); // NOI18N

        classifyButton.setText(resourceMap.getString("classifyButton.text")); // NOI18N
        classifyButton.setEnabled(false);
        classifyButton.setName("classifyButton"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(baseImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addComponent(classifyButton)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aspectRatioLabel)
                            .addComponent(eccentricityLabel)
                            .addComponent(roundnessLabel)
                            .addComponent(areaRatioLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(appRoundnessLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(appAspectRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(appEccentricityLabel))
                            .addComponent(appAreaRatioLabel))))
                .addContainerGap())
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {areaRatioLabel, aspectRatioLabel, eccentricityLabel, roundnessLabel});

        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(baseImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(appAreaRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(areaRatioLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aspectRatioLabel)
                    .addComponent(appAspectRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eccentricityLabel)
                    .addComponent(appEccentricityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundnessLabel)
                    .addComponent(appRoundnessLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classifyButton)
                .addContainerGap())
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appAspectRatioLabel, appEccentricityLabel});

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setMnemonic('A');
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(clasificaciondepizzas.ClasificacionDePizzasApp.class).getContext().getActionMap(ClasificacionDePizzasView.class, this);
        openMenuItem.setAction(actionMap.get("open")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setToolTipText(resourceMap.getString("openMenuItem.toolTipText")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        fileMenuSeparator.setName("fileMenuSeparator"); // NOI18N
        fileMenu.add(fileMenuSeparator);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setToolTipText(resourceMap.getString("exitMenuItem.toolTipText")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('u');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setToolTipText(resourceMap.getString("aboutMenuItem.toolTipText")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void baseImageScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baseImageScrollPaneMouseClicked
        open();
    }//GEN-LAST:event_baseImageScrollPaneMouseClicked

    @Action
    public void open() {
        JFileChooser fileChooser = new JFileChooser("."); // selector de archivos
        File file = null; // archivo seleccionado

        // desactiva la opción "Todos los archivos"
        fileChooser.setAcceptAllFileFilterUsed(false);

        // asigna la opción "Todas las imágenes"
        FileFilter filter = new FileNameExtensionFilter("Todas las imágenes", "png", "bmp", "jpeg", "jpg", "tif", "tiff", "gif");
        fileChooser.setFileFilter(filter);

        int status = fileChooser.showOpenDialog(null);

        // se ha seleccionado un archivo
        if (status == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();

            // el archivo se abre como una imagen.
            try {
                baseImage = JAI.create("fileload", file.getPath());
            } catch (Exception e) {
                JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();

                JOptionPane.showMessageDialog(mainFrame,
                        "No se puede abrir la imagen " + file.getName() + ".",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // muestra la imagen en un JScrollPane
            try {
                // crea una visualización de la imagen
                DisplayJAI display = new DisplayJAI(baseImage);
                // asigna la visualización al JScrollPane
                baseImageScrollPane.setViewportView(display);
            } catch (RuntimeException e) {
                JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();

                JOptionPane.showMessageDialog(mainFrame,
                        file.getName() + " no es un archivo de imagen válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            showMedianFilterBox();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appAreaRatioLabel;
    private javax.swing.JLabel appAspectRatioLabel;
    private javax.swing.JLabel appEccentricityLabel;
    private javax.swing.JLabel appRoundnessLabel;
    private javax.swing.JLabel areaRatioLabel;
    private javax.swing.JLabel aspectRatioLabel;
    private javax.swing.JScrollPane baseImageScrollPane;
    private javax.swing.JButton classifyButton;
    private javax.swing.JLabel eccentricityLabel;
    private javax.swing.JPopupMenu.Separator fileMenuSeparator;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel roundnessLabel;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private ClasificacionDePizzasMedianFilterBox medianFilterBox;
    private PlanarImage baseImage;
    private BaseModel bm;
    private SauceAndToppingModel sm;
    private SauceAndToppingModel tm;
}
