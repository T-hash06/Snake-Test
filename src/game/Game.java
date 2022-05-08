package game;

import graphics.Window;

public class Game implements Runnable {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private boolean isRunning = false;

    private final Window window;
    private Thread thread;

    public Game() {
        window = new Window(WIDTH, HEIGHT, "My Game");
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
    }

    private void tick() {

    }

    private void draw() {

    }
}
