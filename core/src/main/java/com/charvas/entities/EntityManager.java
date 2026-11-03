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

        resolveCollisions();

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

    /**
     * returns a list of pairs of entities that are colliding
     * @return an array of SimpleEntry (used to represent a pair) of entities that are currently colliding
     */
    private ArrayList<SimpleEntry<Entity, Entity>> getCollisions() {
        ArrayList<SimpleEntry<Entity, Entity>> collisionPairs = new ArrayList<SimpleEntry<Entity, Entity>>();

        for (Entity ent1: entityList) {
            for (Entity ent2 : entityList) {
                if(ent1 == ent2 || !checkCollision(ent1, ent2))    
                    continue;

                SimpleEntry<Entity, Entity> collisionPair = new SimpleEntry<Entity, Entity>(ent1, ent2);
                collisionPairs.add(collisionPair);
            }
        }
        
        return collisionPairs;
    }

    public void resolveCollisions() {
        ArrayList<SimpleEntry<Entity, Entity>> collisionPairs = getCollisions();
        if(collisionPairs.isEmpty())
            return;

        for (SimpleEntry<Entity, Entity> collisionPair : collisionPairs) {
            Entity ent1 = collisionPair.getKey();
            Entity ent2 = collisionPair.getValue();

            // System.out.println("Collision detected between " + ent1.getClass().getSimpleName() + " and " + ent2.getClass().getSimpleName());

            // call the on collision method for just ONE entity onto the other
            // this choice is made because there will be another pair in the list
            // where ent2 is the key and ent1 is the value (given that getCollisions() function was the argument passed)
            // this simplifies the getCollisions function since it wont have to check if the 
            // pair is already in the list
            ent1.onCollision(ent2);
        }
    }

    public static boolean checkCollision(Entity ent1, Entity ent2) {
        return ent1.getHitbox().overlaps(ent2.getHitbox());
    }
}
