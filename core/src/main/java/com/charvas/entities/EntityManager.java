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
}
