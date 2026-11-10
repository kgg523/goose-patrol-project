package com.charvas.main;
import com.charvas.entities.Player;

public class PlayerHurtTestItem extends Item {
        /* This is a TEST item for testing adjustability of hp bar */
    private Player player;

    public PlayerHurtTestItem(GameScreen game, Player player) {
        super(game);
        name = "hurt";
        this.player = player;
    }

    @Override
    public boolean use() {
        if (game.getPausedState()) {
            return false;
        }
        player.takeDamage(20);
        return false; //Allows using the item without it being removed
    }
}
