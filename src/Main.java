import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Line2D;

public class Main {
    public static Double[] x1 = {};
    public static Double[] y1 = {};
    public static Double[] x2 = {};
    public static Double[] y2 = {};

    public static Boolean first = true;

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int LINE_LENGTH = 100;

    public Main() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new BouncingGroupK());
                frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setVisible(true);
            }
        });
    }

    public class BouncingGroupK extends JPanel {

        private double[] x1 = {10.0, 30.0, 30.0, 50.0, 70.0, 45.0, 70.0, 50.0, 30.0, 30.0, 10.0};
        private double[] y1 = {10.0, 10.0, 50.0, 10.0, 10.0, 60.0, 110.0, 110.0, 70.0, 110.0, 110.0};
        private double[] x2 = {30.0, 30.0, 50.0, 70.0, 45.0, 70.0, 50.0, 30.0, 30.0, 10.0, 10.0};
        private double[] y2 = {10.0, 50.0, 10.0, 10.0, 60.0, 110.0, 110.0, 70.0, 110.0, 110.0, 10.0};

        private double dx = 2.0;
        private double dy = 2.0;

        public BouncingGroupK() {
            setLayout(null);

            Timer timer = new Timer(20, e -> {
                for (int i = 0; i < x1.length; i++) {
                    x1[i] += dx;
                    y1[i] += dy;
                    x2[i] += dx;
                    y2[i] += dy;
                }

                // Check for collisions with the frame boundaries
                boolean hitBoundary_x = false;
                boolean hitBoundary_y = false;
                for (int i = 0; i < x1.length; i++) {
                    if (x1[i] < 0 || x2[i] > getWidth()) {
                        hitBoundary_x = true;
                        break;
                    }
                    if (y1[i] < 0 || y2[i] > getHeight()){
                        hitBoundary_y = true;
                        break;
                    }
                }

                if (hitBoundary_x) {
                    // If any line hits the boundary, reverse the direction for all lines
                    dx *= -1;
                    dy *= 1;
                }
                if (hitBoundary_y) {
                    // If any line hits the boundary, reverse the direction for all lines
                    dx *= 1;
                    dy *= -1;
                }

                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.red);
            for (int i = 0; i < x1.length; i++) {
                Line2D line = new Line2D.Double(x1[i], y1[i], x2[i], y2[i]);
                g2.draw(line);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Computer Graphics - Kevin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new MyPanel());
        frame.setMaximumSize(new Dimension(1200, 800));
        frame.setBackground(Color.GRAY);
        setDefault();
        frame.setVisible(true);
    }

    public static void setDefault() {
        x1 = new Double[]{10.0, 30.0, 30.0, 50.0, 70.0, 45.0, 70.0, 50.0, 30.0, 30.0, 10.0};
        y1 = new Double[]{10.0, 10.0, 50.0, 10.0, 10.0, 60.0, 110.0, 110.0, 70.0, 110.0, 110.0};
        x2 = new Double[]{30.0, 30.0, 50.0, 70.0, 45.0, 70.0, 50.0, 30.0, 30.0, 10.0, 10.0};
        y2 = new Double[]{10.0, 50.0, 10.0, 10.0, 60.0, 110.0, 110.0, 70.0, 110.0, 110.0, 10.0};
    }
}

class MyPanel extends JPanel {

    JComboBox<String> transformationComboBox;
    CustomTextField textField1;
    CustomTextField textField2;
    CustomTextField textField3;
    CustomTextField textField4;

    public MyPanel() {
        setLayout(new BorderLayout());

        JPanel paintComponentPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics graph) {
                super.paintComponent(graph);
                Graphics2D g2d = (Graphics2D) graph;

                int w = this.getWidth();
                int h = this.getHeight();

                while (Main.first) {
                    if (Main.x1.length != 0 && Main.y1.length != 0 && Main.x2.length != 0 && Main.y2.length != 0) {
                        for (int i = 0; i < Main.x1.length; i++) {
                            Main.x1[i] = Main.x1[i] + (w * 0.5);
                            Main.y1[i] = Main.y1[i] + (h * 0.4);
                            Main.x2[i] = Main.x2[i] + (w * 0.5);
                            Main.y2[i] = Main.y2[i] + (h * 0.4);
                        }
                    }
                    Main.first = false;
                }

                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));
                if (Main.x1.length != 0 && Main.y1.length != 0 && Main.x2.length != 0 && Main.y2.length != 0) {
                    for (int i = 0; i < Main.x1.length; i++) {
                        Line2D k_normal = new Line2D.Double(Main.x1[i], Main.y1[i], Main.x2[i], Main.y2[i]);
                        g2d.draw(k_normal);
                    }
                }
            }
        };

        paintComponentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(paintComponentPanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        transformationComboBox = new JComboBox<>(new String[]{"Dilatation", "Rotation", "Dilatation & Rotation"});
        formPanel.add(transformationComboBox);

        // Text field for dilatation
        Label label1 = new Label("Dilation");
        textField1 = new CustomTextField(20);
        textField1.setPlaceholder("Dilation");
        formPanel.add(label1);
        formPanel.add(textField1);

        // Text field for rotation
        Label label2 = new Label("Rotation");
        textField2 = new CustomTextField(20);
        textField2.setPlaceholder("Rotation");
        formPanel.add(label2);
        formPanel.add(textField2);

        // Text field for anchor point
        Label label3 = new Label("Anchor Point (X)");
        textField3 = new CustomTextField(20);
        textField3.setPlaceholder("Anchor Point X");
        formPanel.add(label3);
        formPanel.add(textField3);
        textField3.setEnabled(false);

        // Text field for anchor point
        Label label4 = new Label("Anchor Point (Y)");
        textField4 = new CustomTextField(20);
        textField4.setPlaceholder("Anchor Point Y");
        formPanel.add(label4);
        formPanel.add(textField4);
        textField4.setEnabled(false);


        transformationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTextFieldPlaceholders();
            }
        });

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update paint component here
                String selectedTransformation = (String) transformationComboBox.getSelectedItem();
                assert selectedTransformation != null;
                switch (selectedTransformation) {
                    case "Dilatation" -> {
                        Double dilatation = Double.parseDouble(textField1.getText());
                        makeDilatation makeDilatation = new makeDilatation();
//                        makeDilatation.calculateDilatation(Main.x1, Main.y1, Main.x2, Main.y2, dilatation);
                        makeDilatation.calculate(Main.x1, Main.y1, Main.x2, Main.y2, dilatation, Double.parseDouble(textField3.getText()), Double.parseDouble(textField4.getText()));
                    }
                    case "Rotation" -> {
                        Double rotation = Double.parseDouble(textField2.getText());
                        makeRotation makeRotation = new makeRotation();
//                        makeRotation.calculateRotation(Main.x1, Main.y1, Main.x2, Main.y2, rotation);
                        makeRotation.calculate(Main.x1, Main.y1, Main.x2, Main.y2, rotation, Double.parseDouble(textField3.getText()), Double.parseDouble(textField4.getText()));
                    }
                    case "Dilatation & Rotation" -> {
                        Double dilatation = Double.parseDouble(textField1.getText());
                        Double rotation = Double.parseDouble(textField2.getText());
                        makeDilatation makeDilatation = new makeDilatation();
//                        makeDilatation.calculateDilatation(Main.x1, Main.y1, Main.x2, Main.y2, dilatation);
                        makeDilatation.calculate(Main.x1, Main.y1, Main.x2, Main.y2, dilatation, Double.parseDouble(textField3.getText()), Double.parseDouble(textField4.getText()));
                        makeRotation makeRotation = new makeRotation();
//                        makeRotation.calculateRotation(Main.x1, Main.y1, Main.x2, Main.y2, rotation);
                        makeRotation.calculate(Main.x1, Main.y1, Main.x2, Main.y2, rotation, Double.parseDouble(textField3.getText()), Double.parseDouble(textField4.getText()));
                    }
                }
                paintComponentPanel.repaint();
            }
        });
        formPanel.add(applyButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setDefault();
                Main.first = true;
                // Update paint component here
                paintComponentPanel.repaint();
            }
        });
        formPanel.add(resetButton);

        add(formPanel, BorderLayout.EAST);
        transformationComboBox.setSelectedItem("Dilatation");

        //button for pick anchor point
        JButton pickAnchorPoint = new JButton("Pick Anchor Point");
        pickAnchorPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickAnchorPoint();
            }
        });
        formPanel.add(pickAnchorPoint);

        //button for run animation
        JButton runAnimation = new JButton("Run Animation (tuing tuing)");
        runAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main();
            }
        });
        formPanel.add(runAnimation);
    }

    //pick anchor point using mouse
    public void pickAnchorPoint() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("X: " + evt.getX() + " Y: " + evt.getY());
                textField3.setText(evt.getX() + "");
                textField4.setText(evt.getY() + "");
            }
        });
    }

    // Helper method to set text field placeholders based on the selected transformation
    private void updateTextFieldPlaceholders() {
        String selectedTransformation = (String) transformationComboBox.getSelectedItem();
        assert selectedTransformation != null;
        switch (selectedTransformation) {
            case "Dilatation" -> {
                textField1.setEnabled(true);
                textField2.setEnabled(false);
            }
            case "Rotation" -> {
                textField1.setEnabled(false);
                textField2.setEnabled(true);
            }
            case "Dilatation & Rotation" -> {
                textField1.setEnabled(true);
                textField2.setEnabled(true);
            }
        }
    }

}

