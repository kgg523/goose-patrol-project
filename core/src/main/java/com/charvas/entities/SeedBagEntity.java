package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class SeedBagEntity extends Entity {
    // time this seedbag should be alive in seconds
    float timeAliveS = 0.f;

    public SeedBagEntity(World pWorld, float timeAliveS) {
        super(pWorld);
        
        setTexture(new Texture("Items/seedBagImg.png"));
        setRegion(0, 0, getTexture().getWidth(), getTexture().getHeight());

        setSize(0.5f, 0.5f);

        Rectangle newHitbox = new Rectangle();
        newHitbox.x = 0.f;
        newHitbox.y = 0.f;
        newHitbox.width = 0.5f;
        newHitbox.height = 0.5f;

        setHitbox(newHitbox);

        this.timeAliveS = timeAliveS;
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