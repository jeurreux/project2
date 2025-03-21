package com.project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private RocketPocket rocketPocket;

    public GameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.png"); // Using same background
        rocketPocket = new RocketPocket(); // Initialize game logic
        rocketPocket.create(); // Call create method from RocketPocket
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        rocketPocket.render(); // Render RocketPocket game elements
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
    }
}
