package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class RCHPoopEntity extends Entity {
    // time this effect should be alive in seconds
    float timeAliveS = 0.f;


    public RCHPoopEntity(Texture texture, World world) {
        super(texture);
        parentWorld = world;
        setSize(1.5f, 1.5f);
        timeAliveS = (float) Math.random()*2;
    }

    @Override
    public void update() {
        if (timeAliveS <= 0.f) {
            kill();
            return;
        }

        timeAliveS -= Gdx.graphics.getDeltaTime();
    }



}
