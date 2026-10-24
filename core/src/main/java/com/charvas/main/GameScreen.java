package com.charvas.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.charvas.entities.Entity;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    final Main game;
    private Texture backgroundTexture;
    private static final float mapScale = 128f;

    private Entity gooseSprite;

    private Animation<TextureRegion> gooseAnimation;

    private TextureAtlas atlas;

    private float stateTime;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    public GameScreen(final Main inGame) {
        game = inGame;
        atlas = new TextureAtlas(Gdx.files.internal("Atlas/game_atlas.atlas"));

        backgroundTexture = new Texture("background.png");

        gooseAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("gooseFrame"), Animation.PlayMode.LOOP);
        stateTime = 0f;

        gooseSprite = new Entity(atlas.findRegion("gooseFrame", 2)); // Load the goose sprite from the texture atlas
        gooseSprite.setSize(1, 1); // Set the size of the goose sprite to 1x1 units
    }

    @Override
    public void show() {
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f); // Assuming your tiles are 32x32 pixels
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();

    }

    private void input() {
        // Handle user input here
        float speed = 3f;
        float deltaTime = Gdx.graphics.getDeltaTime(); // Keep speed consistant across different frame rates

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            gooseSprite.translateX(-speed * deltaTime);
            gooseSprite.setFlip(true, false); // Flip the sprite horizontally when moving left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            gooseSprite.translateX(speed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            gooseSprite.translateY(speed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            gooseSprite.translateY(-speed * deltaTime);
        }

    }

    private void logic() {
        // Update game logic here
        float worldWidth = Main.V_WIDTH;
        float worldHeight = Main.V_HEIGHT;
        gooseSprite.setX(MathUtils.clamp(gooseSprite.getX(), 0, worldWidth - gooseSprite.getWidth()));
        gooseSprite.setY(MathUtils.clamp(gooseSprite.getY(), 0, worldHeight - gooseSprite.getHeight()));

    }

    private void draw() {
        // Draw game objects here
        ScreenUtils.clear(Color.BLACK);
        stateTime += Gdx.graphics.getDeltaTime();
        mapRenderer.setView(camera.combined, 0, 0, Main.V_WIDTH, Main.V_HEIGHT);
        mapRenderer.render();
        camera.position.set(gooseSprite.getX() + gooseSprite.getWidth() / 2,
                gooseSprite.getY() + gooseSprite.getHeight() / 2, 0);
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2,
                Main.V_WIDTH - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2,
                Main.V_HEIGHT - camera.viewportHeight / 2);

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        TextureRegion currentFrame = gooseAnimation.getKeyFrame(stateTime, true);
        gooseSprite.setRegion(currentFrame); // Update the goose sprite's texture region to the current frame
        gooseSprite.draw(game.spriteBatch); // Draw the goose sprite

        game.spriteBatch.end();

        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / mapScale; // Assuming your tiles are 32x32 pixels
        camera.viewportHeight = height / mapScale; // Assuming your tiles are 32x32 pixels
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
        game.spriteBatch.dispose();
        backgroundTexture.dispose();
        map.dispose();
        atlas.dispose();
        mapRenderer.dispose();
        game.dispose();
    }

}
