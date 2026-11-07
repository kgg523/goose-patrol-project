package com.charvas.main;

public class SpeedItem extends Item{
    /* This is a TEST item. 
    Doesn't work due to problems with the player class
    but all item class functionalities work fine */

    public SpeedItem(GameScreen game) {
        super(game);
        name = "Speed";
    }

    @Override
    public boolean use() {
        System.out.println("Speed used");
        return true;
    }
    
}
