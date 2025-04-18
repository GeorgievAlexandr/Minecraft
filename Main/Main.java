package Main;

import Display.Display;
import Functions3D.*;


public class Main {
    public static void main(String[] args) {
        Camera camera = new Camera(new float[]{50, 10, 10}, new float[]{-0.2f, 0, 0});
        Scene scene = new Scene(camera);
        Display display = new Display();
        WorldGenerator.generate();
        scene.updateMeshes();

        for(;;) {
            scene.render(display);
            camera.localMove(new float[]{0.1f, 0, 0});
        }
    }
}