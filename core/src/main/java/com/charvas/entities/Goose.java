package com.charvas.entities;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Goose extends Entity {
    
    Animation<TextureRegion> movingAnimation;
    TextureRegion idleTexture;
    
    public Goose(Array<AtlasRegion> movingAnimation, TextureRegion idleTex)
    {
        float movingAnimationFrameDuration = 0.3f;

        this.movingAnimation = new Animation<TextureRegion>(movingAnimationFrameDuration, movingAnimation, PlayMode.LOOP);
        idleTexture = new TextureRegion(idleTex);
    }

    @Override 
    public void handleInput() {}
    
    @Override 
    public void update() {
        super.update();
    }

    @Override 
    public void onCollision(Entity otherEntity) {
        if(otherEntity instanceof Player)
        {
            Player subEntity = (Player)otherEntity;

        }
    }

}
