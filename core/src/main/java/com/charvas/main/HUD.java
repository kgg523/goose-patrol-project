package com.charvas.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HUD {
    private GUI gui;
    private Stage stage;
    private Button pauseButton;
    private Label pauseLabel;
    private Label timerLabel;
    

    public HUD(GUI gGui) {
        gui = gGui;
        stage = gui.getStage();
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
        pauseButton.setChecked(true);
        pauseButton.addListener(new ClickListener() {
            @Override 
            public void clicked (InputEvent event, float x, float y) {
                pauseButton.setChecked(!pauseButton.isChecked());
                gui.togglePause();
            }
        });
        stage.addActor(pauseButton);

        LabelStyle pauseLabelStyle  = new LabelStyle();
        pauseLabelStyle.font = new BitmapFont();
        pauseLabel = new Label("PAUSED", pauseLabelStyle);
        pauseLabel.setFontScale(6f);
        pauseLabel.setPosition(790, 960);
        pauseLabel.setVisible(true);
        stage.addActor(pauseLabel);



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
    }


    


    public void pause() {
        pauseButton.setChecked(!pauseButton.isChecked());
        pauseLabel.setVisible(pauseButton.isChecked());
    }

    public void setTimerString(String str) {
        timerLabel.setText(str);
    }
}
