package Main;

import Display.Display;
import Functions3D.*;


public class Main {
    public static void main(String[] args) {
        Camera camera = new Camera(new float[]{-10, 25, 10}, new float[]{0, 3.14f, -0.6f});
        Scene scene = new Scene(camera);
        Display display = new Display();
        WorldGenerator.generate();
        scene.updateMeshes();

        for(;;) {
            scene.render(display);
        }
    }
}