package com.charvas.UI;

public class GameTimer {
    
    private float time;
    private GUI gui;

    public GameTimer(GUI gGUI) {
        time = 300f; // 5 minutes;
        gui = gGUI;
    }

    public boolean decrement(float amount) {
        time -= amount;
        gui.updateTimer(time);
        return time <= 0;
    }
}
