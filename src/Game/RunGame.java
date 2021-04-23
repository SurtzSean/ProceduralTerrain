package Game;

import Engine.Input.InputManager;
import Engine.Rendering.Renderer;
import Engine.Rendering.Window;
import Engine.Terrains.TerrainManager;

public class RunGame{
    public static void main(String[] args){
            //get and init window
            Window w = Window.get();
            w.init();
            //set renderer for window
            Renderer r = new Renderer(w);
            //make terrain manager
            TerrainManager t = new TerrainManager(r.camera);
            //set inputmanager callback
            InputManager.setMousePosCallback(w.getGlfwWindow());
            while (!w.windowShouldClose()) {
                //get terrains based on current camera position
                t.setTerrainsToRender();
                //render terrains
                r.render(t.terrainObjs);
                w.update();
                //check input
                InputManager.checkInput(w.getGlfwWindow(), r.camera);
            }
    }

}