class CustomTextField extends JTextField {

    private Font originalFont;
    private Color originalForeground;
    /**
     * Grey by default*
     */
    private Color placeholderForeground = new Color(160, 160, 160);
    private boolean textWrittenIn;

    /**
     * You can insert all constructors.
     * I inserted only this one.*
     */
    public CustomTextField(int columns) {
        super(columns);
    }

    @Override
    public void setFont(Font f) {
        super.setFont(f);
        if (!isTextWrittenIn()) {
            originalFont = f;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!isTextWrittenIn()) {
            originalFont = getFont();
        }
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (!isTextWrittenIn()) {
            originalForeground = fg;
        }
    }

    public Color getPlaceholderForeground() {
        return placeholderForeground;
    }

    public void setPlaceholderForeground(Color placeholderForeground) {
        this.placeholderForeground = placeholderForeground;
    }

    public boolean isTextWrittenIn() {
        return textWrittenIn;
    }

    public void setTextWrittenIn(boolean textWrittenIn) {
        this.textWrittenIn = textWrittenIn;
    }

    public void setPlaceholder(final String text) {

        this.customizeText(text);

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if (getText().trim().length() != 0) {
                    setFont(originalFont);
                    setForeground(originalForeground);
                    setTextWrittenIn(true);
                }

            }
        });

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!isTextWrittenIn()) {
                    setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().length() == 0) {
                    customizeText(text);
                }
            }

        });

    }

    private void customizeText(String text) {
        setText(text);
        /**If you change font, family and size will follow
         * changes, while style will always be italic**/
        setFont(new Font(getFont().getFamily(), Font.ITALIC, getFont().getSize()));
        setForeground(getPlaceholderForeground());
        setTextWrittenIn(false);
    }

}

