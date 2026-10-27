package com.charvas.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimatedEntity extends Entity {
    /* AnimatedEntity class for representing animated game objects */

    public Animation<TextureRegion> animation;

    public AnimatedEntity() {
        super();
    }

    public AnimatedEntity(Array<AtlasRegion> region) {
        /*
         * Sets the animation for the entity and initialize the entity with the first
         * frame
         */
        animation = new Animation<TextureRegion>(0.1f, region, Animation.PlayMode.LOOP);
        //super(region.get(0)); // Initialize the entity with the first frame of the animation
    }

    public AnimatedEntity(Texture texture) {
        super(texture);
    }

    public AnimatedEntity(Texture texture, int srcWidth, int srcHeight) {
        super(texture, srcWidth, srcHeight);
    }

    public void updateAnimation(float stateTime) {
        /* Logic to update the animation frames based on time */

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        this.setRegion(currentFrame); // Update the goose sprite's texture region to the current frame
    }

}
