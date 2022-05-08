package graphics;

import javax.swing.*;
import java.awt.*;

public class Window {

    private String title;
    private final int width, height;

    private final JFrame frame;
    private final Canvas canvas;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

        this.canvas = new Canvas();
        this.canvas.setBackground(new Color(40, 40, 40));

        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.setPreferredSize(new Dimension(width, height));
        this.frame.add(this.canvas);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
