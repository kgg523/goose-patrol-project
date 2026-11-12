package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.charvas.main.Main;

public abstract class Entity extends Sprite {
    /* Entity class for representing game objects */

    World parentWorld;

    private Rectangle customHitbox = new Rectangle(); // Hitbox for collision
    private float hitboxOffsetX = 0.f; // Offset for hitbox position
    private float hitboxOffsetY = 0.f; // Offset for hitbox position

    private Vector2 velocity = new Vector2(); // vector to normalise speed
    private Vector2 acceleration = new Vector2();

    private float maxSpeed = 0.f;

    private boolean alive = true;

    public Entity(Texture texture) {
        super(texture);
        customHitbox = new Rectangle(-100, -100, 1, 1);
    }

    public Entity(Array<AtlasRegion> movingRegion, Array<AtlasRegion> idleRegion) {
        super(idleRegion.get(0));
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
    }

    /************/
    /* OVERIDES */
    /************/
    public void handleInput() {
    }

    public void update() {
        // update entity values each frame
        float deltaTimeS = Gdx.graphics.getDeltaTime();

        getVelocity().add(getAcceleration());
        if (getVelocity().len() > getMaxSpeed()) {
            setVelocity(getVelocity().setLength(getMaxSpeed()));
        }

        Vector2 scaledVel = getVelocity().scl(deltaTimeS);

        // entity not moving at all, skip translation and hitbox update
        if (scaledVel.len2() == 0.f) {
            return;
        }

        this.translate(scaledVel.x, scaledVel.y);
        updateHitbox();
    }

    @Override
    public void draw(Batch pBatch) {
        super.draw(pBatch);
    }

    public void onCollision(Entity otherEntity) {
    }

    public void drawHitbox(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(customHitbox.x, customHitbox.y, customHitbox.width, customHitbox.height);
    }

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
        setX(newPosition.x);
        setY(newPosition.y);
    }

    public final void updateHitbox() {
        customHitbox.setX(getX() + hitboxOffsetX);
        customHitbox.setY(getY() + hitboxOffsetY);
    }

    public final void setHitboxOffset(float offsetX, float offsetY) {
        this.hitboxOffsetX = offsetX;
        this.hitboxOffsetY = offsetY;
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
