package com.charvas.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.charvas.entities.EntityManager;
import com.charvas.entities.Player;

public class World {
    
    EntityManager entManager;
    Player playerRef = null;

    public final Player getPlayer() { return playerRef; }

    public final void processInput() {
        playerRef.handleInput();
    }
    public final void update() {
        entManager.update();
    }

    public final void draw(SpriteBatch pBatch) {
        entManager.draw(pBatch);
    }

    public final boolean spawnGoose(Vector2 spawnPos) {
        return true;
    }

    public final boolean spawnPlayer(Vector2 spawnPos) {

    }
}
