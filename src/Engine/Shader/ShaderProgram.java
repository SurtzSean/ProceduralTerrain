package Engine.Shader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.lwjgl.opengl.GL30.*;

public class ShaderProgram {

    private final int pID;
    private int vsID; //vertex shader id
    private int fsID; //fragment shader id
    private Map<String, Integer> uniforms;

    public ShaderProgram() throws Exception{
        pID = glCreateProgram();
        uniforms = new HashMap<>();
    }

    private void makeVertexShader(String shaderName) throws Exception{
        String shaderCode = readFile(shaderName);
        vsID = createShader(shaderCode,GL_VERTEX_SHADER);
    }

    private void makeFragmentShader(String shaderName) throws Exception{
        String shaderCode = readFile(shaderName);
        fsID = createShader(shaderCode,GL_FRAGMENT_SHADER);
    }

    //make vertex and fragment shaders
    public void makeShaders() throws Exception {
        makeVertexShader("src/Engine/Shader/vertex.vs");
        makeFragmentShader("src/Engine/Shader/fragment.fs");
    }

    //reads file at path
    private String readFile(String path) throws FileNotFoundException {
        File f = new File(path);
        Scanner reader = new Scanner(f);
        String res = "";
        while(reader.hasNextLine())
            res+= reader.nextLine() + "\n";
        return res;
    }

    protected int createShader(String shaderCode, int shaderType){
        int sID = glCreateShader(shaderType);

        //send shader source code to opengl
        glShaderSource(sID, shaderCode);
        glCompileShader(sID);

        glAttachShader(pID, sID);
        return sID;
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniformName), false,
                    value.get(stack.mallocFloat(16)));
        }
    }

    public void link() throws Exception {
        glLinkProgram(pID);//link shader program by program id
        if(vsID != 0) glDetachShader(pID, vsID);
        if(fsID != 0) glDetachShader(pID, fsID);
        //validate shader program
        glValidateProgram(pID);
    }

    public void setUniform(String uniformName) throws Exception {
        int uniformID = glGetUniformLocation(pID, uniformName);
        uniforms.put(uniformName, uniformID);
    }

    public void bind(){
        glUseProgram(pID);
    }

    public void unbind(){
        glUseProgram(0);
    }
}
