package Engine.Terrains;

import Engine.Rendering.Texture;
import Engine.Rendering.Camera;
import org.joml.Vector2i;
import org.joml.Vector3f;
import fnl.FastNoiseLite;

import java.util.*;

public class TerrainManager {
    private int MAX_TERRAINS = 10;
    private final int terrainScale = 75;
    private final int CHUNK_SIZE = 100;
    private final int MAX_HEIGHT = 10000;
    private Camera cam;
    private int prevCamChunkX = -1;
    private int prevCamChunkZ = -1;
    private int camChunkX = 1;
    private int camChunkZ = 1;
    private FastNoiseLite fnl = new FastNoiseLite();
    private LinkedHashMap<Vector2i, Terrain> terrainHashMap = new LinkedHashMap<Vector2i, Terrain>(){
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest) {
            return size() > MAX_TERRAINS * MAX_TERRAINS * 10;
        }
    };
    private Texture tex;
    public ArrayList<Terrain> terrains = new ArrayList<Terrain>();
    public ArrayList<TerrainWrapper> terrainObjs = new ArrayList<TerrainWrapper>();
    private int seed = new Random().nextInt() * 2987347;

    public TerrainManager(Camera cam){
        tex = new Texture("texture/1DTexture.png");
        fnl.SetSeed(seed);
        fnl.SetFractalOctaves(16);
        fnl.SetFrequency(.0052f);
        fnl.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        this.cam = cam;
        setTerrainsToRender();
    }

    public void setTerrainsToRender(){
        setCameraChunkPosition();
        //if in same chunk do nothing
        if(prevCamChunkX == camChunkX && prevCamChunkZ == camChunkZ){
            return;
        }
        else{//if in different chunk find what terrains need to be rendered
            terrains.clear();
            terrainObjs.clear();
            for(int i = camChunkX - MAX_TERRAINS; i < MAX_TERRAINS + camChunkX; i++){
                for(int j = camChunkZ - MAX_TERRAINS; j < MAX_TERRAINS + camChunkZ; j++){
                    Terrain t;
                    if (!terrainHashMap.containsKey(new Vector2i(i, j))) {
                        t = new Terrain(i, j, CHUNK_SIZE, MAX_HEIGHT, terrainScale, fnl);
                        terrainHashMap.put(new Vector2i(i, j),t);
                    }
                    else{
                        t = terrainHashMap.get(new Vector2i(i, j));
                    }
                    terrains.add(t);
                }
            }
            //set terrain position and add to terrain objs to be rendered by renderer
            for(Terrain t : terrains) {
                TerrainWrapper g = new TerrainWrapper(t);
                g.setScale(terrainScale);
                g.setPosition(new Vector3f(g.getPosition().x, -5000f, g.getPosition().z));
                g.setPosition(new Vector3f(t.x * (CHUNK_SIZE - 1) * terrainScale * terrainScale, (float) (0.0), t.z * (CHUNK_SIZE - 1) * terrainScale * terrainScale));
                terrainObjs.add(g);
            }
        }
    }

    //check chunk position camera is in
    private void setCameraChunkPosition(){
        prevCamChunkX = camChunkX;
        prevCamChunkZ = camChunkZ;
        camChunkX = (int)(cam.getPosition().x / ((CHUNK_SIZE - 1) * terrainScale * terrainScale));
        camChunkZ = (int)(cam.getPosition().z / ((CHUNK_SIZE - 1) * terrainScale * terrainScale));
    }

}
