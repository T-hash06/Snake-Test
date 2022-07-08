package objects;

import java.awt.*;

public abstract class GameObject {

    public static final int STANDARD_SIZE = 30;
    protected Point position;
    protected final Color color;
    protected final int width = STANDARD_SIZE, height = STANDARD_SIZE;

    public GameObject(int x, int y, Color color) {
        this.color = color;
        this.position = new Point(x, y);
    }

    public abstract void tick();

    public abstract void draw(Graphics g);

    public boolean inCollision(GameObject object) {
        return this.position.x == object.position.x && this.position.y == object.position.y;
    }

    public boolean inCollision(GameObject firstObject, GameObject secondObject) {
        return firstObject.position.x == secondObject.position.x && firstObject.position.y == secondObject.position.y;
    }

    public Point getPosition() {
        return this.position;
    }
}
