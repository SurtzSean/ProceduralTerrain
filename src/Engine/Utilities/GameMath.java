package Engine.Utilities;

import Engine.Terrains.TerrainWrapper;
import Engine.Rendering.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
public class GameMath {

    //get projection matrix (eye space -> clip space)
    public static Matrix4f calculateProjectionMatrix(float fov, float aspectRatio, float zNear, float zFar){
        Matrix4f projMatrix = new Matrix4f();
        projMatrix.identity();
        projMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projMatrix;
    }

    //get camera's view matrix
    public static Matrix4f calculateViewMatrix(Camera cam) {
        Matrix4f vMatrix = new Matrix4f();
        vMatrix.identity();
        Vector3f cameraPos = cam.getPosition();
        Vector3f rotation = cam.getRotation();
        float xAngle = (float)Math.toRadians(rotation.x);
        float yAngle = (float)Math.toRadians(rotation.y);
        Vector3f xAxis = new Vector3f(1, 0, 0);
        Vector3f yAxis = new Vector3f(0f, 1f, 0f);
        vMatrix.rotate(xAngle, xAxis).rotate(yAngle,yAxis);
        vMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return vMatrix;
    }

    //calculate model view matrix brings from model space to eye space (model space -> world space -> eye space)
    public static Matrix4f calculateMVM(Vector3f position, Vector3f rotation, float scale, Matrix4f viewMatrix) {
        Matrix4f mMatrix = new Matrix4f();
        mMatrix = mMatrix.identity();
        float xRotate = (float)Math.toRadians(-rotation.x);
        float yRotate = (float)Math.toRadians(-rotation.y);
        float zRotate = (float)Math.toRadians(-rotation.z);
        mMatrix.translate(position).rotateX(xRotate).rotateY(yRotate).rotateZ(zRotate).scale(scale);
        Matrix4f vMatrix = new Matrix4f(viewMatrix);
        return vMatrix.mul(mMatrix);
    }

    //convert float arraylist to array
    public static float[] FloatArrayListToArray(ArrayList<Float> f){
        float[] fArray = new float[f.size()];
        for(int i = 0; i < f.size(); i++){
            fArray[i] = f.get(i);
        }
        return fArray;
    }

    //convert int arraylist to array
    public static int[] IntArrayListToArray(ArrayList<Integer> i){
        int[] iArray = new int[i.size()];
        for(int j = 0; j < i.size(); j++){
            iArray[j] = i.get(j);
        }
        return iArray;
    }

}
