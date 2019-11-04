package entity;

import graphics.Screen;
import graphics.Sprite;

import java.awt.*;

public class Tile extends Entity {
    public Sprite sprite;

    public Tile(int x, int y, int width, int height, Color color, Sprite sprite) {
        super(x, y, width, height, color);
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public Tile(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void render(int x, int y, Screen screen) {

    }

    public boolean solid() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }
}
