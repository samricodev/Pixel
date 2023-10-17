package relleno;
import javax.swing.*;
import java.awt.*;

public class Scaling extends Pixel {

    private double scaleX = 1.0;
    private double scaleY = 1.0;

    public Scaling(double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        initUI();
    }

    private void initUI() {
        JFrame window = new JFrame("Escalación");
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

        drawRectangle(100, 100, 150, 150, Color.RED);
        scale(scaleX, scaleY);
        g.drawImage(getBufferedImage(), 0, 0, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Scaling(1.5, 1.5));
    }
}
