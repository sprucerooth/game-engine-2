package entity;

import java.awt.*;
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
        int possibleDistance = desiredDist;
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (e.x2WouldCollideWith(e2, desiredDist)) {
                possibleDistance = Math.min(e.rightDistanceTo(e2) - 1, possibleDistance);
            }
        }
        return possibleDistance;
    }

    static int possibleTravelDistanceLeft(Entity e, int desiredDist) {
        int possibleDistance = desiredDist;
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (e.xWouldCollideWith(e2, desiredDist)) {
                possibleDistance = Math.max(e.leftDistanceTo(e2) + 1, possibleDistance);
            }
        }
        return possibleDistance;
    }

    static double possibleTravelDistanceUp(Entity e, double desiredDist) {
        double possibleDistance = desiredDist;
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (e.yWouldCollideWith(e2, desiredDist)) {
                possibleDistance = Math.max(e.upDistanceTo(e2) + 1, possibleDistance);
            }
        }
        return possibleDistance;
    }

    static int possibleTravelDistanceDown(Entity e, final int desiredDist) {
        int possibleDistance = desiredDist;
        for (Entity e2 : allEntities) {
            if (e.equals(e2)) continue;
            if (e.y2WouldCollideWith(e2, desiredDist)) {
                possibleDistance = Math.min(e.downDistanceTo(e2) - 1, possibleDistance);
            }
        }
        return possibleDistance;
    }

    private boolean y2WouldCollideWith(Entity e2, int distance) {
        return y2() + distance >= e2.y && isDirectlyAbove(e2);
    }

    private boolean yWouldCollideWith(Entity e2, double distance) {
        return y + distance <= e2.y2() && isDirectlyUnder(e2);
    }

    private boolean x2WouldCollideWith(Entity e2, double distance) {
        return x2() + distance >= e2.x && isDirectlyLeftOf(e2);
    }

    private boolean xWouldCollideWith(Entity e2, double distance) {
        return x + distance <= e2.x2() && isDirectlyRightOf(e2);
    }

    private int x2() {
        return this.x + this.width;
    }

    private int y2() {
        return this.y + this.height;
    }

    private int rightDistanceTo(Entity e2) {
        return e2.x - x2();
    }

    private int upDistanceTo(Entity e2) {
        return e2.y2() - this.y;
    }

    private int downDistanceTo(Entity e2) {
        return e2.y - y2();
    }

    private int leftDistanceTo(Entity e2) {
        return e2.x2() - this.x;
    }

    private boolean isUnder(Entity e2) {
        return this.y > e2.y2();
    }

    private boolean isAbove(Entity e2) {
        return this.y2() < e2.y;
    }

    private boolean isDirectlyAbove(Entity e2) {
        return !isUnder(e2) &&
                !isLeftOf(e2) &&
                !isRightOf(e2);
    }

    private boolean isDirectlyUnder(Entity e2) {
        return !isLeftOf(e2) &&
                !isAbove(e2) &&
                !isRightOf(e2);
    }


    private boolean isDirectlyLeftOf(Entity e2) {
        return isLeftOf(e2) &&
                !isAbove(e2) &&
                !isUnder(e2);
    }

    private boolean isDirectlyRightOf(Entity e2) {
        return isRightOf(e2) &&
                !isAbove(e2) &&
                !isUnder(e2);
    }

    private boolean isRightOf(Entity e2) {
        return this.x > e2.x2();
    }

    private boolean isLeftOf(Entity e2) {
        return x2() < e2.x;
    }


}

/*
        return allEntities.stream()
                .filter(e2 -> !e2.equals(e) && e.wouldCollideWith(e2, desiredDist))
                .mapToInt(e2 -> e.downDistanceTo(e2) - 1).filter(i -> i < desiredDist)
        .min().orElse(desiredDist);*/
