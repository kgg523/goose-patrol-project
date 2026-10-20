package com.charvas.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity extends Sprite {

    public Entity() {
    }

    public Entity(TextureRegion region) {
        super(region);
    }

    public Entity(Texture texture) {
        super(texture);
    }

    public Entity(Texture texture, int srcWidth, int srcHeight) {
        super(texture, srcWidth, srcHeight);
    }
}
