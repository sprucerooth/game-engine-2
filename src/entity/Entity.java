package entity;

import graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    public static List<Entity> allEntities = new ArrayList<>();

    public int x, y;
    int width, height;
    Color color;
    double xMove, yMove;

    Entity(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        allEntities.add(this);
    }

    public abstract void update();

    public abstract void draw(Graphics g);

    static int possibleTravelDistanceRight(Entity e, int desiredDist) {
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (rightSideXPos(e) + desiredDist >= e2.x &&
                    !e.isRightOf(e2) &&
                    !(downSideYPos(e) + e.yMove < e2.y) &&
                    !(e.y + e.yMove > downSideYPos(e2))) {
                return e.rightDistanceTo(e2) - 1;
            }
        }
        return desiredDist;
    }

    static int possibleTravelDistanceLeft(Entity e, int desiredDist) {
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (e.x + desiredDist <= rightSideXPos(e2) &&
                    !e.isLeftOf(e2) &&
                    !(downSideYPos(e) + e.yMove < e2.y) &&
                    !(e.y + e.yMove > downSideYPos(e2))) {
                return e.leftDistanceTo(e2) + 1;
            }
        }
        return desiredDist;
    }

    static double possibleTravelDistanceUp(Entity e, double desiredDist) {
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (e.y + desiredDist <= downSideYPos(e2) &&
                    !e.isLeftOf(e2) &&
                    !e.isAbove(e2) &&
                    !e.isRightOf(e2)) {
                return e.upDistanceTo(e2) + 1;
            }
        }
        return desiredDist;
    }

    static int possibleTravelDistanceDown(Entity e, int desiredDist) {
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (downSideYPos(e) + desiredDist >= e2.y &&
                    !e.isUnderneath(e2) &&
                    !e.isLeftOf(e2) &&
                    !e.isRightOf(e2)) {
                return e.downDistanceTo(e2) - 1;
            }
        }
        return desiredDist;
    }

    private static int rightSideXPos(Entity e) {
        return e.x + e.width;
    }

    private static int downSideYPos(Entity e) {
        return e.y + e.height;
    }

    private int rightDistanceTo(Entity e2) {
        return e2.x - rightSideXPos(this);
    }

    private int upDistanceTo(Entity e2) {
        return downSideYPos(e2) - this.y;
    }

    private int downDistanceTo(Entity e2) {
        return e2.y - downSideYPos(this);
    }

    private int leftDistanceTo(Entity e2) {
        return rightSideXPos(e2) - this.x;
    }

    private boolean isUnderneath(Entity e2) {
        return this.y > downSideYPos(e2);
    }

    private boolean isAbove(Entity e2) {
        return downSideYPos(this) < e2.y;
    }

    private boolean isRightOf(Entity e2) {
        return this.x > rightSideXPos(e2);
    }

    private boolean isLeftOf(Entity e2) {
        return rightSideXPos(this) < e2.x;
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