//class matrix multiplication
class makeRotation {
    public static void calculate(Double[] x1, Double[] y1, Double[] x2, Double[] y2, Double rotation, Double anchorX, Double anchorY) {
        rotation = Math.toRadians(rotation);
        for (int l = 0; l < x1.length; l++) {
            Double[][] matrixTranslateBack = {
                    {1.0, 0.0, anchorX},
                    {0.0, 1.0, anchorY},
                    {0.0, 0.0, 1.0},
            };
            Double[][] matrixRotate = {
                    {Math.cos(rotation), -Math.sin(rotation), 0.0},
                    {Math.sin(rotation), Math.cos(rotation), 0.0},
                    {0.0, 0.0, 1.0},
            };
            Double[][] matrixTranslateToOrigin = {
                    {1.0, 0.0, -anchorX},
                    {0.0, 1.0, -anchorY},
                    {0.0, 0.0, 1.0},
            };
            Double[] matrixPoint1 = {
                    x1[l],
                    y1[l],
                    1.0,
            };
            Double[] matrixPoint2 = {
                    x2[l],
                    y2[l],
                    1.0,
            };

            //multiply matrix matrixTranslateBack and matrixRotate
            Double[][] matrixTranslateBackRotate = new Double[3][3];
            for (int i = 0; i < matrixTranslateBackRotate.length; i++) {
                for (int j = 0; j < matrixTranslateBackRotate[i].length; j++) {
                    matrixTranslateBackRotate[i][j] = 0.0;
                    for (int k = 0; k < matrixTranslateBackRotate.length; k++) {
                        matrixTranslateBackRotate[i][j] += matrixTranslateBack[i][k] * matrixRotate[k][j];
                    }
                }
            }

            //multiply matrix matrixTranslateBackRotate and matrixTranslateToOrigin
            Double[][] matrixTranslateBackRotateTranslateToOrigin = new Double[3][3];
            for (int i = 0; i < matrixTranslateBackRotateTranslateToOrigin.length; i++) {
                for (int j = 0; j < matrixTranslateBackRotateTranslateToOrigin[i].length; j++) {
                    matrixTranslateBackRotateTranslateToOrigin[i][j] = 0.0;
                    for (int k = 0; k < matrixTranslateBackRotateTranslateToOrigin.length; k++) {
                        matrixTranslateBackRotateTranslateToOrigin[i][j] += matrixTranslateBackRotate[i][k] * matrixTranslateToOrigin[k][j];
                    }
                }
            }

            //multiply matrix matrixTranslateBackRotateTranslateToOrigin and matrixPoint1
            Double[] matrixPoint1Result = new Double[3];
            for (int i = 0; i < matrixPoint1Result.length; i++) {
                matrixPoint1Result[i] = 0.0;
                for (int j = 0; j < matrixPoint1.length; j++) {
                    matrixPoint1Result[i] += matrixTranslateBackRotateTranslateToOrigin[i][j] * matrixPoint1[j];
                }
            }

            //multiply matrix matrixTranslateBackRotateTranslateToOrigin and matrixPoint2
            Double[] matrixPoint2Result = new Double[3];
            for (int i = 0; i < matrixPoint2Result.length; i++) {
                matrixPoint2Result[i] = 0.0;
                for (int j = 0; j < matrixPoint2.length; j++) {
                    matrixPoint2Result[i] += matrixTranslateBackRotateTranslateToOrigin[i][j] * matrixPoint2[j];
                }
            }

            x1[l] = matrixPoint1Result[0];
            y1[l] = matrixPoint1Result[1];
            x2[l] = matrixPoint2Result[0];
            y2[l] = matrixPoint2Result[1];
        }
    }
}

