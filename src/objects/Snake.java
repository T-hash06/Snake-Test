package objects;

import java.awt.*;

public final class Snake extends GameObject {
    public enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    private final boolean isHead;
    private Direction direction;
    private Snake tail;
    private boolean isAlive;
    private int positionLimit;

    public Snake(int x, int y, Color color, int cellsCount) {
        super(x * STANDARD_SIZE, y * STANDARD_SIZE, color);

        this.direction = Direction.RIGHT;
        this.isHead = true;
        this.isAlive = true;
        this.positionLimit = cellsCount - 1;
    }

    public Snake(boolean isHead, int x, int y, Color color) {
        super(x * STANDARD_SIZE, y * STANDARD_SIZE, color);

        this.direction = Direction.RIGHT;
        this.isHead = isHead;
    }

    @Override
    public void tick() {
        int lastX = this.x;
        int lastY = this.y;

        if (this.isHead) {
            if (this.direction == Direction.RIGHT) {
                this.x += this.width;
            }
            if (this.direction == Direction.LEFT) {
                this.x -= this.width;
            }
            if (this.direction == Direction.DOWN) {
                this.y += this.height;
            }
            if (this.direction == Direction.UP) {
                this.y -= this.height;
            }
        }

        if (this.tail != null) {
            this.tail.tick(lastX, lastY);
        }

        isAlive = !this.inCollisionWithTail(this) && !this.outOfLimits();
    }

    private void tick(int x, int y) {
        int lastX = this.x;
        int lastY = this.y;

        this.x = x;
        this.y = y;

        if (this.tail != null) {
            this.tail.tick(lastX, lastY);
        }
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.x, this.y, this.width, this.height);

        if (this.tail != null)
            tail.draw(g);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void growTail() {
        if (this.tail == null) {
            this.tail = new Snake(false, this.x, this.y, this.color);
        } else {
            this.tail.growTail();
        }
    }

    private boolean outOfLimits() {
        return (this.x < 0 || this.x > positionLimit * STANDARD_SIZE) || (this.y < 0 || this.y > positionLimit * STANDARD_SIZE);
    }

    public boolean inCollisionWithTail(Snake head) {
        if (this.tail != null) {
            if (this.inCollision(head, this.tail)) return true;
            else return this.tail.inCollisionWithTail(head);
        }
        return false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
