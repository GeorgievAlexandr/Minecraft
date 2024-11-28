package Functions3D;

import Display.Display;
import java.util.Arrays;
import java.util.Comparator;

public class Scene {
    public Mesh[] meshes;
    public Camera camera;
    public Scene(Mesh[] meshes, Camera camera) {
        this.meshes = meshes;
        this.camera = camera;
    }
    public Scene(Camera camera) {
        this.camera = camera;
    }

    private Polygon3D[] visiblePolygons(Polygon3D[] polygons){
        //Рассчитывает видимые полигоны
        int numberOfVisiblePolygons = 0;
        for (Polygon3D polygon : polygons) {
            try {
                if (polygon.visible) {
                    polygon.calculateNormalAngle(camera.cameraPosition);
                }
            }
            catch (Exception e) {}
        }
        for (int i = 0; i < polygons.length; i++) {
            if (polygons[i].visible) {
                if (polygons[i].localPivot[0] < -0.87f && polygons[i].normalAngle < 1.57f) {
                    numberOfVisiblePolygons++;
                }
            }
        }
        Polygon3D[] visiblePolygons = new Polygon3D[numberOfVisiblePolygons];
        numberOfVisiblePolygons = 0;
        for (int i = 0; i < polygons.length; i++) {
            if (polygons[i].visible) {
                if (polygons[i].localPivot[0] < -0.87f && polygons[i].normalAngle < 1.57f) {
                    visiblePolygons[numberOfVisiblePolygons] = polygons[i];
                    numberOfVisiblePolygons++;
                }
            }
        }
        return visiblePolygons;
    }

    private Polygon3D[] artistAlgoritme() {
        //Рассчитывает видимые полигоны, сортирует их и рассчитывает 2D полигоны
        int polyNumber = 0;
        for (Mesh mesh : meshes) {
            try {
                for (Polygon3D polygon : mesh.polygons3D) {
                    polyNumber++;
                }
            }
            catch (Exception e) {}
        }
        Polygon3D[] polygons = new Polygon3D[polyNumber];
        polyNumber = 0;
        for (Mesh mesh : meshes) {
            try {
                for (Polygon3D polygon : mesh.polygons3D) {
                    polygons[polyNumber] = polygon;
                    polyNumber++;
                }
            }
            catch (Exception e) {}
        }
        //Рассчёт локальных pivot-ов
        for (Polygon3D polygon : polygons) {
            polygon.calculateLocalPivot(camera.cameraPosition, camera.cameraRotation);
        }
        //Рассчёт видимых полигонов
        Polygon3D[] visiblePolygons = visiblePolygons(polygons);

        for (Polygon3D polygon : visiblePolygons){
            polygon.calculateLocalVerticles(camera.cameraPosition, camera.cameraRotation);
            polygon.calculateDistance();
            polygon.calculatePolygon2D();
        }
        //Сортировка полигонов по расстоянию
        Arrays.sort(visiblePolygons, new Comparator<Polygon3D>() {
            public int compare(Polygon3D p1, Polygon3D p2) {
                return Float.compare(p2.distance, p1.distance);
            }
        });
        return visiblePolygons;
    }

    public void render(Display display) {
        display.updateWindowSize();
        Polygon3D[] polygons = artistAlgoritme();
        display.render(polygons);
    }

    public void updateMeshes(){
        int i = 0;
        for (Chunk chunk : WorldGenerator.chunks) {
            for(Mesh cube : chunk.cubes){
                if(cube != null){
                    i++;
                }
            }
        }
        this.meshes = new Mesh[i];
        i = 0;
        for (Chunk chunk : WorldGenerator.chunks) {
            for(Mesh cube : chunk.cubes){
                if(cube != null){
                    this.meshes[i] = cube;
                    i++;
                }
            }
        }
    }
}
