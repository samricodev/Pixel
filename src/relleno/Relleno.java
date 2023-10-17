package relleno;

import javax.swing.*;
import java.awt.*;

public class Relleno extends Pixel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Relleno");

        Relleno canvas = new Relleno();
        frame.add(canvas);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackgroundColor(Color.BLACK);
        
        fillRectangle(100, 100, 400, 400, Color.RED);
        fillCircle(250, 250, 50, Color.WHITE);
        fillEllipse(250, 250, 20, 30, Color.MAGENTA);

        g.drawImage(getBufferedImage(), 0, 0, null);
    }
}
