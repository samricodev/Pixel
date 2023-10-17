package relleno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Rotacion extends Pixel implements ActionListener {
    
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int RECT_WIDTH = 80;
    private static final int RECT_HEIGHT = 80;
    private double angle = 0;
    private double translateX = 0;
    private double translateY = 0;
    private double[][] rectangleVertices;
    private Color backgroundColor = Color.BLACK;

    public Rotacion() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();
        Timer timer = new Timer(100, this);
        timer.start();

        rectangleVertices = new double[][]{
                {-RECT_WIDTH / 2, -RECT_HEIGHT / 2, 1},
                {RECT_WIDTH / 2, -RECT_HEIGHT / 2, 1},
                {RECT_WIDTH / 2, RECT_HEIGHT / 2, 1},
                {-RECT_WIDTH / 2, RECT_HEIGHT / 2, 1}
        };
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        double[][] rotationMatrix = {
                {Math.cos(angle), -Math.sin(angle), 0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1}
        };

        double[][] translationMatrix = {
                {1, 0, translateX},
                {0, 1, translateY},
                {0, 0, 1}
        };

        double[][] transformationMatrix = multiplyMatrices(rotationMatrix, translationMatrix);

        int[] xPoints = new int[rectangleVertices.length];
        int[] yPoints = new int[rectangleVertices.length];

        for (int i = 0; i < rectangleVertices.length; i++) {
            double[] transformedVertex = multiplyMatrixAndPoint(transformationMatrix, rectangleVertices[i]);
            xPoints[i] = (int) (transformedVertex[0] + centerX);
            yPoints[i] = (int) (transformedVertex[1] + centerY);
        }

        graPixel.setColor(Color.RED);
        graPixel.fillPolygon(xPoints, yPoints, xPoints.length);

        angle += Math.toRadians(5);

        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void translateRectangle(double dx, double dy) {
        translateX += dx;
        translateY += dy;
    }

    private double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] result = new double[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                double sum = 0;
                for (int k = 0; k < cols1; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotacion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(WIDTH, HEIGHT);

        Rotacion animation = new Rotacion();
        frame.add(animation);

        frame.setVisible(true);

        animation.translateRectangle(20, 10);
    }
}