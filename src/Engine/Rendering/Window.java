package Engine.Rendering;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Window {
    int width, height;
    String title;
    private long glfwWindow;
    private boolean resized;

    private static Window window = null;

    //set window dimensions
    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Procedural Terrain";
    }

    //window is Singleton
    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return window;
    }

    public void init(){
        System.out.println("LWJGL Version : " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();
        //error if glfw is not init
        if(!GLFW.glfwInit()){
            throw new IllegalStateException("GLFW not initialized");
        }
        GLFW.glfwDefaultWindowHints();
        //not maximized, resizable
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);

        //create window
        this.glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(glfwWindow == MemoryUtil.NULL){//check if issue creating window
            throw new IllegalStateException("Could not create GLFW Window");
        }

        GLFW.glfwSetFramebufferSizeCallback(glfwWindow, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });
        //set to primary monitor and in center of screen
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(
                this.glfwWindow,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        //set window context
        GLFW.glfwMakeContextCurrent(this.glfwWindow);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(this.glfwWindow);
        GL.createCapabilities();
        GL30.glViewport(0, 0, width, height);
        GL30.glEnable(GL30.GL_DEPTH_TEST);
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(glfwWindow);
    }

    public long getGlfwWindow(){
        return this.glfwWindow;
    }

    public boolean isResized(){
        return this.resized;
    }

    public void setResized(boolean resized){
        this.resized = resized;
    }

    public void update(){
        GLFW.glfwSwapBuffers(glfwWindow);
        GLFW.glfwPollEvents();
    }
}
