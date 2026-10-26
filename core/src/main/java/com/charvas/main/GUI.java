package com.charvas.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class GUI {
    private Stage stage;
    private Table table;
    private SpriteBatch batch;
    private Skin skin;
    private GameScreen game;
    private Button pauseButton;
    private Label timerLabel;

    public GUI(GameScreen gameScr) {
        game = gameScr;
        stage = new Stage(new StretchViewport(1920, 1080));

        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(true); // This is optional, but enables debug lines for tables.
        
        skin = new Skin();
        
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        ButtonStyle bs = new ButtonStyle();
        bs.up = skin.newDrawable("white", Color.DARK_GRAY);


//                                 PAUSE BUTTON


        ButtonStyle pauseStyle = new ButtonStyle();
        Image pauseImg = new Image(new Texture("pause.png"));
        pauseImg.setHeight(100);
        pauseImg.setWidth(100);
        Image pauseOnImg = new Image(new Texture("pause_on.png"));
        pauseOnImg.setHeight(100);
        pauseOnImg.setWidth(100);
        pauseStyle.up = pauseImg.getDrawable();
        pauseStyle.checked = pauseOnImg.getDrawable();

        pauseButton = new Button(pauseStyle);
        pauseButton.setPosition(20, 1020);
        pauseButton.setHeight(50);
        pauseButton.setWidth(50);
        pauseButton.addListener(new ClickListener() {
            @Override 
            public void clicked (InputEvent event, float x, float y) {
                pauseButton.setChecked(!pauseButton.isChecked());
                togglePause();
            }
        });
        stage.addActor(pauseButton);



//                                 TIMER LABEL

        
        Pixmap labelColor = new Pixmap(145, 70, Pixmap.Format.RGB888);
        labelColor.setColor(Color.BLACK);
        labelColor.fill();
        LabelStyle tmLabelStyle  = new LabelStyle();
        tmLabelStyle.background = new Image(new Texture(labelColor)).getDrawable();
        tmLabelStyle.font = new BitmapFont();
        timerLabel = new Label("5:00", tmLabelStyle);
        timerLabel.setFontScale(5f);
        timerLabel.setPosition(887, 1020);
        stage.addActor(timerLabel);









        Button button = new Button(skin.newDrawable("white", Color.RED));
        button.setWidth(100);
        button.setHeight(100);
        button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println(event + " " + actor);
                System.out.println("Clicked! Is checked: " + button.isChecked());
            }
        });
        
        stage.addActor(button);

    }



//        METHOD FOR INTERACTING WITH PAUSE BUTTON
    public void togglePause() {
        pauseButton.setChecked(!pauseButton.isChecked());
        game.pause();
    }




//        METHOD FOR INTERACTING WITH TIMER
    public void updateTimer(float time) {
        int secs = MathUtils.floor(time);
        String timerString = String.valueOf(Math.floorDiv(secs, 60)) + ":" 
        + String.valueOf(new DecimalFormat("00").format(Math.floorMod(secs, 60)));
        timerLabel.setText(timerString);
    }

    public void create () {

    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void render () {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
