package com.charvas.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Texture backgroundTexture;

    private Texture gooseTexture;
    private Sprite gooseSprite;

    private Vector2 touchPosition;

    @Override
    public void create() {
        viewport = new FitViewport(8, 8);
        spriteBatch = new SpriteBatch();
        gooseTexture = new Texture("8BitGoose.png");
        backgroundTexture = new Texture("background.png");
        gooseSprite = new Sprite(gooseTexture);
        gooseSprite.setSize(1, 1); // Set the size of the goose sprite to 1x1 units
        touchPosition = new Vector2();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void render() {
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
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            gooseSprite.translateX(speed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            gooseSprite.translateY(speed * deltaTime);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            gooseSprite.translateY(-speed * deltaTime);
        }

        if (Gdx.input.isTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPosition);
            gooseSprite.setCenter(touchPosition.x, touchPosition.y);
        }

    }

    private void logic() {
        // Update game logic here
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        gooseSprite.setX(MathUtils.clamp(gooseSprite.getX(), 0, worldWidth - gooseSprite.getWidth()));
        gooseSprite.setY(MathUtils.clamp(gooseSprite.getY(), 0, worldHeight - gooseSprite.getHeight()));
    }

    private void draw() {
        // Draw game objects here
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // Draw the background texture to fill the
        gooseSprite.draw(spriteBatch); // Draw the goose sprite

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        gooseTexture.dispose();
    }
}
