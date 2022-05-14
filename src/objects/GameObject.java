package objects;

import java.awt.*;

public abstract class GameObject {

    public static final int STANDARD_SIZE = 30;
    protected int x, y;
    protected Point position;
    protected final Color color;
    protected final int width = STANDARD_SIZE, height = STANDARD_SIZE;

    public GameObject(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.position = new Point(x, y);
    }

    public abstract void tick();

    public abstract void draw(Graphics g);

    public boolean inCollision(GameObject object) {
        return this.x == object.x && this.y == object.y;
    }

    public boolean inCollision(GameObject firstObject, GameObject secondObject) {
        return firstObject.x == secondObject.x && firstObject.y == secondObject.y;
    }

    public Point getPosition() {
        return this.position;
    }

    public void updatePosition() {
        this.position = new Point(this.x, this.y);
    }
}
