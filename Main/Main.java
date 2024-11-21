package Main;

import Display.Display;
import Functions3D.*;


public class Main {
    public static void main(String[] args) {
        Camera camera = new Camera(new float[]{-10, 15, 10}, new float[]{0, 3.14f, -0.3f});
        Scene scene = new Scene(camera);
        Display display = new Display();
        Chunk chunk = new Chunk(new long[]{0, 0, 0});
        scene.meshes = chunk.cubes;

        for(;;) {
            camera.updatePosition(1);
            scene.render(display);
        }
    }
}