package relleno;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Curvas extends Pixel{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Curvas");

        Curvas canvas = new Curvas();
        frame.add(canvas);

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackgroundColor(Color.BLACK);
        
        //drawInfinitySymbol(100, 200, 80, Color.RED);
        //drawInvertedWideParabola(400, 100, -0.1, 0.0, -50.0, -50.0, 50.0, 0.1, Color.YELLOW);
        //drawChurro(5, 30);
        drawHumito();
        //drawFlor(1000);
        //drawSol(1000);
        
        g.drawImage(getBufferedImage(), 0, 0, null);
    }
}
