package com.charvas.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public void checkResolveCollisions() {
        for (Entity ent1: entityList) {
            for (Entity ent2 : entityList) {
                if(ent1 == ent2 || !checkCollision(ent1, ent2))    
                    continue;

                ent1.onCollision(ent2);
                ent2.onCollision(ent1);
            }
        }
    }

    public static boolean checkCollision(Entity ent1, Entity ent2) {
        return ent1.getHitbox().overlaps(ent2.getHitbox());
    }
}
