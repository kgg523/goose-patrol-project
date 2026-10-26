package com.charvas.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.charvas.entities.Player;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    /*
     * Game screen implementation, handles the game logic and rendering of the main
     * game loop
     */

    final Main game; // access to the main game class for rendering and resource management

    // Game assets and state variables
    private Texture backgroundTexture;
    private static final float mapScale = 128f;
    private Player player;

    private TextureAtlas atlas;

    private float stateTime;
    private boolean isPaused = false; // Flag to pause the game

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    public GameScreen(final Main inGame) {
        /* Initialising game assets and state variables */
        game = inGame;
        atlas = new TextureAtlas(Gdx.files.internal("Atlas/game_atlas.atlas"));

        backgroundTexture = new Texture("background.png");

        stateTime = 0f;

        player = new Player(atlas.findRegions("playerRunning"), atlas.findRegions("playerIdle"));
        player.setSize(1, 1); // Set the size of the goose sprite to 1x1 units
    }

    @Override
    public void show() {
        // Initialising the TiledMap and its renderer, as well as the camera
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f); // Assuming your tiles are 32x32 pixels
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isPaused = !isPaused;
        }
        if (!isPaused) {
            logic();
            updateAnimation(delta); // Update animation first so input can use the updated animation state
            input();
        }
        if (isPaused) {

        }
        draw();

    }

    private void input() {
        // Handle user input here
        player.input(); // Call the input method of the goose sprite to handle movement

    }

    private void logic() {
        // Update game logic here
        player.handleBorders();

    }

    private void draw() {
        // Draw game objects here
        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera.combined, 0, 0, Main.V_WIDTH, Main.V_HEIGHT); // Set mapRenderer to use the camera
        mapRenderer.render();

        /*
         * Update the camera position to follow the goose sprite, while keeping it
         * within the bounds of the world
         */
        camera.position.set(player.getX() + player.getWidth() / 2,
                player.getY() + player.getHeight() / 2, 0);
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2,
                Main.V_WIDTH - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2,
                Main.V_HEIGHT - camera.viewportHeight / 2);

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        player.draw(game.spriteBatch); // Draw the goose sprite

        game.spriteBatch.end();

        camera.update();
    }

    private void updateAnimation(float deltaTime) {
        /* Update the animation state time and the goose sprite's animation */
        stateTime += deltaTime;
        player.updateAnimation(stateTime);
    }

    @Override
    public void resize(int width, int height) {
        /*
         * Forcefully sets min scaling at 1920x1080 causing stretching when expanding
         * window size above 1920x1080, but prevents the camera from zooming out too far
         * and showing the black background
         */
        camera.viewportWidth = Math.min(width, 1920) / mapScale; // Assuming your tiles are 32x32 pixels
        camera.viewportHeight = Math.min(height, 1080) / mapScale; // Assuming your tiles are 32x32 pixels
        camera.update();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        game.pause();
    }

    @Override
    public void resume() {
        game.resume();
    }

    public void dispose() {
        /* Dispose of the resources used to reduce memory usage */
        game.spriteBatch.dispose();
        backgroundTexture.dispose();
        map.dispose();
        atlas.dispose();
        mapRenderer.dispose();
        game.dispose();
    }

}