//class dilatation using matrix
class makeDilatation {
    public static void calculate(Double[] x1, Double[] y1, Double[] x2, Double[] y2, Double dilatation, Double anchorX, Double anchorY) {
        for (int l = 0; l < x1.length; l++) {
            Double[][] matrixTranslateBack = {
                    {1.0, 0.0, anchorX},
                    {0.0, 1.0, anchorY},
                    {0.0, 0.0, 1.0},
            };
            Double[][] matrixRotate = {
                    {dilatation, 0.0, 0.0},
                    {0.0, dilatation, 0.0},
                    {0.0, 0.0, 1.0},
            };
            Double[][] matrixTranslateToOrigin = {
                    {1.0, 0.0, -anchorX},
                    {0.0, 1.0, -anchorY},
                    {0.0, 0.0, 1.0},
            };
            Double[] matrixPoint1 = {
                    x1[l],
                    y1[l],
                    1.0,
            };
            Double[] matrixPoint2 = {
                    x2[l],
                    y2[l],
                    1.0,
            };

            //multiply matrix matrixTranslateBack and matrixRotate
            Double[][] matrixTranslateBackRotate = new Double[3][3];
            for (int i = 0; i < matrixTranslateBackRotate.length; i++) {
                for (int j = 0; j < matrixTranslateBackRotate[i].length; j++) {
                    matrixTranslateBackRotate[i][j] = 0.0;
                    for (int k = 0; k < matrixTranslateBackRotate.length; k++) {
                        matrixTranslateBackRotate[i][j] += matrixTranslateBack[i][k] * matrixRotate[k][j];
                    }
                }
            }

            //multiply matrix matrixTranslateBackRotate and matrixTranslateToOrigin
            Double[][] matrixTranslateBackRotateTranslateToOrigin = new Double[3][3];
            for (int i = 0; i < matrixTranslateBackRotateTranslateToOrigin.length; i++) {
                for (int j = 0; j < matrixTranslateBackRotateTranslateToOrigin[i].length; j++) {
                    matrixTranslateBackRotateTranslateToOrigin[i][j] = 0.0;
                    for (int k = 0; k < matrixTranslateBackRotateTranslateToOrigin.length; k++) {
                        matrixTranslateBackRotateTranslateToOrigin[i][j] += matrixTranslateBackRotate[i][k] * matrixTranslateToOrigin[k][j];
                    }
                }
            }

            //multiply matrix matrixTranslateBackRotateTranslateToOrigin and matrixPoint1
            Double[] matrixPoint1Result = new Double[3];
            for (int i = 0; i < matrixPoint1Result.length; i++) {
                matrixPoint1Result[i] = 0.0;
                for (int j = 0; j < matrixPoint1.length; j++) {
                    matrixPoint1Result[i] += matrixTranslateBackRotateTranslateToOrigin[i][j] * matrixPoint1[j];
                }
            }

            //multiply matrix matrixTranslateBackRotateTranslateToOrigin and matrixPoint2
            Double[] matrixPoint2Result = new Double[3];
            for (int i = 0; i < matrixPoint2Result.length; i++) {
                matrixPoint2Result[i] = 0.0;
                for (int j = 0; j < matrixPoint2.length; j++) {
                    matrixPoint2Result[i] += matrixTranslateBackRotateTranslateToOrigin[i][j] * matrixPoint2[j];
                }
            }

            x1[l] = matrixPoint1Result[0];
            y1[l] = matrixPoint1Result[1];
            x2[l] = matrixPoint2Result[0];
            y2[l] = matrixPoint2Result[1];
        }
    }
}


