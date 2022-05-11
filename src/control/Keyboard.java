package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Keyboard implements KeyListener {

    private final boolean[] keys = new boolean[120];

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean spacePressed;

    public boolean rightArrowPressed;
    public boolean leftArrowPressed;
    public boolean upArrowPressed;
    public boolean downArrowPressed;

    public void tick() {
        upPressed = keys[KeyEvent.VK_W];
        downPressed = keys[KeyEvent.VK_S];
        leftPressed = keys[KeyEvent.VK_A];
        rightPressed = keys[KeyEvent.VK_D];
        spacePressed = keys[KeyEvent.VK_SPACE];

        rightArrowPressed = keys[KeyEvent.VK_RIGHT];
        leftArrowPressed = keys[KeyEvent.VK_LEFT];
        upArrowPressed = keys[KeyEvent.VK_UP];
        downArrowPressed = keys[KeyEvent.VK_DOWN];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
