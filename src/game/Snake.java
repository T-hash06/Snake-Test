package game;

import java.awt.*;

public final class Snake extends GameObject {
    public Snake(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void tick() {
        System.out.println("Snake");
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.x, this.y, this.width, this.height);
    }
}
