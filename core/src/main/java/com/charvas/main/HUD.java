package com.charvas.main;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.charvas.entities.World;

public class HUD {
    private GameScreen game;
    private World world;
    private GUI gui;
    private Stage stage;
    private Button pauseButton;
    private Label pauseLabel;
    private Label timerLabel;
    private Image inventoryBgImage;
    private Image hpBarImg;
    private ItemSlot[] itemSlots = new ItemSlot[8];
    

    public HUD(GameScreen game, GUI gui) {
        this.game = game;
        this.gui = gui;
        stage = gui.getStage();
    
    }

    public void setup(World world) {
        this.world = world;
        Inventory inv = world.getPlayer().getInventory();
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

        
        Pixmap tmlabelColor = new Pixmap(145, 70, Pixmap.Format.RGB888);
        tmlabelColor.setColor(Color.BLACK);
        tmlabelColor.fill();
        LabelStyle tmLabelStyle  = new LabelStyle();
        tmLabelStyle.background = new Image(new Texture(tmlabelColor)).getDrawable();
        tmLabelStyle.font = new BitmapFont();
        timerLabel = new Label("5:00", tmLabelStyle);
        timerLabel.setFontScale(5f);
        timerLabel.setPosition(887, 1020);
        tmlabelColor.dispose();
        stage.addActor(timerLabel);



        //                                 INVENTORY
        Pixmap invLabelColor = new Pixmap(700, 350, Pixmap.Format.RGB888);
        Skin skin = new Skin();
        invLabelColor.setColor(Color.DARK_GRAY);
        invLabelColor.fill();  
        skin.add("bg", new Texture(invLabelColor));
        inventoryBgImage = new Image(skin.newDrawable("bg"));
        inventoryBgImage.setSize(710,360);
        inventoryBgImage.setPosition(1210, 0);
        invLabelColor.dispose();
        stage.addActor(inventoryBgImage);

        //Slots which will be used to display and interact with items in the inventory
        int count = 0;
        for (int y = 185; y >= 0; y -= 175) {
            for (int x = 1230; x <= 1755; x += 175) {
                itemSlots[count] =  new ItemSlot(inv, x, y, count);
                count++;
            }
        }

        for (ItemSlot slot : itemSlots) {
            stage.addActor(slot.getWidget());
        }


        //                                 HEALTH BAR
        Pixmap hpLabelColor = new Pixmap(700, 350, Pixmap.Format.RGB888);
        hpLabelColor.setColor(Color.GREEN);
        hpLabelColor.fill();  
        skin.add("hpBar", new Texture(hpLabelColor));
        hpBarImg = new Image(skin.newDrawable("hpBar"));
        hpBarImg.setPosition(10, 10);
        hpBarImg.setSize(750, 100);
        Image hpBgImg = new Image(skin.newDrawable("bg"));
        hpBgImg.setPosition(0, 0);
        hpBgImg.setSize(770, 120);
        stage.addActor(hpBgImg);
        stage.addActor(hpBarImg);
    }

    public GUI getGui() {
        return gui;
    }

    public void pause() {
        pauseButton.setChecked(!pauseButton.isChecked());
        pauseLabel.setVisible(pauseButton.isChecked());
    }

    public void setTimerString(String str) {
        timerLabel.setText(str);
    }

    public void updateItemSlot(int index, Drawable img) {
        itemSlots[index].setImage(img);
    }

    public void updateHpBar() {
        float hp = world.getPlayer().getHealth();
        float maxHealth = world.getPlayer().getMaxHealth();
        hpBarImg.setSize(750*(hp/maxHealth), 100);
    }

    private class ItemSlot {
        /* A single interactable slot of the inventory */
        private Inventory inv;
        private Image slot;
        private int index;
        private Drawable blankImg;

        public ItemSlot(Inventory inv, float x, float y, int index) {
            this.inv = inv;
            this.index = index;
            slot = new Image();
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
            Skin skin = new Skin();
            pixmap.setColor(Color.GRAY);
            pixmap.fill(); 
            skin.add("empty", new Texture(pixmap));
            blankImg = skin.newDrawable("empty");
            pixmap.dispose();
    
            slot.setDrawable(blankImg);
            slot.setPosition(x, y);
            slot.setSize(155, 155);
            slot.addListener(new ClickListener() {
            @Override 
            public void clicked (InputEvent event, float x, float y) {
                inv.useItem(index);
            }
        });

        }

        public Image getWidget() {
            return slot;
        }

        public void setImage(Drawable image){
            if (image == null) {
                slot.setDrawable(blankImg);
            }else {
                slot.setDrawable(image);
            }
        }

    }

}
