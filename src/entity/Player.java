package entity;

import input.KeyListenerImpl;
import input.MouseListenerImpl;

import java.awt.*;

public class Player extends Being {

    private final KeyListenerImpl keyInput;
    private final MouseListenerImpl mouseInput;
    private final double jumpForce = 25;
    private final double gravity = 1.8;
    private final double runSpeed = 5;

    private boolean inAir, jumping, spaceAbove = false;
    private double inAirTick = 0;

    public Player(KeyListenerImpl keyInput, MouseListenerImpl mouseInput) {
        super(0, 0, 50, 50, Color.RED, null);
        this.keyInput = keyInput;
        this.mouseInput = mouseInput;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
        g.setColor(Color.white);
        g.drawString("Pos x: " + x, 50, 50);
        g.drawString("Pos y: " + y, 50, 70);
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

    public void reset() {
        inAir = false;
        jumping = false;
        spaceAbove = false;
        inAirTick = 0;
        x = 580;
        y = 200;
        xMove = yMove = 0;
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
        if (keyInput.up) {
            if (!inAir) {
                yMove = -jumpForce + gForce();
                inAirTick++;
                jumping = true;
            }
        }

        if (keyInput.left) {
            xMove = -runSpeed;
        } else if (keyInput.right) {
            xMove = runSpeed;
        }

        if (keyInput.space) {
            reset();
        }

        if (mouseInput.pressed) {
            Tile tile = new Tile(mouseInput.lastPoint.x, mouseInput.lastPoint.y, 40, 40, Color.CYAN);
            Entity.allEntities.add(tile);
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