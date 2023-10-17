package relleno;
  
import javax.swing.*;
import java.awt.*;

public class Translation extends Pixel {

    private int y = 0;
    private int x = 0;

    private int dx = 100;
    private int dy = 100;

    private int x0 = 100;
    private int y0 = 100;
    private int x1 = 200;
    private int y1 = 200;

    private int xi = x0;
    private int yi = y0;

    private int matrizT[][] = {{1, 0, 0}, {0, 1, 0}, {dx, dy, 0}};

    private int matrizP[][] = {{x0, y0, 1}, {x0, y1, 1}, {x1, y0, 1}, {x1, y1, 1}};

    private boolean animationRunning = true; // Variable de control

    public static void main(String[] args) {
        JFrame window = new JFrame("Traslación");

        Translation canvas = new Translation();
        window.add(canvas);

        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        SwingUtilities.invokeLater(() -> window.setVisible(true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackgroundColor(Color.BLACK);
        
        fillRectangle(x0, y0, x1, y1, Color.RED);

        if (animationRunning) {
            if (x == -100 && y == -100) {
                animationRunning = false;
            }
            if (x == 100 && y == 100) {
                animationRunning = true;
            }
            if (animationRunning) {
                x--;
                y--;
            } else {
                x++;
                y++;
            }

            translate(x, y); // El método translate() mueve el origen de coordenadas y contiene el repaint()
            g.drawImage(getBufferedImage(), 0, 0, null);
        } else {
            g.drawImage(getBufferedImage(), 0, 0, null);
            g.drawImage(getBufferedImage(), x, y, null);
        }
    }
}
