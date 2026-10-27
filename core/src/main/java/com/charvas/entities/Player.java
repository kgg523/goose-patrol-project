package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.charvas.main.Main;

public class Player extends Entity {
    /* Player class representing the player character in the game */

    private Animation<TextureRegion> movingAnimation;
    private Animation<TextureRegion> idleAnimation;
    private TextureRegion currentFrame;
    private boolean isMoving = false; // Flag to check if the player is moving
    private float speed = 2.5f; // Player movement speed

    public Player() {
        super();
    }

    public Player(Array<AtlasRegion> movingRegion, Array<AtlasRegion> idleRegion) {
        // Sets the animation and initializes the entity with the first frame
        // This should be the main contructor used to create a Player object
        movingAnimation = new Animation<TextureRegion>(0.1f, movingRegion, Animation.PlayMode.LOOP);
        idleAnimation = new Animation<TextureRegion>(0.1f, idleRegion, Animation.PlayMode.LOOP);
    }

    // looks at inputs and SETS state of player. position actually gets updated in Player.Update
    @Override   
    public void handleInput()
    {
        /* Handle player input for movement and actions */

        float deltaTime = Gdx.graphics.getDeltaTime(); // Keep speed consistant across different frame rates

        Vector2 moveDirection = new Vector2(0.f, 0.f);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveDirection.x += -1.f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveDirection.x += 1.f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveDirection.y += 1.f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDirection.y += -1.f;
        }

        moveDirection.nor();
        moveDirection.scl(this.speed * deltaTime);

        this.setVelocity(moveDirection);

        // Flip the player sprite based on movement direction, and stay looking that way
        if (moveDirection.x < 0) {
            this.setFlip(true, false); // Flip the goose sprite horizontally when moving left
        } else {
            this.setFlip(false, false); // Reset the flip when moving right
        }

        // Check if the player is moving based on input
        isMoving = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)
                || Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)
                || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)
                || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
    }

    @Override 
    public void update()
    {
        super.update();
        handleBorders();
        updateAnimation();
    }

    @Override
    public void onCollision(Entity otherEntity)
    {

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

    public void handleBorders() {
        // Keep player within the bounds of the world
        float worldWidth = Main.V_WIDTH;
        float worldHeight = Main.V_HEIGHT;
        float offsetX = this.getWidth() * 0.25f; // Offset to allow the player image to be correct

        this.setX(MathUtils.clamp(this.getX(), -offsetX, worldWidth - this.getWidth() + offsetX));
        this.setY(MathUtils.clamp(this.getY(), 0, worldHeight - this.getHeight()));
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void checkCollision(Entity otherEntity) {
        // Check for collision with another entity
        if (this.getBoundingRectangle().overlaps(otherEntity.getBoundingRectangle())) {
            // Handle collision logic here
            System.out.println("Collision detected with " + otherEntity);
        }
    }
}
