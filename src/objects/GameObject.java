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

    //Return -1 if object is not found
    public double distanceToObjectInDirection(Snake.Direction direction, GameObject object, GameObject target) {
        int distance = -1;

        if (direction == Snake.Direction.UP && object.position.x == target.position.x && object.position.y < target.position.y)
            return object.position.distance(target.position);

        if (direction == Snake.Direction.DOWN && object.position.x == target.position.x && object.position.y > target.position.y)
            return object.position.distance(target.position);

        if (direction == Snake.Direction.LEFT && object.position.y == target.position.y && object.position.x > target.position.x)
            return object.position.distance(target.position);

        if (direction == Snake.Direction.RIGHT && object.position.y == target.position.y && object.position.x < target.position.x)
            return object.position.distance(target.position);

        return distance;
    }

    public double distanceToObjectInDirection(Snake.Direction direction, Point object, Point target) {
        int distance = -1;

        if (direction == Snake.Direction.UP && object.x == target.x && object.y < target.y)
            return object.distance(target);

        if (direction == Snake.Direction.DOWN && object.x == target.x && object.y > target.y)
            return object.distance(target);

        if (direction == Snake.Direction.LEFT && object.y == target.y && object.x > target.x)
            return object.distance(target);

        if (direction == Snake.Direction.RIGHT && object.y == target.y && object.x < target.x)
            return object.distance(target);

        return distance;
    }
}
