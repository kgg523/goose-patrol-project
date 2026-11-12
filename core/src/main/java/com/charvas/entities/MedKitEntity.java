package com.charvas.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.charvas.items.MedKitItem;

public class MedKitEntity extends Entity {

    public MedKitEntity(Texture texture) {
        super(texture);

        Rectangle newHitbox = getHitbox();
        newHitbox.setWidth(0.5f);
        newHitbox.setHeight(0.5f);
        setHitbox(newHitbox);
        updateHitbox();

    }

    @Override
    public void onCollision(Entity otherEntity) {
        if (otherEntity instanceof Player) {
            Player playerEntity = (Player) otherEntity;
            playerEntity.getInventory().addItem(new MedKitItem(playerEntity));
            kill();
        }
    }
}
