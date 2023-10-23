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
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Computer Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new MyPanel());
        frame.setMaximumSize(new Dimension(1200, 800));
        frame.setBackground(Color.GRAY);

//        frame.add(new InitialPanel());
        frame.setVisible(true);
//        frame.pack();
    }

    public static void setDefault(){
        x1 = new Double[]{10.0, 30.0, 30.0, 50.0, 70.0, 45.0, 70.0, 50.0, 30.0, 30.0, 10.0};
        y1 = new Double[]{10.0, 10.0, 50.0, 10.0, 10.0, 60.0, 110.0, 110.0, 70.0, 110.0, 110.0};
        x2 = new Double[]{30.0, 30.0, 50.0, 70.0, 45.0, 70.0, 50.0, 30.0, 30.0, 10.0, 10.0};
        y2 = new Double[]{10.0, 50.0, 10.0, 10.0, 60.0, 110.0, 110.0, 70.0, 110.0, 110.0, 10.0};
    }


}

//class FirstPanel extends JPanel {
//    public FirstPanel() {
//        setPreferredSize(new Dimension(1200, 800));
//        setBackground(Color.BLACK);
//    }
//
//    @Override
//    public void paintComponent(Graphics graph) {
//        super.paintComponent(graph);
//        Graphics2D g2d = (Graphics2D) graph; // cast to get 2D drawing methods
//
//        g2d.setColor(Color.BLUE);
//        g2d.setStroke(new BasicStroke(2));
//        for (int i = 0; i < 11; i++) {
//            Line2D k_normal = new Line2D.Double(Main.x1[i], Main.y1[i], Main.x2[i], Main.y2[i]);
//            g2d.draw(k_normal);
//        }
//    }
//}


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

                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));
                if (Main.x1.length != 0) {
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
    }

    //pick anchor point using mouse
    public void pickAnchorPoint(){
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
class makeRotation{
    public static void calculate(Double[] x1, Double[] y1, Double[] x2, Double[] y2, Double rotation, Double anchorX, Double anchorY){
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
class makeDilatation{
    public static void calculate(Double[] x1, Double[] y1, Double[] x2, Double[] y2, Double dilatation, Double anchorX, Double anchorY){
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
