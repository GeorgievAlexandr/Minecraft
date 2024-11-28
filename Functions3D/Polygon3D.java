package Functions3D;

import java.awt.*;

public class Polygon3D{
    public float[][] vertices;
    public float[][] localVertices;
    public float[][] normal;
    public float distance;
    public float normalAngle = 0;
    public float[] pivot;
    public float[] localPivot;
    public boolean visible = true;
    public Polygon polygon2D;
    public Color color;

    public Polygon3D(float[][] vertices, Color color, float[] pivot) {
        this.vertices = vertices;
        this.color = color;
        this.pivot = pivot;
    }
    public Polygon3D(float[][] vertices, float[][] normal, Color color, float[] pivot) {
        this.vertices = vertices;
        this.normal = normal;
        this.color = color;
        this.pivot = pivot;
    }
    public Polygon3D(float[][] verticles, Color color){
        this.vertices = verticles;
        this.color = color;
        calculatePivot();
    }
    public Polygon3D(float[][] vertices) {
        this.vertices = vertices;
        calculatePivot();
    }

    public void rotate(float[] angle, float[] pivotCoord) {
        //Поворачивает полигон вокруг данного pivot-a
        for (int i = 0; i < vertices.length; i++) {
            this.vertices[i] = Functions3D.vectorWorldRotate(this.vertices[i], pivotCoord, angle);
        }
        try {
            this.normal[0] = Functions3D.vectorWorldRotate(this.normal[0], pivotCoord, angle);
            this.normal[1] = Functions3D.vectorWorldRotate(this.normal[1], pivotCoord, angle);
        }
        catch (Exception e) {
            System.out.println("No normal");
        }
        this.pivot = Functions3D.vectorWorldRotate(this.pivot, pivotCoord, angle);
    }

    public void move(float[] coord){
        //Двигает полигон
        for (int i = 0; i < vertices.length; i++) {
            this.vertices[i] = Functions3D.coordSum(this.vertices[i], coord);
        }
        this.pivot = Functions3D.coordSum(this.pivot, coord);
    }

    private void calculatePivot(){
        //Рассчитывает pivot, лучше этим не пользоватся
        float[] pivotCoord = new float[3];
        for (int i = 0; i < vertices.length; i++) {
            for (int axis = 0; axis < 3; axis++) {
                pivotCoord[axis] += vertices[i][axis];
            }
        }

        for (int axis = 0; axis < 3; axis++) {
            pivotCoord[axis] /= vertices.length;
        }
        this.pivot = pivotCoord;
    }

    public void calculateDistance(){
        //Рассчитывает расстояние до pivot-a полигона
        this.distance = Functions3D.vectorLength(this.localPivot);
    }

    public void calculateNormalAngle(float[] position){
        //Рассчитывает угол между нормалью полигона и лучом из камеры, проходящем через pivot
        this.normalAngle = Functions3D.vectorAngle(this.normal[0], this.normal[1], this.pivot, position);
    }

    public void calculateLocalVerticles(float[] position, float[] rotation){
        //Рассчитывает локальные координаты вершин полигона
        this.localVertices = new float[3][3];
        for (int i = 0; i < 3; i++) {
            this.localVertices[i] = Functions3D.rotateVector(Functions3D.coordDiff(this.vertices[i], position), rotation);
        }
    }

    public void calculateLocalPivot(float[] position, float[] rotation){
        //Рассчитывает локальные координаты pivot-a полигона
        this.localPivot = Functions3D.rotateVector(Functions3D.coordDiff(this.pivot, position), rotation);
    }

    public void calculatePolygon2D() {
        //Рассчитывает полигон для вывода на экран
        int[] polygonsX = new int[3];
        int[] polygonsY = new int[3];
        for (int j = 0; j < 3; j++) {
            float[] screenCoordF = Functions3D.screenCoord(this.localVertices[j]);
            polygonsX[j] = (int) screenCoordF[0];
            polygonsY[j] = (int) screenCoordF[1];
        }
        this.polygon2D = new Polygon(polygonsX, polygonsY, 3);
    }
}
