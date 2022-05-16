package logic;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public final class WorldManager {

    private final World[] worlds;
    private int countRenderedWorlds;
    private final int worldCount;
    private int generation;

    //TODO: Variable snake color
    public WorldManager(int worldCount, int cellsCount) {

        this.worlds = new World[worldCount];
        for (int i = 0; i < worldCount; i++) {
            this.worlds[i] = new World(cellsCount, Color.red, Color.green);
        }

        this.worldCount = worldCount;
        this.countRenderedWorlds = 1;
        this.generation = 0;
    }

    public void tick() {
        int worlsAlive = 0;
        for (World world : this.worlds) {
            if (world.isSnakeAlive()) {
                worlsAlive++;
                world.tick();
            }
        }

        if (worlsAlive == 0) {
            sortWorldsByScore();
            System.out.println(worlds[0].getScore());
            System.out.println("=====================");
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

    private void sortWorldsByScore() {
        Arrays.sort(this.worlds, Collections.reverseOrder());
    }

    public int getCountRenderedWorlds() {
        return countRenderedWorlds;
    }

    public void setCountRenderedWorlds(int countRenderedWorlds) {
        this.countRenderedWorlds = countRenderedWorlds;
    }
}
