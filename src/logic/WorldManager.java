package logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class WorldManager {

    private World[] worlds;
    private int countRenderedWorlds;
    private final int worldCount;
    private int generation;
    private final int cellsCount;

    //TODO: Variable snake color
    //TODO: Odd worlds count support
    public WorldManager(int worldCount, int cellsCount) {
        if (worldCount % 2 != 0) System.out.println("Odd worlds count!");

        this.worlds = new World[worldCount];
        for (int i = 0; i < worldCount; i++) {
            this.worlds[i] = new World(cellsCount, Color.red, Color.green);
        }

        this.worldCount = worldCount;
        this.countRenderedWorlds = worldCount;
        this.generation = 0;
        this.cellsCount = cellsCount;
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
            generateNewBestWorlds();
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < countRenderedWorlds; i++) {
            if (worlds[i].isSnakeAlive())
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

    private void generateNewBestWorlds() {
        World[] newWorlds = new World[worldCount];
        ArrayList<World> bestWorlds = new ArrayList<>();

        for (int index = 0; index < worldCount; index += 2) {
            NeuralNetwork temporalBrain = NeuralNetwork.
                    createByAverageLayers(
                            worlds[index].getSnake().getBrain(),
                            worlds[index + 1].getSnake().getBrain());

            newWorlds[index] = new World(cellsCount, temporalBrain, Color.red, Color.green);
            newWorlds[(index) + 1] = new World(cellsCount, temporalBrain, Color.red, Color.green);
        }

        this.worlds = newWorlds.clone();
    }

    public boolean fitnessFunction(double value) {
        return value > 1;
    }

    public int getCountRenderedWorlds() {
        return countRenderedWorlds;
    }

    public void setCountRenderedWorlds(int countRenderedWorlds) {
        this.countRenderedWorlds = countRenderedWorlds;
    }
}
