package com.charvas.main;

public class GameTimer {
    private float time;
    private GUI gui;

    public GameTimer(GUI gGUI) {
        time = 300f;
        gui = gGUI;
    }

    public boolean decrement(float amount) {
        time -= amount;
        gui.updateTimer(time);
        return time <= 0;
    }
}
