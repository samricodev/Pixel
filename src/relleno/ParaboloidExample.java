package relleno;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ParaboloidExample extends JFrame {

    private static final long serialVersionUID = 1L;
    private BufferedImage bufferedImage;

    public ParaboloidExample() {
        bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        drawParaboloid();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, this);
            }
        };

        getContentPane().add(panel);

        setSize(800, 600);
        setTitle("Examen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void putPixel(int x, int y, Color color) {
        bufferedImage.setRGB(x, y, color.getRGB());
    }

    private void applyProjection(float[] point, float[] projectionMatrix) {
        float x = point[0];
        float y = point[1];
        float z = point[2];

        float w = projectionMatrix[3] * x + projectionMatrix[7] * y + projectionMatrix[11] * z + projectionMatrix[15];

        point[0] = (projectionMatrix[0] * x + projectionMatrix[4] * y + projectionMatrix[8] * z + projectionMatrix[12]) / w;
        point[1] = (projectionMatrix[1] * x + projectionMatrix[5] * y + projectionMatrix[9] * z + projectionMatrix[13]) / w;
        point[2] = (projectionMatrix[2] * x + projectionMatrix[6] * y + projectionMatrix[10] * z + projectionMatrix[14]) / w;
    }

    private void drawParaboloid() {
        int steps = 500;
        float twoPI = (float) (2 * Math.PI);

        Color red = Color.RED;

        float[] projectionMatrix = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        };

        for (int i = 0; i <= steps; i++) {
            float u = 2.5f * ((float) i / steps);
            for (int j = 0; j <= steps; j++) {
                float v = twoPI * ((float) j / steps);

                float x = u * (float) Math.cos(v);
                float y = u * (float) Math.sin(v);
                float z = u * u;

                float[] point = {x, y, z};
                applyProjection(point, projectionMatrix);

                int screenX = (int) (point[0] * 50 + 400);
                int screenY = (int) (point[1] * 50 + 300);

                putPixel(screenX, screenY, red);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParaboloidExample::new);
    }
}
