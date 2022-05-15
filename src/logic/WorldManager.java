package logic;

import java.awt.*;

public final class WorldManager {

    private final World[] worlds;
    private int countRenderedWorlds;
    private final int worldCount;

    //TODO: Variable snake color
    public WorldManager(int worldCount, int cellsCount) {

        this.worlds = new World[worldCount];
        for (int i = 0; i < worldCount; i++) {
            this.worlds[i] = new World(cellsCount, Color.red, Color.green);
        }

        this.worldCount = worldCount;
        this.countRenderedWorlds = 1;
    }

    public void tick() {
        int worlsAlive = 0;
        for (World world : this.worlds) {
            if (world.isSnakeAlive()) {
                worlsAlive++;
                world.tick();
            }
        }
        

    }

    public void draw(Graphics g) {
        for (int i = 0; i < countRenderedWorlds; i++) {
            worlds[i].draw(g);
        }
    }

    public void increaseCountRenderedWorlds(int step) {
        if (step > 0 && countRenderedWorlds + step > worldCount) {
            this.countRenderedWorlds = worldCount;
            return;
        }
        if (step < 0 && countRenderedWorlds + step < 2) {
            this.countRenderedWorlds = 1;
            return;
        }

        this.countRenderedWorlds += step;
    }

    public int getCountRenderedWorlds() {
        return countRenderedWorlds;
    }

    public void setCountRenderedWorlds(int countRenderedWorlds) {
        this.countRenderedWorlds = countRenderedWorlds;
    }
}
