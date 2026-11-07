package com.charvas.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.charvas.entities.Player;

public class MedKitItem extends Item {
    /* Item which heals the given player by a set 50 hp */
    private Player player;

    public MedKitItem(GameScreen game, Player targetPlayer){
        super(game);
        player = targetPlayer;
        name = "Medkit";
        imageDrawable = new Image(new Texture("Items/medkitImg.png")).getDrawable();
    }

    @Override
    public boolean use() {
        if (player.heal(50)) {
            return true;
        }
        return false;
    }
    
}
