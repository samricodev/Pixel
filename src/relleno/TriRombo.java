package relleno;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class TriRombo extends Pixel{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangulos y rombos");

        TriRombo canvas = new TriRombo();
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
        

        fillTriangle(100, 300, 200, 300, 150, 200, Color.green);
        fillDiamond(100, 100, 150, 150, 200, 100, 150, 50, Color.yellow);

        g.drawImage(getBufferedImage(), 0, 0, null);
    }
}
