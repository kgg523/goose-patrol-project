package com.charvas.main;

// libGDX imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;
import com.charvas.UI.GUI;
import com.charvas.UI.GameTimer;
// GoosePatrol imports
import com.charvas.entities.World;
import com.charvas.items.Inventory;
import com.charvas.items.MedKitItem;
import com.charvas.items.PlayerHurtTestItem;

public class GameScreen implements Screen {
    /*
     * Game screen implementation, handles the game logic and rendering of the main
     * game loop
     */

    final Main game; // access to the main game class for rendering and resource management

    // Game assets and state variables
    private Texture backgroundTexture = null;
    private static final float mapScale = 128f;

    private TextureAtlas atlas = null; // Load textures for entity's all at once

    private boolean isPaused = true; // Flag to pause the game

    private World world = null;

    private GUI gui = null;
    private GameTimer timer = null;

    public GameScreen(final Main inGame) {
        /* Initialising game assets and state variables */
        game = inGame;
        atlas = new TextureAtlas(Gdx.files.internal("Atlas/game_atlas.atlas"));
        backgroundTexture = new Texture("background.png");

    }

    @Override
    public void show() {
        world = new World(atlas, "map.tmx");
        world.getCamera().setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
        world.getCamera().update();
        Inventory inventory = world.getPlayer().getInventory();
        
        gui = new GUI(this);
        inventory.addHud(gui.getHud());
        gui.setup(world);
        timer = new GameTimer(gui);

        inventory.addItem(new MedKitItem(world.getPlayer()));
        inventory.addItem(new PlayerHurtTestItem(world.getPlayer()));
    }

    @Override
    public void render(float delta) {
        // Render gui and game elements
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gui.togglePause();
        }

        if (!isPaused) {
            logic(delta);
            // Update animation first so input can use the updated animation state
            input();
        }

        draw();

    }

    private void input() {
        // Handle user input here
        world.processInput();
    }

    private void logic(float delta) {
        // Update game logic here
        world.update();
        timer.decrement(delta);
    }

    private void draw() {
        // Draw game objects here
        ScreenUtils.clear(Color.BLACK);

        world.draw(game.spriteBatch);

        gui.render();

    }

    @Override
    public void resize(int width, int height) {
        /*
         * Forcefully sets min scaling at 1920x1080 causing stretching when expanding
         * window size above 1920x1080, but prevents the camera from zooming out too far
         * and showing the black background
         */
        OrthographicCamera worldCamRef = world.getCamera();

        worldCamRef.viewportWidth = Math.min(width, 1920) / mapScale; // Assuming your tiles are 32x32 pixels
        worldCamRef.viewportHeight = Math.min(height, 1080) / mapScale; // Assuming your tiles are 32x32 pixels
        worldCamRef.update();
        gui.resize(width, height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        // game.pause();
    }

    @Override
    public void resume() {
        // game.resume();
    }

    public void dispose() {
        /* Dispose of the resources used to reduce memory usage */
        game.spriteBatch.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
        game.dispose();
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public GUI getGui() {
        return gui;
    }

    public World getWorld() {
        return world;
    }

    public boolean getPausedState() {
        return isPaused;
    }
}
