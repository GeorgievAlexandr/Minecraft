package Functions3D;

public class Mesh {
    public Polygon3D[] polygons3D;
    public float[] pivot;

    public Mesh(Polygon3D[] polygons, float[] pivot) {
        this.polygons3D = polygons;
        this.pivot = pivot;
    }

    public Mesh(Polygon3D[] polygons) {
        this.polygons3D = polygons;
        calculatePivot(polygons);
    }

    private void calculatePivot(Polygon3D[] polygons) {
        //Рассчитывает pivot меша
        float[] pivotCoord = new float[3];
        int numberOfVertices = 0;
        for (Polygon3D polygon : polygons) {
            for (float[] vertex : polygon.vertices) {
                for (int axis = 0; axis < 3; axis++) {
                    pivotCoord[axis] += vertex[axis];
                }
                numberOfVertices++;
            }
        }
        for (int axis = 0; axis < 3; axis++) {
            pivotCoord[axis] /= numberOfVertices;
        }
        this.pivot = pivotCoord;
    }


    public void rotate(float[] angle) {
        //Поворачивает меш вокруг его пивота
        for (int i = 0; i < this.polygons3D.length; i++) {
            polygons3D[i].rotate(angle, this.pivot);
        }
    }

    public void move(float[] position) {
        //Двигает меш
        for (int i = 0; i < this.polygons3D.length; i++) {
            polygons3D[i].move(position);
        }
        this.pivot = Functions3D.coordSum(this.pivot, position);
    }
}
