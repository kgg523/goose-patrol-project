package com.charvas.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Item {
    private GameScreen game;
    private String name;
    private Drawable imageDrawable;

    public Item(GameScreen game) {
        this.game = game;
        name = "None";
        Pixmap pixmap= new Pixmap(1, 1, Pixmap.Format.RGB888);
        Skin skin = new Skin();
        pixmap.setColor(Color.PURPLE);
        pixmap.fill(); 
        skin.add("empty", new Texture(pixmap));
        imageDrawable = skin.newDrawable("empty");
        pixmap.dispose();
    }

    public void use() {
        System.out.println("Item used.");
    }
    public String getName() {
        return name;
    }
    public Drawable getDrawable() {
        return imageDrawable;
    }

}
