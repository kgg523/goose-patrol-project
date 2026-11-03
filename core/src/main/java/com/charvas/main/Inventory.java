package com.charvas.main;

import com.badlogic.gdx.Game;

public class Inventory {
    private GameScreen game;
    private HUD hud;
    private Item[] items = new Item[8]; 
    private final int MAX_SIZE = 8;
    private int size = 0;

    public Inventory(GameScreen game, GUI gui) {
        this.game = game;
        hud = gui.getHud();
        
    }

    public boolean addItem(Item item) {
        if (size == MAX_SIZE) {
            return false;
        }
        size++;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (items[i] == null) {
                items[i] = item;
                hud.updateItemSlot(i, item.getDrawable());
                return true;
            }
        }
        return false;
    }

    public void useItem(int index) {
        if (items[index] == null) {
            return;
        }
        if (items[index].use()) {
            items[index] = null;
            hud.updateItemSlot(index, null);
        }
    }




}
