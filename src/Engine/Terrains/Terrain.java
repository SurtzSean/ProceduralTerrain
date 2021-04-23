package Engine.Terrains;

import Engine.Rendering.Texture;
import fnl.FastNoiseLite;
import Engine.Utilities.GameMath;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Terrain {

    private int CHUNK_SIZE;
    private int terrainScale;
    private int MAX_HEIGHT;
    private float[] noiseMap;
    private float[] positions;
    private int[] indices;
    private float[] textures;
    private int idxID;
    private int posID;
    private int vaoID;
    private int texID;
    public int x;
    public int z;
    FastNoiseLite fnl;



    public Terrain(int terrainX, int terrainZ, int chunkSize, int maxHeight, int terrainScale, FastNoiseLite fnl){
        this.CHUNK_SIZE = chunkSize;
        this.MAX_HEIGHT = maxHeight;
        this.x = terrainX;
        this.z = terrainZ;
        this.fnl = fnl;
        this.terrainScale = terrainScale;
        noiseMap = generateTerrainNoise();
        positions = setupPosition();
        indices = setupEBO();
        textures = getTerrainTextures();
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        setupTextureVBO(textures);
        setupPosVBO(positions);
        setupIndex(indices);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    //determine what texture to use for terrain based off height
    private float[] getTerrainTextures(){
        ArrayList<Float> textures = new ArrayList<>();
        for(int i = 1; i < positions.length; i+=3){
            float textureLocation = ((positions[i]/MAX_HEIGHT) + 1) / 2;
            textures.add(textureLocation);
            textures.add(0f);
        }
        return GameMath.FloatArrayListToArray(textures);
    }


    //generate noise for terrain
    private float[] generateTerrainNoise(){
        ArrayList<Float> noise = new ArrayList<Float>();
        for(int z = 0; z < CHUNK_SIZE; z++){
            for(int x = 0; x < CHUNK_SIZE; x++){
                noise.add(generateNoise(x, z));
            }
        }
        return GameMath.FloatArrayListToArray(noise);
    }

    public float generateNoise(int x, int z){
        return fnl.GetNoise(x + (this.CHUNK_SIZE - 1) * this.x, z + (this.CHUNK_SIZE - 1) * this.z) * MAX_HEIGHT;
    }

    //XYZ positions set every loop
    private float[] setupPosition(){
        ArrayList<Float> pos = new ArrayList<Float>();
        for(int z = 0; z < CHUNK_SIZE; z++){
            for(int x = 0; x < CHUNK_SIZE; x++) {
                pos.add((float) x * terrainScale);
                pos.add(noiseMap[x + z * CHUNK_SIZE]);
                pos.add((float) z * terrainScale);
            }
        }
        return GameMath.FloatArrayListToArray(pos);
    }

    //get indices for EBO === tells how to draw triangles; e/a inner loop = 2 triangles
    /*
    v2---v3
    |  / |
    v1---v4
     */
    private int[] setupEBO(){
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for(int z = 0; z < CHUNK_SIZE - 1; z++) {
            for (int x = 0; x < CHUNK_SIZE - 1; x++) {
                int v1 = x + z * CHUNK_SIZE;
                int v2 = v1 + 1;
                int v3 = v1 + 1 + CHUNK_SIZE;
                int v4 = v1 + CHUNK_SIZE;
                //triangle 1
                indices.add(v1); //vert from curr col
                indices.add(v2); //vert from next col
                indices.add(v3); //vert from next col above

                //triangle 2
                indices.add(v1); //vert from curr col
                indices.add(v3); //vert from next col and above
                indices.add(v4); //vert from next col
            }
        }
        return GameMath.IntArrayListToArray(indices);
    }

    //render terrain chunk
    public void render(){
        glBindVertexArray(vaoID);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, texID);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    //connect terrain
    private void setupIndex(int[] indices){
        IntBuffer ib = MemoryUtil.memAllocInt(indices.length);
        ib.put(indices).flip();
        idxID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib, GL_STATIC_DRAW);
    }

    //setup vbo for position of terrain
    private void setupPosVBO(float[] positions){
        posID = glGenBuffers();
        FloatBuffer fb = MemoryUtil.memAllocFloat(positions.length);
        fb.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, posID);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }

    private void setupTextureVBO(float[] textures){
        texID = glGenBuffers();
        FloatBuffer fb = MemoryUtil.memAllocFloat(textures.length);
        fb.put(textures).flip();
        glBindBuffer(GL_ARRAY_BUFFER, texID);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
    }
}
