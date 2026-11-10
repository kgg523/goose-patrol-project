package com.charvas.items;

import com.charvas.UI.HUD;
import com.charvas.main.GameScreen;


public class Inventory{
    private GameScreen game;
    private HUD hud;
    private Item[] items = new Item[8]; 
    private final int MAX_SIZE = 8;
    private int size = 0;
    private boolean pauseState;

    public boolean addItem(Item item) {
        //Adds an item into the next available slot in the inventory

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
        /* Will use the item stored in the given index of the inventory array 
            IF there exists an item in said slot and the game isn't paused
            once used an item will be removed.
            Note: Items retain their position in the array even when
                other items are removed.
            */

        if (items[index] == null) {
            return;
        }
        if (items[index].use()) {
            items[index] = null;
            hud.updateItemSlot(index, null);
        }
    }

    public void addHud(HUD hud) {
        this.hud = hud;
    }

    public void updatePauseState(boolean pause) {
        pauseState = pause;
    }




}
