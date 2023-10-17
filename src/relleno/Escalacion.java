package relleno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Escalacion extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private int centerX, centerY;
    private int radius = 20;
    private double[][] circleVertices;
    private double[][] transformationMatrix;
    private Color backgroundColor = Color.BLACK;
    private Color circleColor = Color.RED;
    private Timer timer;
    private double scale = 1.0;
    private boolean growing = true; 

    public Escalacion() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        centerX = WIDTH / 2;
        centerY = HEIGHT / 2;

        transformationMatrix = new double[][] {
                { 1.0, 0.0, 0.0 },
                { 0.0, 1.0, 0.0 },
                { 0.0, 0.0, 1.0 }
        };

        timer = new Timer(100, this);
        timer.start();

        initializeCircleVertices();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

        double[][] scaledCircleVertices = new double[circleVertices.length][3];
        for (int i = 0; i < circleVertices.length; i++) {
            scaledCircleVertices[i] = multiplyMatrixAndPoint(transformationMatrix, circleVertices[i]);
        }

        fillCircle(scaledCircleVertices, circleColor);

        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (growing) {
            scale += 0.1;
        } else {
            scale -= 0.1;
        }

        if (scale >= 2.0 || scale <= 0.1) {
            growing = !growing;
        }

        transformationMatrix[0][0] = scale;
        transformationMatrix[1][1] = scale;

        radius = (int) (20 * scale);

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Escalacion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        Escalacion animation = new Escalacion();
        frame.add(animation);

        frame.setVisible(true);
    }

    private void initializeCircleVertices() {
        int numVertices = 360;
        circleVertices = new double[numVertices][3];

        for (int i = 0; i < numVertices; i++) {
            double angle = Math.toRadians(i * (360.0 / numVertices));
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            circleVertices[i] = new double[] { x, y, 1.0 };
        }
    }

    private void fillCircle(double[][] vertices, Color fillColor) {
        int xc = centerX;
        int yc = centerY;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                double distance = Math.sqrt(x * x + y * y);
                if (distance <= radius) {
                    int px = xc + x;
                    int py = yc + y;
                    putPixel(px, py, fillColor);
                }
            }
        }
    }

    private double[] multiplyMatrixAndPoint(double[][] matrix, double[] point) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * point[j];
            }
            result[i] = sum;
        }

        return result;
    }
}
