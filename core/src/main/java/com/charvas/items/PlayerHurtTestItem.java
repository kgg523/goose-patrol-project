package com.charvas.items;
import com.charvas.entities.Player;
import com.charvas.main.GameScreen;

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
        player.takeDamage(20);
        return false; //Allows using the item without it being removed
    }
}
