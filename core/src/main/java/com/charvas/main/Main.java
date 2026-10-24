package com.charvas.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    static final int V_WIDTH = 30; // Virtual width
    static final int V_HEIGHT = 30; // Virtual height
    public SpriteBatch spriteBatch;
    public FitViewport viewport;

    public void create() {
        spriteBatch = new SpriteBatch();
        // use libGDX's default font
        //viewport = new FitViewport(V_WIDTH, V_HEIGHT);

        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render(); // wont work unless called as render() is overwritten in GameScreen()
    }

    public void dispose() {
        spriteBatch.dispose();
    }
}
