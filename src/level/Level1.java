package level;

import entity.Player;
import entity.Tile;

import java.awt.*;

public class Level1 implements Level {

    private Canvas canvas;
    private Player player;

    public Level1(final Canvas canvas, final Player player) {
        this.canvas = canvas;
        this.player = player;
        player.x = 980;
        player.y = 700;
    }

    public void generate() {

        new Tile(200, 1000, 1500, 50, Color.BLUE);
        new Tile(1000, 850, 200, 50, Color.RED);
        new Tile(900, 800, 350, 150, Color.RED);
        new Tile(300, 950, 200, 200, Color.RED);
    }

    public void render(Graphics g) {
        g.setColor(new Color(0x0101010));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void loadLevel(String path) {
        //load level file
    }

    public void update() {

    }

}
