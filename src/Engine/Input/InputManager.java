package Engine.Input;

import Engine.Rendering.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class InputManager {

    private static boolean right = false;
    private static boolean left = false;
    private static boolean forward = false;
    private static boolean back = false;
    private static boolean up = false;
    private static boolean down = false;
    private static Vector2f prevMousePos = new Vector2f(0, 0);
    private static Vector2f mousePos = new Vector2f(1, 1);
    //mouse callback to update position
    private static GLFWCursorPosCallbackI MousePositionCallback = ((window, xpos, ypos) -> {
        mousePos.x = (float)xpos; mousePos.y = (float)ypos;
    });

    public static void moveCamera(long glfwWindow, Camera cam){
        Vector3f posOffset = new Vector3f(0, 0, 0);
        //check if keys are pressed/held down
        if(GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) forward = true;
        else forward = false;
        if(GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) left = true;
        else left = false;
        if(GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) back = true;
        else back = false;
        if(GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) right = true;
        else right = false;
        if(GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_BACKSPACE) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) up = true;
        else up = false;
        if(GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) down = true;
        else down = false;

        //update position when key is pressed/held down
        if(forward) posOffset.z -= 5000f;
        if(back) posOffset.z += 5000f;
        if(left) posOffset.x -= 5000f;
        if (right) posOffset.x += 5000f;
        if(up) posOffset.y += 5000f;
        if(down) posOffset.y -= 5000f;

        cam.move(posOffset);
    }

    //rotate when mouse moves
    public static void rotateCamera(Camera cam){
        Vector2f moveOffset = new Vector2f(mousePos.x - prevMousePos.x, mousePos.y - prevMousePos.y);
        prevMousePos.x = mousePos.x;
        prevMousePos.y = mousePos.y;
        if(moveOffset.x != 0) cam.changeRotation(new Vector3f(0, moveOffset.x, 0));
        //dont let user flip camera over or under
        if(moveOffset.y != 0 && cam.getRotation().x + moveOffset.y < 90 && cam.getRotation().x + moveOffset.y > -90) cam.changeRotation(new Vector3f(moveOffset.y, 0, 0));
    }

    //set mouse callback function
    public static void setMousePosCallback(long glfwWindow){
        GLFW.glfwSetCursorPosCallback(glfwWindow, MousePositionCallback);
    }

    //calls move/rotate functions
    public static void checkInput(long glfwWindow, Camera camera){
        moveCamera(glfwWindow, camera);
        rotateCamera(camera);
    }

}
