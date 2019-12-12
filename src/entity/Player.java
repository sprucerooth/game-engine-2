package entity;

import graphics.Sprite;
import input.KeyboardListenerImpl;

import java.awt.*;

public class Player extends Being {

    private final KeyboardListenerImpl input;
    private final double jumpForce = 25;
    private final double gravity = 1.8;
    private final double runSpeed = 5;

    private boolean inAir, jumping, spaceAbove = false;
    private double inAirTick = 0;

    public Player(KeyboardListenerImpl input, int x, int y, int width, int height, Color color, final Sprite sprite) {
        super(x, y, width, height, color, sprite);
        this.input = input;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void update() {
        checkGravity();
        checkInput();
        checkCollision();

        move(((int) xMove), ((int) yMove));
        xMove = 0;
        yMove = 0;
    }

    private void checkGravity() {
        if (!inAir || hittingTheHead()) {
            jumping = false;
            inAirTick = 0;
        } else {
            if (jumping) yMove = -jumpForce;
            yMove += gForce();
            inAirTick++;
        }
    }

    private void checkInput() {
        if (input.up) {
            if (!inAir) {
                yMove = -jumpForce + gForce();
                inAirTick++;
                jumping = true;
            }
        }

        if (input.left) {
            xMove = -runSpeed;
        } else if (input.right) {
            xMove = runSpeed;
        }

        if (input.space) {
            x = 980;
            y = 100;
            xMove = yMove = inAirTick = 0;
        }

    }

    private void checkCollision() {
        inAir = possibleTravelDistanceDown(this, 1) > 0;
        spaceAbove = possibleTravelDistanceUp(this, -1) != 0;

        if (yMove > 0) yMove = possibleTravelDistanceDown(this, (int) yMove);
        else if (yMove < 0) yMove = possibleTravelDistanceUp(this, yMove);

        if (xMove > 0) xMove = possibleTravelDistanceRight(this, (int) xMove);
        else if (xMove < 0) xMove = possibleTravelDistanceLeft(this, (int) xMove);
    }

    private double gForce() {
        return gravity * inAirTick;
    }

    private boolean hittingTheHead() {
        return jumping && !spaceAbove;
    }
}