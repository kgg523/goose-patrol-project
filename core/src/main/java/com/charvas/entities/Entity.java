package com.charvas.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Entity extends Sprite {
    /* Entity class for representing game objects */

    public float speed;
    public Rectangle customHitbox;

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

    public Rectangle getHitbox() {
        return customHitbox;
    }
}
