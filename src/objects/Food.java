package objects;

import java.awt.*;

public final class Food extends GameObject {
    public Food(int cellsCount, Color color) {
        super(0, 0, color);

        this.reLocate(cellsCount);
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.x, this.y, this.width, this.height);
    }

    public void reLocate(int cellsCount) {
        int randomX = (int) Math.round(Math.random() * (cellsCount - 1));
        int randomY = (int) Math.round(Math.random() * (cellsCount - 1));

        this.x = randomX * STANDARD_SIZE;
        this.y = randomY * STANDARD_SIZE;

        this.updatePosition();
    }
}
