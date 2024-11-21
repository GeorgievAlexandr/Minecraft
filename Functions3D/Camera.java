package Functions3D;

import Settings.Settings;

//Camera
public class Camera {
    public float[] cameraPosition;
    public float[] cameraRotation;
    public Camera(float[] cameraPosition, float[] cameraRotation) {
        this.cameraPosition = cameraPosition;
        this.cameraRotation = cameraRotation;
    }

    public void localMove(float[] coord){
        this.cameraPosition = Functions3D.coordDiff(this.cameraPosition, Functions3D.rotateVector(coord, this.cameraRotation));
    }
    public void moveCamera(float[] coord){
        this.cameraPosition = Functions3D.coordSum(this.cameraPosition, coord);
    }
    public void rotateCamera(float[] coord) {this.cameraRotation = Functions3D.coordSum(this.cameraRotation, coord); }
    public void updatePosition(float dT){
        if (Input.upPressed){
            localMove(new float[]{Settings.VELOCITY * dT, 0, 0});
        }
        if (Input.downPressed){
            localMove(new float[]{-Settings.VELOCITY * dT, 0, 0});
        }
        if (Input.leftPressed){
            localMove(new float[]{0, 0, Settings.VELOCITY * dT});
        }
        if (Input.rightPressed){
            localMove(new float[]{0, 0, -Settings.VELOCITY * dT});
        }
    }
}
