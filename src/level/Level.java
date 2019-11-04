package level;

import entity.Player;
import entity.Tile;
import graphics.Screen;

import java.awt.*;

public class Level {

    private int width, height;
    private Player player;

    public Level(int width, int height, Player player) {
        this.width = width;
        this.height = height;
        this.player = player;
    }

    public Level(final Player player) {
        this.player = player;
        player.x = 980;
        player.y = 100;
    }

    public void generateLevel() {

        new Tile(200, 1000, 1500, 50, Color.BLUE);
        new Tile(1000, 850, 200, 50, Color.RED);
    }

    private void loadLevel(String path) {
        //load level file
    }

    public void update() {

    }

    public void render(Screen screen) {

    }

}
