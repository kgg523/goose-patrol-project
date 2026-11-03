package com.charvas.main;

public class SpeedItem extends Item{

    public SpeedItem(GameScreen game) {
        super(game);
        name = "Speed";
    }

    @Override
    public boolean use() {
        game.getWorld().getPlayer().setSpeed(50f);
        System.out.println("Speed used");
        return true;
    }
    
}
