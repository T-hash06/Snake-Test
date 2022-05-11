package logic;

import objects.Food;
import objects.Snake;

import java.awt.*;

public final class World {

    private Snake snake;
    private final Food food;
    private final boolean isRunning;
    private final int cellsCount;

    public World(int cellsCount, Color snakeColor, Color foodColor) {
        this.cellsCount = cellsCount;
        this.snake = new Snake(cellsCount / 2, cellsCount / 2, snakeColor, cellsCount);
        this.food = new Food(cellsCount, foodColor);
        this.isRunning = true;
    }

    public void tick() {
        if (this.isRunning)
            this.snake.tick();

        if (snake.inCollision(food)) {
            food.reLocate(cellsCount);
            snake.growTail();
        }

        if (!snake.isAlive()) {
            this.snake = new Snake(cellsCount / 2, cellsCount / 2, Color.red, cellsCount);
            this.food.reLocate(cellsCount);
        }
    }

    public void draw(Graphics g) {
        this.snake.draw(g);
        this.food.draw(g);
    }
}
