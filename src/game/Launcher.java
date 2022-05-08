package game;

public class Launcher {

    private static Game game;

    public static void main(String[] args) {
        game = new Game();
        game.start();
        game.stop();
    }
}
