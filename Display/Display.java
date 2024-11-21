package Display;

import Functions3D.Input;
import Functions3D.Polygon3D;
import Settings.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class Display extends JPanel {
    public static int WIDTH = 1200;
    public static int HEIGHT = 800;
    JFrame window = new JFrame();
    Input input;

    public Display(int Width, int Height) {
        Display.WIDTH = Width;
        Display.HEIGHT = Height;
        openWindow();
    }

    public Display() {
        openWindow();
    }

    private void openWindow(){
        //Открывает окно
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(Display.WIDTH, Display.HEIGHT);
        window.setResizable(true);
        window.setTitle(Settings.TITLE);
        window.setVisible(true);
        this.addKeyListener(input);
        this.setFocusable(true);
    }

    public void updateWindowSize(){
        this.WIDTH = window.getWidth();
        this.HEIGHT = window.getHeight();
    }

    public void render(Polygon3D[] polygons3D) {
        //Выводит в окно 2D полигоны
        int polynumber = 0;
        Graphics g = window.getGraphics();
        Image image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = image.getGraphics();
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        for(Polygon3D polygon3D : polygons3D) {
            polynumber++;
            try {
                g2.setColor(polygon3D.color);
            }
            catch (Exception e){
                System.out.println("Polygon has no color");
            }

            g2.fillPolygon(polygon3D.polygon2D);
        }
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        //System.out.println("Rendered: " + polynumber + " polygons");
    }
}
