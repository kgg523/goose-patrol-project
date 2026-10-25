package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.charvas.main.Main;

public class Player extends AnimatedEntity {
    /* Player class representing the player character in the game */

    private Animation<TextureRegion> animation;

    public Player() {
        super();
    }

    public Player(Array<AtlasRegion> region) {

        animation = new Animation<TextureRegion>(0.1f, region, Animation.PlayMode.LOOP);
        super(region); // Initialize the entity with the first frame of the animation
    }

    public void input() {
        /* Handle player input for movement and actions */
        float speed = 3f;
        float deltaTime = Gdx.graphics.getDeltaTime(); // Keep speed consistant across different frame rates
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.translateX(-speed * deltaTime);
            this.setFlip(true, false); // Flip the sprite horizontally when moving left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.translateX(speed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.translateY(speed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.translateY(-speed * deltaTime);
        }
    }

    public void updateAnimation(float stateTime) {
        /* Logic to update the animation frames based on time */

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        this.setRegion(currentFrame); // Update the goose sprite's texture region to the current frame
    }

    public void handleBorders() {
        float worldWidth = Main.V_WIDTH;
        float worldHeight = Main.V_HEIGHT;

        this.setX(MathUtils.clamp(this.getX(), 0, worldWidth - this.getWidth()));
        this.setY(MathUtils.clamp(this.getY(), 0, worldHeight - this.getHeight()));
    }
}
