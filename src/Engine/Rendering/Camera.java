package Engine.Rendering;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;
    private float FOV = (float)Math.toRadians(75);
    private float ZNEAR = 100f;
    private float ZFAR = 100000000f;

    public Camera(){
        position = new Vector3f(1,-.01f,10);
        rotation = new Vector3f(0,0,0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
    
    public void changeRotation(Vector3f rotationOffset){
        rotation.add(rotationOffset);
    }

    //moves camera based off offset vector
    public void move(Vector3f posOffset){
        //move z - left/right
        position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * posOffset.z;
        position.z += (float)Math.cos(Math.toRadians(rotation.y)) * posOffset.z;
        //move x - forward/back
        position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * posOffset.x;
        position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * posOffset.x;
        //move up/down
        position.y += posOffset.y;
    }

    public void setFOV(float FOV) {
        this.FOV = FOV;
    }

    public float getFOV() {
        return FOV;
    }

    public float getZNEAR() {
        return ZNEAR;
    }

    public float getZFAR() {
        return ZFAR;
    }
}
