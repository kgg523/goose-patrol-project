package com.charvas.main;

import com.charvas.entities.Player;

public class MedKitItem extends Item {
    private Player player;

    public MedKitItem(GameScreen game, Player player){
        super(game);
        this.player = player;
        name = "Medkit";
    }

    @Override
    public boolean use() {
        if (player.heal(50)) {
            return true;
        }
        return false;
    }
    
}
