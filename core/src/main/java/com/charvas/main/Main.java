package com.charvas.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    public SpriteBatch spriteBatch;
    public FitViewport viewport;

    public void create() {
        spriteBatch = new SpriteBatch();
        // use libGDX's default font
        viewport = new FitViewport(8, 8);

        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        spriteBatch.dispose();
    }
}
