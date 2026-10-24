package com.charvas.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    static final float V_WIDTH = 30f; // Virtual width
    static final float V_HEIGHT = 30f; // Virtual height
    public SpriteBatch spriteBatch;
    public FitViewport viewport;

    public void create() {
        spriteBatch = new SpriteBatch();

        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render(); // wont work unless called as render() is overwritten in GameScreen()
    }

    public void dispose() {
        spriteBatch.dispose();
    }
}
