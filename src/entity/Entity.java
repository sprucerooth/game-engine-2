package entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    public static List<Entity> allEntities = new ArrayList<>();

    public int x, y;
    int width, height;
    Color color;

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


    static int returnCollisionDistanceRight(Entity entity, int dist) {
        for (Entity otherEntity : allEntities) {
            if (entity.equals(otherEntity)) continue;
            if (entity.x + entity.width + dist >= otherEntity.x && entity.x < otherEntity.x + otherEntity.width && entity.y + entity.height > otherEntity.y && !(entity.y > otherEntity.y + otherEntity.height)) {
                return otherEntity.x - entity.x - entity.width - 1;
            }
        }
        return dist;
    }

    static int returnCollisionDistanceLeft(Entity entity, int dist) {
        for (Entity otherEntity : allEntities) {
            if (entity.equals(otherEntity)) continue;
            if (entity.x + dist <= otherEntity.x + otherEntity.width && entity.x + entity.width > otherEntity.x && entity.y + entity.height > otherEntity.y && !(entity.y > otherEntity.y + otherEntity.height)) {
                return otherEntity.x + otherEntity.width - entity.x + 1;
            }
        }
        return dist;
    }

    static double returnCollisionDistanceUp(Entity entity, double dist) {
        for (Entity otherEntity : allEntities) {
            if (entity.equals(otherEntity)) continue;
            if (entity.y + dist <= otherEntity.y + otherEntity.height && entity.y + entity.height > otherEntity.y && entity.x + entity.width > otherEntity.x && !(entity.x > otherEntity.x + otherEntity.width)) {
                return otherEntity.y + otherEntity.height - entity.y + 1;
            }
        }
        return dist;
    }

    static int returnCollisionDistanceDown(Entity entity, int dist) {
        for (Entity otherEntity : allEntities) {
            if (entity.equals(otherEntity)) continue;
            if (entity.y + entity.height + dist >= otherEntity.y && entity.y < otherEntity.y + otherEntity.height && entity.x + entity.width > otherEntity.x && !(entity.x > otherEntity.x + otherEntity.width)) {

                return otherEntity.y - entity.y - entity.height - 1;
            }
        }
        return dist;
    }


}