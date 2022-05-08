package objects;

import java.awt.*;

public abstract class GameObject {

    public static final int STANDARD_SIZE = 20;
    protected int x, y;
    protected final Color color;
    protected final int width = STANDARD_SIZE, height = STANDARD_SIZE;

    public GameObject(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract void tick();

    public abstract void draw(Graphics g);

    public boolean inCollision(GameObject object) {
        return false;
    }
}