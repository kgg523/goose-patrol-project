package com.charvas.entities;

import com.charvas.entities.Entity;;
import java.util.ArrayList;

public class EntityManager {
    private ArrayList<Entity> entityList = new ArrayList<>();

    public EntityManager() {}

    public final void addEntity(Entity newEntity)
    {
        entityList.add(newEntity);
    }

    public final void removeEntity(Entity )

    public final ArrayList<Entity> getEntities()
    {
        return entityList;
    }



    public final void update()
    {
        for (Entity entity : entityList) {
            entity.update();
        }
    }

    public static boolean checkCollision(Entity ent1, Entity ent2)
    {
        
    }
}
