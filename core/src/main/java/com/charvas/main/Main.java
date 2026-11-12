package com.charvas.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    /* Main class which serves as the entry point for the application */
    public static final float V_WIDTH = 40f; // Virtual width
    public static final float V_HEIGHT = 30f; // Virtual height
    public SpriteBatch spriteBatch;
    public FitViewport viewport;

    public void create() {
        // Starts the game elements
        spriteBatch = new SpriteBatch();

        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render(); // wont work unless called, as render() is overwritten in GameScreen(). DO NOT
                        // REMOVE
    }

    public void dispose() {
        /* Dispose of the resources used to reduce memory usage */
        spriteBatch.dispose();
    }
}
