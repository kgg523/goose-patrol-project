package com.charvas.entities;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
    private ArrayList<Entity> entityList = new ArrayList<Entity>();

    public EntityManager() {}

    public final void addEntity(Entity newEntity)
    {
        entityList.add(newEntity);
    }

    public final void removeEntity(Entity ent) {
        entityList.remove(ent);
    }

    public final ArrayList<Entity> getEntities() {
        return entityList;
    }

    public final void update() {
        for (Entity entity : entityList)
            entity.update();

        checkResolveCollisions();

        removeDeadEntities();
    }

    public final void removeDeadEntities() {
        ArrayList<Entity> deadEntites = new ArrayList<Entity>();

        for (Entity ent : entityList)
            if(!ent.isAlive())
                deadEntites.add(ent);
        
        entityList.removeAll(deadEntites);
    }

    public final void draw(SpriteBatch pBatch) {
        for (Entity entity : entityList)
            entity.draw(pBatch);
    }

    public final void drawHitboxes(ShapeRenderer shapeRenderer) {
        for (Entity entity : entityList)
            entity.drawHitbox(shapeRenderer);
    }

    public void checkResolveCollisions() {
        resolveCollisions(getCollisions());
    }

    public ArrayList<SimpleEntry<Entity, Entity>> getCollisions() {
        ArrayList<SimpleEntry<Entity, Entity>> collisionPairs = new ArrayList<SimpleEntry<Entity, Entity>>();

        for (Entity ent1: entityList) {
            for (Entity ent2 : entityList) {
                if(ent1 == ent2 || !checkCollision(ent1, ent2))    
                    continue;

                SimpleEntry<Entity, Entity> collisionPair = new SimpleEntry<Entity, Entity>(ent1, ent2);
                if( !collisionPairs.contains(collisionPair) && 
                    !collisionPairs.contains(new SimpleEntry<Entity, Entity>(ent2, ent1))) {
                    collisionPairs.add(collisionPair);
                }
            }
        }
        
        return collisionPairs;
    }

    public static void resolveCollisions(ArrayList<SimpleEntry<Entity, Entity>> collisionPairs) {
        if(collisionPairs == null || collisionPairs.isEmpty())
            return;

        for (SimpleEntry<Entity, Entity> collisionPair : collisionPairs) {
            Entity ent1 = collisionPair.getKey();
            Entity ent2 = collisionPair.getValue();

            ent1.onCollision(ent2);
            ent2.onCollision(ent1);
        }
    }

    public static boolean checkCollision(Entity ent1, Entity ent2) {
        return ent1.getHitbox().overlaps(ent2.getHitbox());
    }
}
