package Functions3D;

import Display.Display;
import Settings.Settings;

public abstract class Functions3D {
    public static float vectorLength(float[] vector){
        //Расстояние от нуля координат до точки
        float vectorLength = (float) Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
        return vectorLength;
    }

    public static float[] screenCoord(float[] localCoord){
        float planeL = (float) (Display.WIDTH / Math.tan(Settings.FOV * 0.5));
        float[] screenCoord = new float[2];
        screenCoord[0] = (float) ((localCoord[2] / localCoord[0]) * planeL + Display.WIDTH * 0.5);
        screenCoord[1] = (float) ((localCoord[1] / localCoord[0]) * planeL + Display.WIDTH * 0.5);
        return screenCoord;
    }

    @Deprecated
    public static float[] coord3D2screenCoord(float[] localCoord) {
        //Локальные координаты в координаты на плоскости
        float[] polarCoord = new float[3];
        polarCoord[0] = (float) Math.atan(localCoord[2] / localCoord[0]); // z/x
        polarCoord[1] = (float) Math.atan(localCoord[1] / localCoord[0]); // y/x

        float[] screenCoord = new float[3];
        screenCoord[0] = polarCoord[0] / Settings.FOV * Display.WIDTH + Display.WIDTH * 0.5f;
        screenCoord[1] = (polarCoord[1] / Settings.FOV * Display.WIDTH + Display.HEIGHT * 0.5f);

        return screenCoord;
    }

    public static float[] coordDiff(float[] coord1, float[] coord2) {
        //Разность координат
        float[] coordDiff = new float[3];
        for (int i = 0 ; i < 3 ; i++) {
            coordDiff[i] = coord1[i] - coord2[i];
        }
        return coordDiff;
    }

    public static float[] coordSum(float[] coord1, float[] coord2) {
        //Сумма координат
        float[] coordSum = new float[3];
        for (int i = 0 ; i < 3 ; i++) {
            coordSum[i] = coord1[i] + coord2[i];
        }
        return coordSum;
    }

    public static float[] vectorWorldRotate(float[] vector, float[] pivotCoord, float[] angle) {
        //Поворачивает точку на заданный угол вокруг пивота
        float[] vectorLocal = rotateVector(coordDiff(vector, pivotCoord), angle);
        return coordSum(vectorLocal, pivotCoord);
    }

    public static float[] rotateVector(float[] localCoord, float[] angle) {
        //Поворачивает точку на заданный угол вокруг нуля координат
        float[] outVector = new float[3];
        //x
        if (angle[0] != 0) {
            outVector[1] = (float) (localCoord[1] * Math.cos(angle[0]) - localCoord[2] * Math.sin(angle[0]));
            outVector[2] = (float) (localCoord[1] * Math.sin(angle[0]) + localCoord[2] * Math.cos(angle[0]));
            localCoord[1] = outVector[1];
            localCoord[2] = outVector[2];
        }
        //y
        if (angle[1] != 0) {
            outVector[0] = (float) (localCoord[0] * Math.cos(angle[1]) - localCoord[2] * Math.sin(angle[1]));
            outVector[2] = (float) (localCoord[0] * Math.sin(angle[1]) + localCoord[2] * Math.cos(angle[1]));
            localCoord[0] = outVector[0];
            localCoord[2] = outVector[2];
        }
        //z
        if (angle[2] != 0) {
            outVector[0] = (float) (localCoord[0] * Math.cos(angle[2]) - localCoord[1] * Math.sin(angle[2]));
            outVector[1] = (float) (localCoord[0] * Math.sin(angle[2]) + localCoord[1] * Math.cos(angle[2]));
            localCoord[0] = outVector[0];
            localCoord[1] = outVector[1];
        }
        return localCoord;
    }

    public static float vectorAngle(float[] vectorPos1, float[] vectorPos2, float[] vector2Pos1, float[] vector2Pos2) {
        //Угол между векторами
        float[] vector1Local = coordDiff(vectorPos2, vectorPos1);
        float[] vector2Local = coordDiff(vector2Pos2, vector2Pos1);
        return (float) Math.acos((vector1Local[0] * vector2Local[0] + vector1Local[1] * vector2Local[1]
                + vector1Local[2] * vector2Local[2]) / (vectorLength(vector2Local) * vectorLength(vector2Local)));
    }
}
