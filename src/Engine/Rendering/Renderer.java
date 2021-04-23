package Engine.Rendering;

import Engine.Terrains.TerrainWrapper;
import Engine.Shader.ShaderProgram;
import Engine.Utilities.GameMath;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class Renderer {
    private Window window;
    private ShaderProgram shaderProgram;

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;
    public Camera camera;

    public Renderer (Window window){
        setupShaderProgram();
        this.window = window;
        camera = new Camera();
    }

    private void setupShaderProgram(){
        try
        {
            shaderProgram = new ShaderProgram();
            shaderProgram.makeShaders();
            shaderProgram.link();
            shaderProgram.setUniform("projectionMatrix");
            shaderProgram.setUniform("modelViewMatrix");
            shaderProgram.setUniform("tex");
        }
        catch (Exception e){System.err.println("error loading shaders");e.printStackTrace();}
    }

    public void render(ArrayList<TerrainWrapper> terrains){
        clear();
        //checkResize();
        shaderProgram.bind();
        projectionMatrix = GameMath.calculateProjectionMatrix(camera.getFOV(),
                (float)(window.width)/(float)(window.height), camera.getZNEAR(), camera.getZFAR());
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        viewMatrix = GameMath.calculateViewMatrix(camera);
        renderTerrainChunks(terrains);
        shaderProgram.unbind();
    }

    private void renderTerrainChunks(ArrayList<TerrainWrapper> tws){
            for(TerrainWrapper tw : tws){
                Matrix4f mvm = GameMath.calculateMVM(tw.getPosition(), tw.getRotation(), tw.getScale(), viewMatrix);
                shaderProgram.setUniform("modelViewMatrix", mvm);
                tw.terrain.render();
            }
    }

    private void checkResize(){
        if (window.isResized()) {
            GL30.glViewport(0, 0, window.width, window.height);
            window.setResized(false);
        }
    }


    public void clear(){
        GL30.glClearColor(0.0f, 0.5f, .85f, 1.0f);
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
    }

}
