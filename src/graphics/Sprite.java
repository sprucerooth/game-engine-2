package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

    private int width, height, x, y;
    private double xScale, yScale;
    public int[] pixels;
    private SpriteSheet sheet;
    private BufferedImage image;

    public static Sprite moose = new Sprite(0, 0, 256, 256, SpriteSheet.moose);

    public Sprite(int column, int row, int width, int height, SpriteSheet sheet) {
        this.x = column * width;
        this.y = row * height;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
        pixels = new int[width * height];
        load();
    }

    public Sprite(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.xScale = 1;
        this.yScale = 1;
    }

    private void load() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getXscale() {
        return xScale;
    }

    public void setXscale(double xScale) {
        this.xScale = xScale;
    }

    public double getYscale() {
        return yScale;
    }

    public void setYscale(double yScale) {
        this.yScale = yScale;
    }

    public static Sprite createSprite() {
        Sprite sprite;
        try {
            return new Sprite(ImageIO.read(new File("res/moose.png")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
