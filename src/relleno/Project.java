package relleno;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Project extends Pixel implements ActionListener{
    
    Color sky = new Color(31, 141, 189);
    Color white = new Color(255, 255, 255);
    Color ladrillo = new Color(220, 90, 33);
    Color black = new Color(0, 0, 0);
    
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
        
        //Piso
        fillRectangle(0, 400, 600, 500, ladrillo);
        drawLine(0, 400, 600, 400, black);
        drawLine(0, 430, 600, 430, black);
        
        for(int i = 0; i < 600; i += 30){
            drawLine(i, 400, i, 600, black);
        }
        
        

        g.drawImage(getBufferedImage(), 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
