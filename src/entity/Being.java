package entity;

import graphics.Sprite;

import java.awt.*;

public abstract class Being extends Entity {
    Sprite sprite;
    protected int dir = 0;

    Being(final int x, final int y, final int width, final int height, final Color color, final Sprite sprite) {
        super(x, y, width, height, color);
        this.sprite = sprite;
    }

    void move(final int xa, final int ya) {
        x += xa;
        y += ya;
    }

    public void render(final Graphics g) {

        final int isFlipped;
        if (this.sprite.getXscale() < 0) {
            isFlipped = 1;
        } else {
            isFlipped = 0;
        }

        g.drawImage(this.sprite.getImage(),
                this.x + (this.sprite.getWidth() * isFlipped),
                this.y,
                (int) (this.sprite.getWidth() * this.sprite.getXscale()),
                (int) (this.sprite.getHeight() * this.sprite.getYscale()),
                null);
    }

}
