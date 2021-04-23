package Engine.Terrains;

import Engine.Terrains.Terrain;
import org.joml.Vector3f;


public class TerrainWrapper {

    public Terrain terrain;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    //wrapper for terrain to change rotation, position, scale, etc.
    public TerrainWrapper(Terrain terrain){
        this.terrain = terrain;
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = 1;
    }

    public void setPosition(Vector3f pos){
        this.position = pos;
    }

    public void setRotation(Vector3f rotation){
        this.rotation = rotation;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Terrain getTerrain(){return this.terrain;}
}
