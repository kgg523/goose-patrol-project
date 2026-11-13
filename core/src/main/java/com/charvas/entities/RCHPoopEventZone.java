package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class RCHPoopEventZone extends Entity{

    float cooldown = 5f;
    boolean canActivate = true;

    public RCHPoopEventZone(World world) {
        super(new Texture("Items/seedBagImg.png"));
        parentWorld = world;

        Rectangle newHitbox = getHitbox();
        newHitbox.setWidth(6f);
        newHitbox.setHeight(7f);
        setHitbox(newHitbox);
        updateHitbox();


    }

    @Override
    public void onCollision(Entity otherEntity) {
        if (otherEntity instanceof Player) {
            Player player = (Player) otherEntity;
            float chanceOfActivation = 0.005f;
            if  (canActivate && Math.random() < chanceOfActivation){
                parentWorld.incrementEventCounter();
                System.out.println("Event triggered");
                parentWorld.spawnPoop(parentWorld.getPlayer().getPosition());
                player.takeDamage(10);
                canActivate = false;
                cooldown = 5f;
            }
        }
    }

    @Override
    public void draw(Batch pBatch) {
        
    }

    @Override
    public void update() {
        if (!canActivate) {
            cooldown -= Gdx.graphics.getDeltaTime();
            if (cooldown < 0.f){
                canActivate = true;
            }
        }
    }
}
