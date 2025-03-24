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
    private Texture background;
    private RocketPocket rocketPocket;

    public GameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("background.png");
        rocketPocket = new RocketPocket(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        rocketPocket.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        rocketPocket.dispose();
    }

    // Other required Screen methods...
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
