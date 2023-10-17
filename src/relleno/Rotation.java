package relleno;

import javax.swing.*;
import java.awt.*;

public class Rotation extends Pixel {

    private double angle = 0.0;

    public Rotation(double angle) {
        this.angle = angle;
        initUI();
    }

    private void initUI() {
        JFrame window = new JFrame("Rotación");
        window.add(this);
        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackgroundColor(Color.BLACK);

        drawRect(100, 100, 200, 200, Color.RED);
        rotate(angle);
        g.drawImage(getBufferedImage(), 0, 0, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Rotation(Math.toRadians(40)));
    }
}
