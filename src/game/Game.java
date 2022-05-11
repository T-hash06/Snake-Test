package game;

import control.Keyboard;
import graphics.Helper;
import graphics.Window;
import logic.WorldManager;
import objects.GameObject;

import java.awt.*;
import java.awt.image.BufferStrategy;

public final class Game implements Runnable {

    private final int CELLS_COUNT = 20;
    private final int WIDTH = CELLS_COUNT * GameObject.STANDARD_SIZE;
    private final int HEIGHT = CELLS_COUNT * GameObject.STANDARD_SIZE;
    private final String TITLE = "My Game";

    private int currentTPS = 0;
    private int currentFPS = 0;

    private boolean isRunning = false;

    private final Window window;
    private Thread thread;

    private final Keyboard keyboard;
    private final WorldManager worldManager;

    public Game() {
        this.window = new Window(WIDTH, HEIGHT, TITLE);
        this.keyboard = new Keyboard();

        this.window.getFrame().addKeyListener(keyboard);
        this.worldManager = new WorldManager(5, CELLS_COUNT);
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

        worldManager.tick();

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

        //Draw Area
        worldManager.draw(graphics);

        //Draw Area

        Helper.drawCells(WIDTH, HEIGHT, GameObject.STANDARD_SIZE, graphics);
        graphics.dispose();
        bufferStrategy.show();
        currentFPS++;
    }
}
