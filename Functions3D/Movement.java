package Functions3D;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Movement extends Thread implements KeyListener {
    public void run(){
    }
    public static Camera camera;
    @Override
    public void keyPressed(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent event) {}

    @Override
    public void keyTyped(KeyEvent event){
        if (event.getKeyChar() == 'w'){
            camera.localMove(new float[] {1, 0, 0});
        }
        if (event.getKeyChar() == 's'){
            camera.localMove(new float[] {-1, 0, 0});
        }
        if (event.getKeyChar() == 'a'){
            camera.localMove(new float[] {0, 0, 1});
        }
        if (event.getKeyChar() == 'd'){
            camera.localMove(new float[] {0, 0, -1});
        }
    }
}
