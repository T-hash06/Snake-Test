package logic;

import java.awt.*;

public final class WorldManager {

    private final World[] worlds;

    //TODO: Variable snake color
    public WorldManager(int worldCount, int cellsCount) {

        this.worlds = new World[worldCount];
        for (int i = 0; i < worldCount; i++) {
            this.worlds[i] = new World(cellsCount, Color.red, Color.green);
        }
    }

    public void tick() {
        for (World world : this.worlds) {
            world.tick();
        }
    }

    public void draw(Graphics g) {
        for (World world : this.worlds) {
            world.draw(g);
        }
    }
}
