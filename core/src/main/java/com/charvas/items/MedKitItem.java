package com.charvas.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.charvas.entities.Player;
import com.charvas.main.GameScreen;

public class MedKitItem extends Item {
    /* Item which heals the given player by a set 100 hp */
    private Player player;

    public MedKitItem( Player targetPlayer){
        super();
        player = targetPlayer;
        name = "Medkit";
        imageDrawable = new Image(new Texture("Items/medkitImg.png")).getDrawable();
    }

    @Override
    public boolean use() {
        if (player.heal(100)) {
            return true;
        }
        return false;
    }
    
}
