package relleno;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Project extends Pixel implements ActionListener {

    private int currentTime = 0;

    public Project() {

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTime++;
                repaint();
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Projecto");

        Project project = new Project();
        frame.add(project);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(frame);
        frame.setResizable(false);

        SwingUtilities.invokeLater(() -> frame.setVisible(true));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (currentTime < 5) {
            // Llama al método world1 durante los primeros 30 segundos
            world1(g);
        } else if (currentTime < 10) {
            // Llama al método world2 durante los segundos 31-60
            world2(g);
        } else if (currentTime < 15) {
            world3(g);
        } else if (currentTime < 20){
            finish(g);
        }else {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void world1(Graphics g) {
        setBackgroundColor(sky);

        //Nubes
        fillCircle(50, 50, 10, white);
        fillCircle(60, 50, 15, white);
        fillCircle(70, 50, 10, white);

        fillCircle(100, 70, 10, white);
        fillCircle(110, 70, 15, white);
        fillCircle(120, 70, 10, white);

        fillCircle(400, 40, 20, white);
        fillCircle(420, 40, 25, white);
        fillCircle(440, 40, 20, white);

        drawFlor(1000);

        //Piso
        fillRectangle(0, 400, 600, 500, ladrillo);
        drawLine(0, 400, 600, 400, black);
        drawLine(0, 430, 600, 430, black);

        for (int i = 0; i < 600; i += 30) {
            drawLine(i, 400, i, 600, black);
        }

        //Pastisales
        fillTriangle(20, 399, 50, 350, 80, 399, pastos);

        fillTriangle(200, 399, 220, 350, 240, 399, pastoSeco);
        fillTriangle(220, 399, 240, 350, 260, 399, pastoSeco);
        fillTriangle(240, 399, 260, 350, 280, 399, pastoSeco);

        fillCircle(400, 379, 20, pastoSeco);
        fillCircle(420, 379, 20, pastoSeco);
        fillCircle(440, 379, 20, pastoSeco);

        //Barras
        fillRectangle(150, 250, 190, 290, moneda);
        drawRect(150, 250, 190, 290, black);

        fillRectangle(300, 250, 500, 290, ladrillo);
        fillRectangle(420, 250, 460, 290, moneda);
        drawRect(300, 250, 500, 290, black);

        for (int i = 300; i < 500; i += 40) {
            drawLine(i, 250, i, 290, black);
        }

        g.drawImage(getBufferedImage(), 0, 0, null);
    }

    public void world2(Graphics g) {
        fillRectangle(0, 0, 600, 500, black);
        setBackground(black);

        //Pared
        fillRectangle(0, 0, 30, 400, ladrillo2);
        drawRect(0, 0, 30, 400, black);
        for (int i = 0; i < 400; i += 40) {
            drawLine(0, i, 30, i, black);
        }

        //Piso
        fillRectangle(0, 400, 600, 500, ladrillo2);
        drawLine(0, 400, 600, 400, black);
        drawLine(0, 430, 600, 430, black);

        for (int i = 0; i < 600; i += 30) {
            drawLine(i, 400, i, 600, black);
        }

        //Barras
        fillRectangle(300, 250, 500, 290, ladrillo2);
        fillRectangle(340, 250, 380, 290, moneda);
        drawRect(300, 250, 500, 290, black);
        for (int i = 300; i < 500; i += 40) {
            drawLine(i, 250, i, 290, black);
        }

        //Techo
        fillRectangle(100, 0, 600, 80, ladrillo2);
        drawRect(100, 0, 600, 80, black);
        drawLine(100, 40, 600, 40, black);
        for (int i = 100; i < 600; i += 30) {
            drawLine(i, 0, i, 80, black);
        }

        drawHumito();

        //Tubo
        fillRectangle(540, 340, 560, 400, tubo);
        drawRect(540, 340, 560, 400, black);
        fillRectangle(560, 360, 600, 400, tubo);
        drawRect(560, 360, 600, 400, black);

        g.drawImage(getBufferedImage(), 0, 0, null);
    }

    public void world3(Graphics g) {
        setBackgroundColor(sky);

        //Nubes
        fillCircle(100, 70, 10, white);
        fillCircle(110, 70, 15, white);
        fillCircle(120, 70, 10, white);

        fillCircle(400, 40, 20, white);
        fillCircle(420, 40, 25, white);
        fillCircle(440, 40, 20, white);
        
        drawInfinitySymbol(250, 50, 30, white);

        //Piso
        fillRectangle(0, 400, 600, 500, ladrillo);
        drawLine(0, 400, 600, 400, black);
        drawLine(0, 430, 600, 430, black);

        for (int i = 0; i < 600; i += 30) {
            drawLine(i, 400, i, 600, black);
        }

        //Pastisales
        fillTriangle(20, 399, 50, 350, 80, 399, pastos);
        
        //Bandera
        fillRectangle(252, 150, 258, 380, tubo);
        drawRect(252, 150, 258, 380, black);
        fillRectangle(240, 370, 270, 400, ladrillo);
        drawRect(240, 370, 270, 400, black);
        fillCircle(255, 145, 5, tubo);
        drawCircle(255, 145, 5, black);
        fillTriangle(230, 145, 249, 135, 249, 155, white);
        
        //Castillo
        fillRectangle(400, 150, 500, 200, ladrillo);
        fillRectangle(420, 160, 440, 170, black);
        fillRectangle(460, 160, 480, 170, black);
        drawRect(400, 150, 500, 200, black);
        for(int i = 400; i < 500; i += 20){
            drawLine(i, 150, i, 200, black);
        }
        
        for(int i = 150; i < 200; i+=10){
            drawLine(400, i, 500, i, black);
        }
        
        fillRectangle(350, 200, 550, 400, ladrillo);
        drawRect(350, 200, 550, 400, black);
        fillEllipse(450, 300, 50, 50, black);
        fillRectangle(400, 300, 500, 400, black);
        for(int i = 350; i < 550; i += 20){
            drawLine(i, 200, i, 400, black);
        }
        
        for(int i = 200; i < 400; i+=10){
            drawLine(350, i, 550, i, black);
        }

        g.drawImage(getBufferedImage(), 0, 0, null);
    }
    
    public void finish(Graphics g){
        fillRectangle(0, 0, 600, 500, black);
        setBackgroundColor(black);
        
        g.setColor(white); 
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Over", 300, 350);
       
        //Gorra
        fillRectangle(190, 200, 250, 210, rojo);
        fillRectangle(180, 210, 280, 220, rojo);
        //Cara
        fillRectangle(180, 220, 250, 280, piel);
        //Nariz
        fillRectangle(250, 230, 260, 240, piel);
        fillRectangle(250, 240, 270, 250, piel);
        //Ojo
        fillRectangle(230, 220, 240, 240, pelo);
        //Pelo
        fillRectangle(170, 220, 180, 260, pelo);
        fillRectangle(180, 220, 200, 230, pelo);
        //Bigote
        fillRectangle(230, 250, 260, 260, pelo);
        //Cuello
        fillRectangle(180, 280, 240, 290, pelo);
        //Torso
        fillRectangle(180, 290, 240, 350, rojo);
        fillRectangle(190, 280, 200, 300, rojo);
        fillRectangle(190, 300, 200, 310, piel);
        fillRectangle(220, 280, 230, 300, rojo);
        fillRectangle(220, 300, 230, 310, piel);
        
        //Brazos
        fillRectangle(170, 290, 180, 320, pelo);
        fillRectangle(240, 290, 250, 320, pelo);
        fillRectangle(170, 320, 180, 340, piel);
        fillRectangle(240, 320, 250, 340, piel);
        
        //Piernas
        fillRectangle(180, 350, 200, 380, rojo);
        fillRectangle(220, 350, 240, 380, rojo);
        
        //Botas
        fillRectangle(170, 380, 200, 400, pelo);
        fillRectangle(220, 380, 250, 400, pelo);
        
        fillRectangle(160, 390, 170, 400, pelo);
        fillRectangle(260, 390, 250, 400, pelo);
        
        g.drawImage(getBufferedImage(), 0, 0, null);
    }
}
