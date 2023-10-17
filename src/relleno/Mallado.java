package relleno;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Mallado extends Pixel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mallado");

        Mallado canvas = new Mallado();
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
        int[] array1 = new int[71]; 
        int[] array2 = new int[51]; 

        for (int i = 0; i < 71; i++) {
            array1[i] = 30 + i; 
        }

        for (int i = 0; i < 51; i++) {
            array2[i] = 50 + i; 
        }

        drawMeshFromArrayProduct(array1,array2, 4, Color.blue);

        g.drawImage(getBufferedImage(), 0, 0, null);
    }
}
