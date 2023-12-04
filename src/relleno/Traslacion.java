package relleno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Traslacion extends Pixel implements ActionListener {

    private BufferedImage bufferedImage;
    private Graphics2D pixel;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int RECT_WIDTH = 40;
    private static final int RECT_HEIGHT = 40;
    private double DX = 0;
    private double Dy = 0;
    private double[][] rectangleVertices;
    private Color backgroundColor = Color.BLACK;
    private Color fillColor = Color.RED;
    private boolean increasing = true;

    public Traslacion() {
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixel = bufferedImage.createGraphics();
        Timer timer = new Timer(100, this);
        timer.start();

        rectangleVertices = new double[][]{
            {-RECT_WIDTH / 2, -RECT_HEIGHT / 2, 1}, // P1
            {RECT_WIDTH / 2, -RECT_HEIGHT / 2, 1}, // P2
            {RECT_WIDTH / 2, RECT_HEIGHT / 2, 1}, // P3
            {-RECT_WIDTH / 2, RECT_HEIGHT / 2, 1} // P4
        };
    }

    public void putPixel(int x, int y, Color c) {
        bufferedImage.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        pixel.setColor(backgroundColor);
        pixel.fillRect(0, 0, WIDTH, HEIGHT);

        double[][] translationMatrix = {
            {1, 0, DX},
            {0, 1, Dy},
            {0, 0, 1}
        };

        int[] xPoints = new int[rectangleVertices.length];
        int[] yPoints = new int[rectangleVertices.length];

        for (int i = 0; i < rectangleVertices.length; i++) {
            double[] transformedVertex = multiplyMatrixAndPoint(translationMatrix, rectangleVertices[i]);
            xPoints[i] = (int) (transformedVertex[0] + (WIDTH / 2));
            yPoints[i] = (int) (transformedVertex[1]);
        }

        fillPolygon(xPoints, yPoints, fillColor);

        g.drawImage(bufferedImage, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (increasing) {
            DX += 5;
            Dy += 5;
            if (DX >= WIDTH / 2) {
                increasing = false;
            }
        } else {
            DX -= 5;
            Dy -= 5;
            if (DX <= -WIDTH / 2) {
                increasing = true;
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Traslacion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        Traslacion animation = new Traslacion();
        frame.add(animation);

        frame.setVisible(true);
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
    
    private void fillPolygon(int[] xPoints, int[] yPoints, Color fillColor) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int y : yPoints) {
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }

        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            for (int i = 0; i < xPoints.length; i++) {
                int j = (i + 1) % xPoints.length;
                int xi = xPoints[i];
                int xj = xPoints[j];
                int yi = yPoints[i];
                int yj = yPoints[j];

                if ((yi <= y && yj > y) || (yj <= y && yi > y)) {
                    int x = (int) (xi + (double) (y - yi) / (double) (yj - yi) * (xj - xi));
                    if (x < x1) {
                        x1 = x;
                    }
                    if (x > x2) {
                        x2 = x;
                    }
                }
            }

            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }
}