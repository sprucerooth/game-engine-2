package entity;

import graphics.Sprite;
import input.KeyboardListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends Being {

    private final KeyboardListener input;
    private final double gravity = 0.25;
    private final double mass = 10;
    private final int maxJumpTime = 5;

    private double xMove = 0,
            yMove = 0,
            yForce = 0,
            xForce = 0,
            deltaSpeed = 0,
            deltaGravity = 0,
            deacceleration = 0.06,
            runSpeed = 20;
    private boolean jump = false;
    private int fallTime = 0;
    private int jumpTime = 0;

    public Player(KeyboardListener input, int x, int y, int width, int height, Color color, final Sprite sprite) {
        super(x, y, width, height, color, sprite);
        this.input = input;
    }

    private double gravityForce() {
        return 0.5 * gravity * fallTime * fallTime;
    }

    @Override
    public void update() {
        // TEMP return to start
        if (input.space) {
            x = 980;
            y = 100;
        }

        // Jump. If player is on ground OR if jumping within jumpTime
        if (input.up && (returnCollisionDistanceDown(this, 1) == 0 || jumpTime < maxJumpTime)) {
            yForce -= 5;
            jumpTime++;
        }

        // Horizontal movement
        deltaSpeed = deltaSpeed > 0 ? deltaSpeed - deacceleration : 0;

        // Left movement
        if (input.left) {
            xForce = -runSpeed;
            deltaSpeed = 1;
        }
        // Right movement
        if (input.right) {
            xForce = runSpeed;
            deltaSpeed = 1;
        }

        yMove = yForce + gravityForce();
        xMove = xForce * deltaSpeed;

        collision();

        move(((int) xMove), ((int) yMove));

        xMove = 0;
        yMove = 0;

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    /**
     * Check collision
     */
    private void collision() {
        // If in air
        if (returnCollisionDistanceDown(this, 1) > 0) {
            fallTime++;
        } else {
            fallTime = 0;
            yForce = 0;
            jumpTime = 0;
        }

        // Down collision
        if (yMove > 0) {
            yMove = returnCollisionDistanceDown(this, (int) yMove);
            // Up collision
        } else if (yMove < 0) {
            yMove = returnCollisionDistanceUp(this, yMove);
            System.out.println(returnCollisionDistanceUp(this, yMove) + "i want to move: " + yMove);
            if (yMove == 0) {
                yForce = 0;
            }
        }

        // Right collision
        if (xForce > 0) {
            xMove = returnCollisionDistanceRight(this, (int) xMove);

        }
        // Left collision
        if (xForce < 0) {
            xMove = returnCollisionDistanceLeft(this, (int) xMove);
        }

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