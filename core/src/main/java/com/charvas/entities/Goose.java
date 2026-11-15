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
    float waterSpeed = 1.1f; // speed multiplier in water
    Entity target;

    public Goose(Array<AtlasRegion> movingAnimation, TextureRegion idleTex, World givenWorld) {
        super(movingAnimation, movingAnimation);
        parentWorld = givenWorld;
        float movingAnimFrameTime = 0.3f; // animation speed for moving

        this.movingAnimation = new Animation<TextureRegion>(movingAnimFrameTime, movingAnimation, PlayMode.LOOP);
        idleTexture = idleTex; // idle texture

        // setup hitboc for goose
        Rectangle newHitbox = getHitbox();
        newHitbox.setWidth(0.5f);
        newHitbox.setHeight(0.5f);
        setHitbox(newHitbox);
        updateHitbox();

        updateAnimation();
    }

    @Override
    public void handleInput() {
        // handles the movement of the goose
        time += Gdx.graphics.getDeltaTime();
        // Vector2 newVelocity = new Vector2((float) Math.sin(time * 2.f), (float)
        // Math.cos(time * 2.f));
        Vector2 newVelocity;
        found: { // create a loop container to break if SeedBag is found
            for (Entity entity : parentWorld.entManager.getEntities()) { // loop through entities to find SeedBag
                if (entity instanceof SeedBagEntity) {
                    newVelocity = new Vector2(entity.getX() - getX(), entity.getY() - getY()); // set direction to
                                                                                               // SeedBag
                    if (newVelocity.len() > 3) { // skip if not in seed bag range
                        continue;
                    }
                    break found;
                }

            }
            newVelocity = new Vector2(target.getX() - getX(), target.getY() - getY()); // set direction to player if no
                                                                                       // SeedBag
        }

        if (parentWorld.isTileWater(getX(), getY())) {
            newVelocity.scl(0.5f * waterSpeed);
        } else {
            newVelocity.scl(0.5f);
        }
        setVelocity(newVelocity);
    }

    private float time = 0.f;

    @Override
    public void update() {
        // update the goose
        super.update();
        updateAnimation();
        handleInput();

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
        // handles collisions, mainly with the player
        if (otherEntity instanceof Player) {
            Player playerEntity = (Player) otherEntity;
            playerEntity.takeDamage(damage);
            kill();
        }
    }

    public void updateAnimation() {
        // update the animation
        if (parentWorld.isTileWater(getX(), getY())) { // update animation speed in water
            stateTimeS += Gdx.graphics.getDeltaTime() * waterSpeed;
        } else {
            stateTimeS += Gdx.graphics.getDeltaTime();
        }

        TextureRegion newSpriteTex = null;
        if (this.getVelocity().len() > 0) {
            newSpriteTex = movingAnimation.getKeyFrame(stateTimeS, true);
        } else {
            newSpriteTex = idleTexture;
        }

        this.setRegion(newSpriteTex);
    }

}
