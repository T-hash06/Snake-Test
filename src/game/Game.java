package game;

import control.Keyboard;
import graphics.Helper;
import graphics.Window;
import objects.Food;
import objects.GameObject;
import objects.Snake;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int CELLS_COUNT = WIDTH / GameObject.STANDARD_SIZE;
    private final String TITLE = "My Game";

    private int currentTPS = 0;
    private int currentFPS = 0;

    private boolean isRunning = false;

    private final Window window;
    private Thread thread;

    private final Keyboard keyboard;
    private final Snake snake;
    private final Food food;

    public Game() {
        this.window = new Window(WIDTH, HEIGHT, TITLE);
        this.keyboard = new Keyboard();

        this.window.getFrame().addKeyListener(keyboard);
        this.snake = new Snake(0, 0, Color.red);
        this.food = new Food(CELLS_COUNT, Color.green);
    }

    public void start() {
        thread = new Thread(this, "Graphics Thread");
        thread.start();
        isRunning = true;
    }

    public void stop() {
        try {
            thread.join();
            isRunning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        final int TPS = 15;
        final double NS_PER_SECOND = 1000000000;
        final double NS_PER_TICK = NS_PER_SECOND / TPS;

        long tickRef = System.nanoTime();
        long countRef = System.nanoTime();

        double time;
        double delta = 0;

        while (isRunning) {
            final long start = System.nanoTime();

            time = start - tickRef;
            tickRef = start;

            delta += time / NS_PER_TICK;

            while (delta >= 1) {
                tick();
                delta--;
            }

            draw();

            if (System.nanoTime() - countRef > NS_PER_SECOND) {
                window.getFrame().setTitle(TITLE + " || TPS: " + currentTPS + " || FPS: " + currentFPS);
                currentTPS = 0;
                currentFPS = 0;
                countRef = System.nanoTime();
            }
        }
    }

    private void tick() {
        keyboard.tick();
        snake.tick();

        if (keyboard.upPressed && snake.getDirection() != Snake.Direction.DOWN) {
            snake.setDirection(Snake.Direction.UP);
        }
        if (keyboard.downPressed && snake.getDirection() != Snake.Direction.UP) {
            snake.setDirection(Snake.Direction.DOWN);
        }
        if (keyboard.leftPressed && snake.getDirection() != Snake.Direction.RIGHT) {
            snake.setDirection(Snake.Direction.LEFT);
        }
        if (keyboard.rightPressed && snake.getDirection() != Snake.Direction.LEFT) {
            snake.setDirection(Snake.Direction.RIGHT);
        }

        if (snake.inCollision(food)) {
            food.reLocate(CELLS_COUNT);
            snake.growTail();
        }

        currentTPS++;
    }

    private void draw() {
        BufferStrategy bufferStrategy = window.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            window.getCanvas().createBufferStrategy(2);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        Helper.clearBackground(WIDTH, HEIGHT, graphics);
        Helper.drawCells(WIDTH, HEIGHT, GameObject.STANDARD_SIZE, graphics);

        //Draw Area

        snake.draw(graphics);
        food.draw(graphics);

        //Draw Area

        graphics.dispose();
        bufferStrategy.show();
        currentFPS++;
    }
}
