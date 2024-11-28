package Functions3D;
//Чанк
import java.awt.Color;

public class Chunk {
    public Mesh[] cubes = new Mesh[65536];
    public long[] coordinates;

    public Chunk(long[] coordinates) {
        this.coordinates = coordinates;
        generate();
    }

    public void generate(){
        //Заполняет cubes
        for(long x = coordinates[0]; x < coordinates[0] + 16; x++){
            for(long z = coordinates[2]; z < coordinates[2] + 16; z++){
                int limit = PerlinNoise.getNoise(x, z);
                for(int y = 0; y < limit; y++){
                    cubes[(int) ((x - coordinates[0]) + 16 * (y - coordinates[1]) + 256 * (z - coordinates[2]))] = createCube(new float[]{x, y, z});
                }
            }
        }
    }

    private static Mesh createCube(float[] coord){
        //Создаёт куб
        float[] a = new float[]{0, 0, 0};
        float[] b = new float[]{1, 0, 0};
        float[] c = new float[]{1, 1, 0};
        float[] d = new float[]{1, 1, 1};
        float[] e = new float[]{0, 1, 1};
        float[] f = new float[]{0, 0, 1};
        float[] g = new float[]{0, 1, 0};
        float[] k = new float[]{1, 0, 1};
        float[][] upNormal = new float[][]{{0.5f, 1, 0.5f}, {0.5f, 2, 0.5f}};
        float[][] downNormal = new float[][]{{0.5f, 0, 0.5f}, {0.5f, -1, 0.5f}};
        float[][] frontNormal = new float[][]{{0, 0.5f, 0.5f}, {-1, 0.5f, 0.5f}};
        float[][] backNormal = new float[][]{{1, 0.5f, 0.5f}, {2, 0.5f, 0.5f}};
        float[][] leftNormal = new float[][]{{0.5f, 0.5f, 1}, {0.5f, 0.5f, 2}};
        float[][] rightNormal = new float[][]{{0.5f, 0.5f, 0}, {0.5f, 0.5f, -1}};
        Polygon3D up = new Polygon3D(new float[][]{e, g, c}, upNormal, Color.BLACK, new float[]{0.5f, 1, 0.5f});
        Polygon3D up2 = new Polygon3D(new float[][]{e, d, c}, upNormal, Color.BLACK, new float[]{0.5f, 1, 0.5f});
        Polygon3D front = new Polygon3D(new float[][]{e, g, a}, frontNormal, Color.BLUE, new float[]{0, 0.5f, 0.5f});
        Polygon3D front2 = new Polygon3D(new float[][]{e, f, a}, frontNormal, Color.BLUE, new float[]{0, 0.5f, 0.5f});
        Polygon3D back = new Polygon3D(new float[][]{b, c, d}, backNormal, Color.RED, new float[]{1, 0.5f, 0.5f});
        Polygon3D back2 = new Polygon3D(new float[][]{b, k, d}, backNormal, Color.RED, new float[]{1, 0.5f, 0.5f});
        Polygon3D down = new Polygon3D(new float[][]{a, b, k}, downNormal, Color.GREEN, new float[]{0.5f, 0, 0.5f});
        Polygon3D down2 = new Polygon3D(new float[][]{a, f, k}, downNormal, Color.GREEN, new float[]{0.5f, 0, 0.5f});
        Polygon3D right = new Polygon3D(new float[][]{a, g, c}, rightNormal, Color.CYAN, new float[]{0.5f, 0.5f, 0});
        Polygon3D right2 = new Polygon3D(new float[][]{a, b, c}, rightNormal, Color.CYAN, new float[]{0.5f, 0.5f, 0});
        Polygon3D left = new Polygon3D(new float[][]{f, e, d}, leftNormal, Color.PINK, new float[]{0.5f, 0.5f, 1});
        Polygon3D left2 = new Polygon3D(new float[][]{f, k, d}, leftNormal, Color.PINK, new float[]{0.5f, 0.5f, 1});
        Mesh cube = new Mesh(new Polygon3D[]{up, up2, front, front2, back, back2, down, down2, right, right2, left, left2});
        cube.move(Functions3D.coordDiff(coord, new float[]{0.5f, 0.5f, 0.5f}));
        return cube;
    }
}
