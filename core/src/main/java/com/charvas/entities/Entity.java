package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.charvas.main.Main;

public abstract class Entity extends Sprite {
    /* Entity class for representing game objects */

    public Rectangle customHitbox; // Hitbox for collision

    private Vector2 velocity = new Vector2(); // vector to normalise speed
    private Vector2 acceleration = new Vector2();

    private float maxSpeed = 2.5f;

    private boolean alive = true;


    /****************/
    /* CONSTRUCTORS */
    /****************/

    public Entity() {
    }

    public Entity(TextureRegion region) {
        super(region);
        customHitbox = new Rectangle(-100, -100, 1, 1);
    }

    public Entity(Texture texture) {
        super(texture);
        customHitbox = new Rectangle(-100, -100, 1, 1);
    }

    public Entity(Texture texture, int srcWidth, int srcHeight) {
        super(texture, srcWidth, srcHeight);
        customHitbox = new Rectangle(-100, -100, srcWidth, srcHeight);
    }

    /***********/
    /* HELPERS */
    /***********/
    // sets the players position 
    public final void handleBorders() {
        // Keep player within the bounds of the world
        float worldWidth = Main.V_WIDTH;
        float worldHeight = Main.V_HEIGHT;
        float offsetX = this.getWidth() * 0.25f; // Offset to allow the player image to be correct

        this.setX(MathUtils.clamp(getX(), -offsetX, worldWidth - getWidth() + offsetX));
        this.setY(MathUtils.clamp(getY(), 0, worldHeight - getHeight()));

        // update hitbox to player location
        customHitbox.set(getX() + offsetX, getY(), 0.5f, 1.0f);
    }

    /************/
    /* OVERIDES */
    /************/
    public void handleInput() {}
    
    public void update() {
        // update entity values each frame
        float deltaTimeS = Gdx.graphics.getDeltaTime();

        Vector2 scaledAcc = new Vector2(this.acceleration);
        Vector2 scaledVel = new Vector2(this.velocity);
        
        scaledAcc.scl(deltaTimeS);
        scaledVel.add(scaledAcc);
        if (scaledVel.len() > this.maxSpeed * deltaTimeS) {
            scaledVel.setLength(this.maxSpeed * deltaTimeS);
        }

        this.translate(scaledVel.x, scaledVel.y);
    }

    public void onCollision(Entity otherEntity) {}


    /***********/
    /* GETTERS */
    /***********/
    public final Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public final Vector2 getVelocity() {
        return new Vector2(this.velocity);
    }

    public final Vector2 getAcceleration() {
        return new Vector2(this.acceleration);
    }

    public final float getMaxSpeed() {
        return this.maxSpeed;
    }

    public final Rectangle getHitbox() {
        return new Rectangle(customHitbox);
    }

    public final boolean isAlive() {
        return alive;
    }

    /***********/
    /* SETTERS */
    /***********/

    public final void setPosition(Vector2 newPosition) {
        this.setX(newPosition.x);
        this.setY(newPosition.y);
    }

    public final void setVelocity(Vector2 newVelocity) {
        this.velocity.set(newVelocity);
    }

    public final void setAcceleration(Vector2 newAcceleration) {
        this.acceleration.set(newAcceleration);
    }

    public final void setMaxSpeed(float newMaxSpeed) {
        this.maxSpeed = newMaxSpeed;
    }

    public final void setHitbox(Rectangle newHitbox) {
        customHitbox = newHitbox;
    }

    // flags to EntityManager to remove this instance from entity list
    public final void kill() {
        this.alive = false;
    }
}
