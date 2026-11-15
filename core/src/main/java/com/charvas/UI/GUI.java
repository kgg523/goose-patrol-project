package com.charvas.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.charvas.entities.World;
import com.charvas.main.GameScreen;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;

public class GUI{
    private Stage stage;
    private Skin skin;
    private GameScreen game;
    private HUD hud;


    public GUI(GameScreen gameScr) {
        game = gameScr;
        stage = new Stage(new StretchViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        hud = new HUD(this);
    }

    public void setup(World world) {
        hud.setup(world);
    }


//        METHOD FOR INTERACTING WITH PAUSE BUTTON
    public void togglePause() {
        hud.pause();
        game.togglePause();
    }




//        METHOD FOR INTERACTING WITH TIMER
    public void updateTimer(float time) {
        int secs = MathUtils.floor(time);
        String timerString = String.valueOf(Math.floorDiv(secs, 60)) + ":" 
        + String.valueOf(new DecimalFormat("00").format(Math.floorMod(secs, 60)));
        hud.setTimerString(timerString);
    }


    public void create () {

    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void render () {
        hud.updateHpBar();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public void update() {
        hud.setEventCounter(game.getWorld().getEventCount());
    }


    /***********/
    /* GETTERS */
    /***********/

    public Stage getStage() {
        return stage;
    }

    public GameScreen getGame() {
        return game;
    }

    public HUD getHud(){
        return hud;
    }

    public boolean getPausedState() {
        return game.getPausedState();
    }
}
