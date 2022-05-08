package objects;

import java.awt.*;

public final class Snake extends GameObject {
    public enum Direction {
        RIGHT, LEFT, UP, DOWN;
    }

    private Direction direction;

    public Snake(int x, int y, Color color) {
        super(x, y, color);

        this.direction = Direction.RIGHT;
    }

    @Override
    public void tick() {

        if (this.direction == Direction.RIGHT) this.x++;
        if (this.direction == Direction.LEFT) this.x--;
        if (this.direction == Direction.DOWN) this.y++;
        if (this.direction == Direction.UP) this.y--;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.x, this.y, this.width, this.height);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
