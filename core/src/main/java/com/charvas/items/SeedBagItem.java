package com.charvas.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.charvas.entities.World;

public class SeedBagItem extends Item {
    private World world;

    public SeedBagItem(World world) {
        super();
        name = "SeedBag";
        this.world = world;
        imageDrawable = new Image(new Texture("Items/seedBagImg.png")).getDrawable();

    }

    @Override
    public boolean use() {
        world.spawnSeedBag(new Vector2(0, 0));
        return true;
    }
}
