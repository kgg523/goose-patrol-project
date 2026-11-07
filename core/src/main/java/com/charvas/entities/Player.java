package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {
    /* Player class representing the player character in the game */

    private Animation<TextureRegion> movingAnimation;
    private Animation<TextureRegion> idleAnimation;
    private TextureRegion currentFrame;
    private boolean isMoving = false; // Flag to check if the player is moving
    private boolean left = false; // Flag to flip player if looking left
    
    private int iFrameCooldown = 10; // number of invincibility frames after getting hit
    private int iFrameCount = 0; // current count of invincibility frames

    private float speed = 2.5f; // Player movement speed
    
    private int maxHealth = 100;
    private int health = maxHealth;

    public Player(Array<AtlasRegion> movingRegion, Array<AtlasRegion> idleRegion) {
        // Sets the animation and initializes the entity with the first frame
        // This should be the main contructor used to create a Player object
        super(movingRegion.get(0));

        float movingSecPerFrame = 0.2f; // 5fps
        float idleSecPerFrame = 0.5f; // 2fps
        
        movingAnimation = new Animation<TextureRegion>(movingSecPerFrame, movingRegion, Animation.PlayMode.LOOP);
        idleAnimation = new Animation<TextureRegion>(idleSecPerFrame, idleRegion, Animation.PlayMode.LOOP);
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
        
        // normalise speed vector
        moveDirection.nor();
        moveDirection.scl(this.speed);

        this.setVelocity(moveDirection);

        if(this.getVelocity().x < 0.f)
            this.setFlip(true, false); // Flip the goose sprite horizontally when moving left

        // Check if the player is moving based on input
        isMoving = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)
                || Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)
                || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)
                || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
    }

    @Override
    public void update() {
        super.update();
        handleBorders();
        updateAnimation();
        if(iFrameCount > 0)
            iFrameCount--;
    }

    @Override
    public void onCollision(Entity otherEntity) {
        if(iFrameCount > 0)
            return;

        iFrameCount = iFrameCooldown;

    }

    static float stateTimeS = 0.f;

    public void updateAnimation() {
        /* Logic to update the animation frames based on time */
        stateTimeS += Gdx.graphics.getDeltaTime();
        if (isMoving) {
            currentFrame = movingAnimation.getKeyFrame(stateTimeS, true);
            this.setRegion(currentFrame); // Update the player sprite's texture region to the current frame
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTimeS, false);
            this.setRegion(currentFrame); // Update the player sprite's texture region to the current frame
        }
    }

    public void setHealth(int newHealth) {
        health = MathUtils.clamp(newHealth, 0, maxHealth);
    }

    public void setMaxHealth(int newMaxHealth) {
        maxHealth = newMaxHealth;
    }

    public boolean heal(int hp) {
        if (health == maxHealth){
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

    public void setSpeed(float newSpeed) {
        speed = newSpeed;
    }
}
