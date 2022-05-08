package objects;

import java.awt.*;

public final class Food extends GameObject {
    public Food(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.x, this.y, this.width, this.height);
    }
}
