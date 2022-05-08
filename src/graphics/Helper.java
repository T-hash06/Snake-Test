package graphics;

import java.awt.*;

public abstract class Helper {

    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    public static final Color CELLS_COLOR = new Color(60, 60, 60);

    public static void drawCells(int windowWidth, int windowHeight, int squareSize, Graphics graphics) {
        graphics.setColor(Helper.CELLS_COLOR);

        for (int x = 0; x < windowWidth; x += squareSize) {
            graphics.fillRect(x, 0, 1, windowHeight);
        }

        for (int y = 0; y < windowWidth; y += squareSize) {
            graphics.fillRect(0, y, windowWidth, 1);
        }

        graphics.setColor(Color.black);
    }

    public static void clearBackground(int windowWidth, int windowHeight, Graphics graphics) {
        graphics.setColor(Helper.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, windowWidth, windowHeight);
        graphics.setColor(Color.black);
    }
}
