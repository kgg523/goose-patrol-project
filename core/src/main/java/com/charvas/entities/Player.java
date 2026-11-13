package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.charvas.items.Inventory;

public class Player extends Entity {
    /* Player class representing the player character in the game */

    private Animation<TextureRegion> movingAnimation;
    private Animation<TextureRegion> idleAnimation;

    private Inventory inventory;

    private boolean isMoving = false; // Flag to check if the player is moving
    private boolean lookingLeft = false; // Flag to flip player if looking left

    private int iFrameCooldown = 10; // number of invincibility frames after getting hit
    private int iFrameCount = 0; // current count of invincibility frames

    private int maxHealth = 100;
    private int health = maxHealth;
    private float movingSecPerFrame = 0.2f; // 5fps
    private float idleSecPerFrame = 0.5f; // 2fps

    public Player(Array<AtlasRegion> movingRegion, Array<AtlasRegion> idleRegion, World givenWorld) {
        // Sets the animation and initializes the entity with the first frame
        // This should be the main contructor used to create a Player object
        super(movingRegion, idleRegion);
        parentWorld = givenWorld;

        movingAnimation = new Animation<TextureRegion>(movingSecPerFrame, movingRegion, Animation.PlayMode.LOOP);
        idleAnimation = new Animation<TextureRegion>(idleSecPerFrame, idleRegion, Animation.PlayMode.LOOP);

        inventory = new Inventory();
        updateAnimation();

        Rectangle customHitbox = getHitbox();

        customHitbox.setWidth(0.3f);
        customHitbox.setHeight(1.f);

        setHitbox(customHitbox);
        setHitboxOffset(0.35f, 0.f);
    }

    // looks at inputs and SETS state of player. position actually gets updated in
    // Player.Update
    @Override
    public void handleInput() {
        /* Handle player input for movement and actions */
    
        Vector2 moveDirection = new Vector2(0.f, 0.f);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            moveDirection.x -= 1.f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            moveDirection.x += 1.f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            moveDirection.y += 1.f;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            moveDirection.y -= 1.f;

        float currSpeed = getMaxSpeed() * parentWorld.getTileSpeed(getX(), getY());

        moveDirection.setLength(currSpeed);

        this.setVelocity(moveDirection);

        if (this.getVelocity().x < 0.f)
            lookingLeft = true;
        else if (this.getVelocity().x > 0.f)
            lookingLeft = false;

        // Check if the player is moving based on input
        isMoving = getVelocity().len2() > 0.f;
    }

    @Override
    public void update() {
        super.update();
        handleBorders();
        updateAnimation();

        if (lookingLeft) {
            this.setFlip(true, false);
        } else {
            this.setFlip(false, false);
        }

        if (iFrameCount > 0)
            iFrameCount--;
    }

    @Override
    public void onCollision(Entity otherEntity) {
        if (iFrameCount > 0)
            return;

        iFrameCount = iFrameCooldown;
    }

    static float stateTimeS = 0.f;

    public void updateAnimation() {
        /* Logic to update the animation frames based on time */

        stateTimeS += Gdx.graphics.getDeltaTime() * parentWorld.getTileSpeed(getX(), getY());
        TextureRegion newSpriteTex = null;

        if (isMoving)
            newSpriteTex = movingAnimation.getKeyFrame(stateTimeS, true);
        else
            newSpriteTex = idleAnimation.getKeyFrame(stateTimeS, false);

        this.setRegion(newSpriteTex); // Update the player sprite's texture region to the current frame
    }

    public void setHealth(int newHealth) {
        health = MathUtils.clamp(newHealth, 0, maxHealth);
    }

    public void setMaxHealth(int newMaxHealth) {
        maxHealth = newMaxHealth;
    }

    public boolean heal(int hp) {
        if (health == maxHealth) {
            return false;
        }
        health = MathUtils.clamp(health + hp, 0, maxHealth);
        return true;
    }

    public void takeDamage(int damagePts) {
        health = MathUtils.clamp(health - damagePts, 0, maxHealth);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
