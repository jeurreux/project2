package com.project2;

import com.badlogic.gdx.Game;

public class MainGame extends Game { // CHANGED
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this)); // *sigh* start with MainMenuScreen instead of game
    }
}
