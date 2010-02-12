/*
 * ClasificacionDePizzasView.java
 */
package clasificaciondepizzas;

import clasificaciondepizzas.models.BaseModel;
import clasificaciondepizzas.models.SauceAndToppingModel;
import clasificaciondepizzas.types.images.ImageType;
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

        if (bm != null && bm.isModified()) {
            baseClassifyButton.setEnabled(true);
        }
    }

    public void showThresholdBox(ImageType type) {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        thresholdBox = new ClasificacionDePizzasThresholdBox(mainFrame);
        thresholdBox.setLocationRelativeTo(mainFrame);
        thresholdBox.setImageType(type);

        switch (type) {
            case SAUCE:
                thresholdBox.setImage(sauceImage);
                stm = new SauceAndToppingModel();
                stm.setOriginalImage(sauceImage);
                thresholdBox.setSauceAndToppingModel(stm);
                break;

            case TOPPING:
                thresholdBox.setImage(toppingImage);
                stm = new SauceAndToppingModel();
                stm.setOriginalImage(toppingImage);
                thresholdBox.setSauceAndToppingModel(stm);
                break;
        }

        ClasificacionDePizzasApp.getApplication().show(thresholdBox);

        switch (type) {
            case SAUCE:
                appSauceMeanHLabel.setText(String.valueOf(stm.getMeanOfHue()));
                appSauceStandardDeviationHLabel.setText(String.valueOf(stm.getStandardDeviationOfHue()));
                appSauceMeanSLabel.setText(String.valueOf(stm.getMeanOfHue()));
                appSauceStandardDeviationSLabel.setText(String.valueOf(stm.getStandardDeviationOfSaturation()));

                if (stm != null && stm.isModified()) {
                    sauceClassifyButton.setEnabled(true);
                }

                break;

            case TOPPING:
                appToppingMeanHLabel.setText(String.valueOf(stm.getMeanOfHue()));
                appToppingStandardDeviationHLabel.setText(String.valueOf(stm.getStandardDeviationOfHue()));
                appToppingMeanSLabel.setText(String.valueOf(stm.getMeanOfHue()));
                appToppingStandardDeviationSLabel.setText(String.valueOf(stm.getStandardDeviationOfSaturation()));

                if (stm != null && stm.isModified()) {
                    toppingClassifyButton.setEnabled(true);
                }

                break;
        }
    }

    public void showBaseClassificationBox(double roundness, double eccentricity, double areaRatio, double aspectRatio) {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        baseClassificationBox = new ClasificacionDePizzasBaseClassificationBox(mainFrame, roundness, eccentricity, areaRatio, aspectRatio);

        ClasificacionDePizzasApp.getApplication().show(baseClassificationBox);
    }

    public void showSauceClassificationBox(double meanH, double standardDeviationH, double meanS, double standardDeviationS) {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        sauceClassificationBox = new ClasificacionDePizzasSauceClassificationBox(mainFrame, meanH, standardDeviationH, meanS, standardDeviationS);

        ClasificacionDePizzasApp.getApplication().show(sauceClassificationBox);
    }

    public void showToppingClassificationBox(double meanH, double standardDeviationH, double meanS, double standardDeviationS) {
        JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();
        toppingClassificationBox = new ClasificacionDePizzasToppingClassificationBox(mainFrame, meanH, standardDeviationH, meanS, standardDeviationS);

        ClasificacionDePizzasApp.getApplication().show(toppingClassificationBox);
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
        sauceImageScrollPane = new javax.swing.JScrollPane();
        toppingImageScrollPane = new javax.swing.JScrollPane();
        areaRatioLabel = new javax.swing.JLabel();
        appAreaRatioLabel = new javax.swing.JLabel();
        aspectRatioLabel = new javax.swing.JLabel();
        appAspectRatioLabel = new javax.swing.JLabel();
        eccentricityLabel = new javax.swing.JLabel();
        appEccentricityLabel = new javax.swing.JLabel();
        roundnessLabel = new javax.swing.JLabel();
        appRoundnessLabel = new javax.swing.JLabel();
        baseClassifyButton = new javax.swing.JButton();
        sauceMeanHLabel = new javax.swing.JLabel();
        appSauceMeanHLabel = new javax.swing.JLabel();
        sauceStandardDeviationHLabel = new javax.swing.JLabel();
        appSauceStandardDeviationHLabel = new javax.swing.JLabel();
        sauceMeanSLabel = new javax.swing.JLabel();
        appSauceMeanSLabel = new javax.swing.JLabel();
        sauceStandardDeviationSLabel = new javax.swing.JLabel();
        appSauceStandardDeviationSLabel = new javax.swing.JLabel();
        sauceClassifyButton = new javax.swing.JButton();
        toppingMeanHLabel = new javax.swing.JLabel();
        appToppingMeanHLabel = new javax.swing.JLabel();
        toppingStandardDeviationHLabel = new javax.swing.JLabel();
        appToppingStandardDeviationHLabel = new javax.swing.JLabel();
        toppingMeanSLabel = new javax.swing.JLabel();
        appToppingMeanSLabel = new javax.swing.JLabel();
        toppingStandardDeviationSLabel = new javax.swing.JLabel();
        appToppingStandardDeviationSLabel = new javax.swing.JLabel();
        toppingClassifyButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openBaseMenuItem = new javax.swing.JMenuItem();
        openSauceMenuItem = new javax.swing.JMenuItem();
        openToppingMenuItem = new javax.swing.JMenuItem();
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

        sauceImageScrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("sauceImageScrollPane.viewportBorder.title"))); // NOI18N
        sauceImageScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sauceImageScrollPane.setName("sauceImageScrollPane"); // NOI18N
        sauceImageScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sauceImageScrollPaneMouseClicked(evt);
            }
        });

        toppingImageScrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("toppingImageScrollPane.viewportBorder.title"))); // NOI18N
        toppingImageScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        toppingImageScrollPane.setName("toppingImageScrollPane"); // NOI18N
        toppingImageScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toppingImageScrollPaneMouseClicked(evt);
            }
        });

        areaRatioLabel.setText(resourceMap.getString("areaRatioLabel.text")); // NOI18N
        areaRatioLabel.setName("areaRatioLabel"); // NOI18N

        appAreaRatioLabel.setText(resourceMap.getString("appAreaRatioLabel.text")); // NOI18N
        appAreaRatioLabel.setName("appAreaRatioLabel"); // NOI18N

        aspectRatioLabel.setText(resourceMap.getString("aspectRatioLabel.text")); // NOI18N
        aspectRatioLabel.setName("aspectRatioLabel"); // NOI18N

        appAspectRatioLabel.setText(resourceMap.getString("appAspectRatioLabel.text")); // NOI18N
        appAspectRatioLabel.setName("appAspectRatioLabel"); // NOI18N

        eccentricityLabel.setText(resourceMap.getString("eccentricityLabel.text")); // NOI18N
        eccentricityLabel.setName("eccentricityLabel"); // NOI18N

        appEccentricityLabel.setText(resourceMap.getString("appEccentricityLabel.text")); // NOI18N
        appEccentricityLabel.setName("appEccentricityLabel"); // NOI18N

        roundnessLabel.setText(resourceMap.getString("roundnessLabel.text")); // NOI18N
        roundnessLabel.setName("roundnessLabel"); // NOI18N

        appRoundnessLabel.setText(resourceMap.getString("appRoundnessLabel.text")); // NOI18N
        appRoundnessLabel.setName("appRoundnessLabel"); // NOI18N

        baseClassifyButton.setText(resourceMap.getString("baseClassifyButton.text")); // NOI18N
        baseClassifyButton.setEnabled(false);
        baseClassifyButton.setName("baseClassifyButton"); // NOI18N
        baseClassifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baseClassifyButtonActionPerformed(evt);
            }
        });

        sauceMeanHLabel.setText(resourceMap.getString("sauceMeanHLabel.text")); // NOI18N
        sauceMeanHLabel.setName("sauceMeanHLabel"); // NOI18N

        appSauceMeanHLabel.setText(resourceMap.getString("appSauceMeanHLabel.text")); // NOI18N
        appSauceMeanHLabel.setName("appSauceMeanHLabel"); // NOI18N

        sauceStandardDeviationHLabel.setText(resourceMap.getString("sauceStandardDeviationHLabel.text")); // NOI18N
        sauceStandardDeviationHLabel.setName("sauceStandardDeviationHLabel"); // NOI18N

        appSauceStandardDeviationHLabel.setText(resourceMap.getString("appSauceStandardDeviationHLabel.text")); // NOI18N
        appSauceStandardDeviationHLabel.setName("appSauceStandardDeviationHLabel"); // NOI18N

        sauceMeanSLabel.setText(resourceMap.getString("sauceMeanSLabel.text")); // NOI18N
        sauceMeanSLabel.setName("sauceMeanSLabel"); // NOI18N

        appSauceMeanSLabel.setText(resourceMap.getString("appSauceMeanSLabel.text")); // NOI18N
        appSauceMeanSLabel.setName("appSauceMeanSLabel"); // NOI18N

        sauceStandardDeviationSLabel.setText(resourceMap.getString("sauceStandardDeviationSLabel.text")); // NOI18N
        sauceStandardDeviationSLabel.setName("sauceStandardDeviationSLabel"); // NOI18N

        appSauceStandardDeviationSLabel.setText(resourceMap.getString("appSauceStandardDeviationSLabel.text")); // NOI18N
        appSauceStandardDeviationSLabel.setName("appSauceStandardDeviationSLabel"); // NOI18N

        sauceClassifyButton.setText(resourceMap.getString("sauceClassifyButton.text")); // NOI18N
        sauceClassifyButton.setEnabled(false);
        sauceClassifyButton.setName("sauceClassifyButton"); // NOI18N
        sauceClassifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sauceClassifyButtonActionPerformed(evt);
            }
        });

        toppingMeanHLabel.setText(resourceMap.getString("toppingMeanHLabel.text")); // NOI18N
        toppingMeanHLabel.setName("toppingMeanHLabel"); // NOI18N

        appToppingMeanHLabel.setText(resourceMap.getString("appToppingMeanHLabel.text")); // NOI18N
        appToppingMeanHLabel.setName("appToppingMeanHLabel"); // NOI18N

        toppingStandardDeviationHLabel.setText(resourceMap.getString("toppingStandardDeviationHLabel.text")); // NOI18N
        toppingStandardDeviationHLabel.setName("toppingStandardDeviationHLabel"); // NOI18N

        appToppingStandardDeviationHLabel.setText(resourceMap.getString("appToppingStandardDeviationHLabel.text")); // NOI18N
        appToppingStandardDeviationHLabel.setName("appToppingStandardDeviationHLabel"); // NOI18N

        toppingMeanSLabel.setText(resourceMap.getString("toppingMeanSLabel.text")); // NOI18N
        toppingMeanSLabel.setName("toppingMeanSLabel"); // NOI18N

        appToppingMeanSLabel.setText(resourceMap.getString("appToppingMeanSLabel.text")); // NOI18N
        appToppingMeanSLabel.setName("appToppingMeanSLabel"); // NOI18N

        toppingStandardDeviationSLabel.setText(resourceMap.getString("toppingStandardDeviationSLabel.text")); // NOI18N
        toppingStandardDeviationSLabel.setName("toppingStandardDeviationSLabel"); // NOI18N

        appToppingStandardDeviationSLabel.setText(resourceMap.getString("appToppingStandardDeviationSLabel.text")); // NOI18N
        appToppingStandardDeviationSLabel.setName("appToppingStandardDeviationSLabel"); // NOI18N

        toppingClassifyButton.setText(resourceMap.getString("toppingClassifyButton.text")); // NOI18N
        toppingClassifyButton.setEnabled(false);
        toppingClassifyButton.setName("toppingClassifyButton"); // NOI18N
        toppingClassifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toppingClassifyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(baseImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aspectRatioLabel)
                            .addComponent(eccentricityLabel)
                            .addComponent(roundnessLabel)
                            .addComponent(areaRatioLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(appAreaRatioLabel)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(appRoundnessLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(appAspectRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(appEccentricityLabel))))
                    .addComponent(baseClassifyButton))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sauceImageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(sauceMeanHLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appSauceMeanHLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(sauceStandardDeviationHLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appSauceStandardDeviationHLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(sauceMeanSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appSauceMeanSLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(sauceStandardDeviationSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appSauceStandardDeviationSLabel))
                    .addComponent(sauceClassifyButton))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toppingClassifyButton)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(toppingStandardDeviationSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appToppingStandardDeviationSLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(toppingMeanSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appToppingMeanSLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(toppingStandardDeviationHLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appToppingStandardDeviationHLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(toppingMeanHLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appToppingMeanHLabel))
                    .addComponent(toppingImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                .addContainerGap())
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {areaRatioLabel, aspectRatioLabel, eccentricityLabel, roundnessLabel});

        mainPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {sauceMeanHLabel, sauceMeanSLabel, sauceStandardDeviationHLabel, sauceStandardDeviationSLabel});

        mainPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {toppingMeanHLabel, toppingMeanSLabel, toppingStandardDeviationHLabel, toppingStandardDeviationSLabel});

        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(toppingImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(baseImageScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(sauceImageScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(toppingMeanHLabel)
                                .addComponent(appToppingMeanHLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(toppingStandardDeviationHLabel)
                                .addComponent(appToppingStandardDeviationHLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(toppingMeanSLabel)
                                .addComponent(appToppingMeanSLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(toppingStandardDeviationSLabel)
                                .addComponent(appToppingStandardDeviationSLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(toppingClassifyButton))
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(appSauceMeanHLabel)
                                .addComponent(sauceMeanHLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                .addComponent(sauceStandardDeviationHLabel)
                                .addComponent(appSauceStandardDeviationHLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                .addComponent(sauceMeanSLabel)
                                .addComponent(appSauceMeanSLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                .addComponent(sauceStandardDeviationSLabel)
                                .addComponent(appSauceStandardDeviationSLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sauceClassifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(areaRatioLabel)
                            .addComponent(appAreaRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(baseClassifyButton)))
                .addGap(11, 11, 11))
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appAspectRatioLabel, appEccentricityLabel});

        menuBar.setName("menuBar"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(clasificaciondepizzas.ClasificacionDePizzasApp.class).getContext().getActionMap(ClasificacionDePizzasView.class, this);
        fileMenu.setAction(actionMap.get("openTopping")); // NOI18N
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        openBaseMenuItem.setAction(actionMap.get("openBase")); // NOI18N
        openBaseMenuItem.setText(resourceMap.getString("openBaseMenuItem.text")); // NOI18N
        openBaseMenuItem.setToolTipText(resourceMap.getString("openBaseMenuItem.toolTipText")); // NOI18N
        openBaseMenuItem.setName("openBaseMenuItem"); // NOI18N
        fileMenu.add(openBaseMenuItem);

        openSauceMenuItem.setAction(actionMap.get("openSauce")); // NOI18N
        openSauceMenuItem.setText(resourceMap.getString("openSauceMenuItem.text")); // NOI18N
        openSauceMenuItem.setName("openSauceMenuItem"); // NOI18N
        fileMenu.add(openSauceMenuItem);

        openToppingMenuItem.setAction(actionMap.get("openTopping")); // NOI18N
        openToppingMenuItem.setText(resourceMap.getString("openToppingMenuItem.text")); // NOI18N
        openToppingMenuItem.setName("openToppingMenuItem"); // NOI18N
        fileMenu.add(openToppingMenuItem);

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
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 669, Short.MAX_VALUE)
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
        openBase();
    }//GEN-LAST:event_baseImageScrollPaneMouseClicked

    private void sauceImageScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sauceImageScrollPaneMouseClicked
        openSauce();
    }//GEN-LAST:event_sauceImageScrollPaneMouseClicked

    private void toppingImageScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toppingImageScrollPaneMouseClicked
        openTopping();
    }//GEN-LAST:event_toppingImageScrollPaneMouseClicked

    private void baseClassifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baseClassifyButtonActionPerformed
        showBaseClassificationBox(Double.parseDouble(appRoundnessLabel.getText()), Double.parseDouble(appEccentricityLabel.getText()), Double.parseDouble(appAreaRatioLabel.getText()), Double.parseDouble(appAspectRatioLabel.getText()));
    }//GEN-LAST:event_baseClassifyButtonActionPerformed

    private void sauceClassifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sauceClassifyButtonActionPerformed
        showSauceClassificationBox(Double.parseDouble(appSauceMeanHLabel.getText()), Double.parseDouble(appSauceStandardDeviationHLabel.getText()), Double.parseDouble(appSauceMeanHLabel.getText()), Double.parseDouble(appSauceStandardDeviationSLabel.getText()));
    }//GEN-LAST:event_sauceClassifyButtonActionPerformed

    private void toppingClassifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toppingClassifyButtonActionPerformed
        showToppingClassificationBox(Double.parseDouble(appToppingMeanHLabel.getText()), Double.parseDouble(appToppingStandardDeviationHLabel.getText()), Double.parseDouble(appToppingMeanHLabel.getText()), Double.parseDouble(appToppingStandardDeviationSLabel.getText()));
    }//GEN-LAST:event_toppingClassifyButtonActionPerformed

    @Action
    public void openBase() {
        JFileChooser fileChooser = new JFileChooser("../imagenes"); // selector de archivos
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

    @Action
    public void openSauce() {
        JFileChooser fileChooser = new JFileChooser("../imagenes"); // selector de archivos
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
                sauceImage = JAI.create("fileload", file.getPath());
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
                DisplayJAI display = new DisplayJAI(sauceImage);
                // asigna la visualización al JScrollPane
                sauceImageScrollPane.setViewportView(display);
            } catch (RuntimeException e) {
                JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();

                JOptionPane.showMessageDialog(mainFrame,
                        file.getName() + " no es un archivo de imagen válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            showThresholdBox(ImageType.SAUCE);
        }
    }

    @Action
    public void openTopping() {
        JFileChooser fileChooser = new JFileChooser("../imagenes"); // selector de archivos
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
                toppingImage = JAI.create("fileload", file.getPath());
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
                DisplayJAI display = new DisplayJAI(toppingImage);
                // asigna la visualización al JScrollPane
                toppingImageScrollPane.setViewportView(display);
            } catch (RuntimeException e) {
                JFrame mainFrame = ClasificacionDePizzasApp.getApplication().getMainFrame();

                JOptionPane.showMessageDialog(mainFrame,
                        file.getName() + " no es un archivo de imagen válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            showThresholdBox(ImageType.TOPPING);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appAreaRatioLabel;
    private javax.swing.JLabel appAspectRatioLabel;
    private javax.swing.JLabel appEccentricityLabel;
    private javax.swing.JLabel appRoundnessLabel;
    private javax.swing.JLabel appSauceMeanHLabel;
    private javax.swing.JLabel appSauceMeanSLabel;
    private javax.swing.JLabel appSauceStandardDeviationHLabel;
    private javax.swing.JLabel appSauceStandardDeviationSLabel;
    private javax.swing.JLabel appToppingMeanHLabel;
    private javax.swing.JLabel appToppingMeanSLabel;
    private javax.swing.JLabel appToppingStandardDeviationHLabel;
    private javax.swing.JLabel appToppingStandardDeviationSLabel;
    private javax.swing.JLabel areaRatioLabel;
    private javax.swing.JLabel aspectRatioLabel;
    private javax.swing.JButton baseClassifyButton;
    private javax.swing.JScrollPane baseImageScrollPane;
    private javax.swing.JLabel eccentricityLabel;
    private javax.swing.JPopupMenu.Separator fileMenuSeparator;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openBaseMenuItem;
    private javax.swing.JMenuItem openSauceMenuItem;
    private javax.swing.JMenuItem openToppingMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel roundnessLabel;
    private javax.swing.JButton sauceClassifyButton;
    private javax.swing.JScrollPane sauceImageScrollPane;
    private javax.swing.JLabel sauceMeanHLabel;
    private javax.swing.JLabel sauceMeanSLabel;
    private javax.swing.JLabel sauceStandardDeviationHLabel;
    private javax.swing.JLabel sauceStandardDeviationSLabel;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton toppingClassifyButton;
    private javax.swing.JScrollPane toppingImageScrollPane;
    private javax.swing.JLabel toppingMeanHLabel;
    private javax.swing.JLabel toppingMeanSLabel;
    private javax.swing.JLabel toppingStandardDeviationHLabel;
    private javax.swing.JLabel toppingStandardDeviationSLabel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private ClasificacionDePizzasMedianFilterBox medianFilterBox;
    private ClasificacionDePizzasThresholdBox thresholdBox;
    private ClasificacionDePizzasBaseClassificationBox baseClassificationBox;
    private ClasificacionDePizzasSauceClassificationBox sauceClassificationBox;
    private ClasificacionDePizzasToppingClassificationBox toppingClassificationBox;
    private PlanarImage baseImage;
    private PlanarImage sauceImage;
    private PlanarImage toppingImage;
    private BaseModel bm;
    private SauceAndToppingModel stm;
}
