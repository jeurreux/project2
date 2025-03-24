package com.project2;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
