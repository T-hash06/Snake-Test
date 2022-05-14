package logic;

import objects.Food;
import objects.Snake;

import java.awt.*;

public final class World {

    private Snake snake;
    private final Food food;
    private final boolean isRunning;
    private final boolean isSnakeAlive;
    private final int cellsCount;

    private double bornTime;

    public World(int cellsCount, Color snakeColor, Color foodColor) {
        this.cellsCount = cellsCount;
        this.food = new Food(cellsCount, foodColor);
        this.snake = new Snake(cellsCount / 2, cellsCount / 2, this.food.getPosition(), snakeColor, cellsCount);

        this.bornTime = System.nanoTime();
        this.isRunning = true;
        this.isSnakeAlive = true;
    }

    public void tick() {
        if (this.isRunning)
            this.snake.tick();

        if (snake.inCollision(food)) {
            food.reLocate(cellsCount);
            snake.growTail();
        }

        if (!snake.isAlive()) {
            this.food.reLocate(cellsCount);
            this.snake = new Snake(cellsCount / 2, cellsCount / 2, this.food.getPosition(), Color.red, cellsCount);
        }
    }

    public void draw(Graphics g) {
        this.snake.draw(g);
        this.food.draw(g);
    }

    //TODO: Config isNotAlive event
    public double getTimeAlive() {
        return System.nanoTime() - this.bornTime;
    }

    public boolean isSnakeAlive() {
        return isSnakeAlive;
    }
}
