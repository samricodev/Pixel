package relleno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Recorte extends Pixel {
    private List<Shape> shapes = new ArrayList<>();
    private Shape currentShape;
    private boolean drawing = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Recorte");

        Recorte canvas = new Recorte();
        frame.add(canvas);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public Recorte() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!drawing) {
                    int x = e.getX();
                    int y = e.getY();
                    currentShape = new Circle(x, y, 0, Color.BLACK);
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawing && currentShape != null) {
                    int x = e.getX();
                    int y = e.getY();
                    int radius = (int) Math.sqrt(Math.pow(x - currentShape.getX(), 2) + Math.pow(y - currentShape.getY(), 2));
                    currentShape.setRadius(radius);
                    shapes.add(currentShape);
                    currentShape = null;
                    drawing = false;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing && currentShape != null) {
                    int x = e.getX();
                    int y = e.getY();
                    int radius = (int) Math.sqrt(Math.pow(x - currentShape.getX(), 2) + Math.pow(y - currentShape.getY(), 2));
                    currentShape.setRadius(radius);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackgroundColor(Color.BLACK);

        fillRectangle(100, 100, 400, 400, Color.RED);

        for (Shape shape : shapes) {
            shape.draw(g);
        }

        if (drawing && currentShape != null) {
            currentShape.draw(g);
        }

        g.drawImage(getBufferedImage(), 0, 0, null);
    }

    private interface Shape {
        void draw(Graphics g);

        int getX();

        int getY();

        void setRadius(int radius);
    }

    private class Circle implements Shape {
        private int x, y, radius;
        private Color color;

        public Circle(int x, int y, int radius, Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void draw(Graphics g) {
            fillCircle(x, y, radius, color);
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void setRadius(int radius) {
            this.radius = radius;
        }
    }
}

