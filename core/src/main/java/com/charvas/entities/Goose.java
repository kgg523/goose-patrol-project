package com.charvas.entities;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Goose extends Entity {

    Animation<TextureRegion> movingAnimation;
    TextureRegion idleTexture;

    float stateTimeS = 0.f;

    // Damage that the goose does to the player on collision
    int damage = 10;

    public Goose(Array<AtlasRegion> movingAnimation, TextureRegion idleTex) {
        super(movingAnimation, movingAnimation);
        float movingAnimFrameTime = 0.3f;

        this.movingAnimation = new Animation<TextureRegion>(movingAnimFrameTime, movingAnimation, PlayMode.LOOP);
        idleTexture = idleTex;

        Rectangle newHitbox = getHitbox();
        newHitbox.setWidth(0.5f);
        newHitbox.setHeight(0.5f);
        setHitbox(newHitbox);
        updateHitbox();

        updateAnimation();
    }

    @Override
    public void handleInput() {
        time += Gdx.graphics.getDeltaTime();
        Vector2 newVelocity = new Vector2((float) Math.sin(time * 2.f), (float) Math.cos(time * 2.f));
    
        newVelocity.scl(0.5f);
        setVelocity(newVelocity);
    }

    private float time = 0.f;

    @Override
    public void update() {

        super.update();
        updateAnimation();

        if (getVelocity().x < 0.f)
            setFlip(true, false);
        else if (getVelocity().x > 0.f)
            setFlip(false, false);
    }

    @Override
    public final void draw(Batch pBatch) {
        super.draw(pBatch);
    }

    @Override
    public void onCollision(Entity otherEntity) {
        if (otherEntity instanceof Player) {
            Player playerEntity = (Player) otherEntity;
            playerEntity.takeDamage(damage);
            kill();
        }
    }

    public void updateAnimation() {
        stateTimeS += Gdx.graphics.getDeltaTime();

        TextureRegion newSpriteTex = null;
        if (this.getVelocity().len() > 0) {
            newSpriteTex = movingAnimation.getKeyFrame(stateTimeS, true);
        } else {
            newSpriteTex = idleTexture;
        }

        this.setRegion(newSpriteTex);
    }

}
