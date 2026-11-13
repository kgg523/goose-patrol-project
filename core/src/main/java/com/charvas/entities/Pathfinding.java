package com.charvas.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import com.charvas.entities.Entity;

public class Pathfinding {

    private ArrayList<Vector2> nodes = new ArrayList<Vector2>();

    private Entity targetEntity;

    /**
     * @return vector of (0, 0) if there is no path or entity set. make sure to call update before
     */
    public Vector2 getDirection() {
        if(nodes.size() == 0)
            return new Vector2(0.f, 0.f);
        
        Vector2 dir = new Vector2();

        nodes.get(0);
        
        return dir;
    }

    /**
     * @param targetEnt pass null for
     */
    public void setTargetEntity(Entity targetEnt) {
        if(targetEnt == null)
            nodes.clear();

        targetEntity = targetEnt;
    }

    /**
     * updates the internal path
     */
    public void update() {
        aStar(targetEntity.getWorld());
    }
    
    public void aStar(World world) {
        if(targetEntity == null)
            return;

        
    }

}
