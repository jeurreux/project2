package com.project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class GameOverScreen implements Screen {
    private Game game;
    private Stage stage;
    private Texture background, gameover, backButton;
    private int score;

    public GameOverScreen(Game game, int score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background.png");
        gameover = new Texture("gameover.png");
        backButton = new Texture("quit-button.png");

        Table table = new Table();
        table.setFillParent(true);

        // Background
        Image bgImage = new Image(background);
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        // Game Over Image
        table.add(new Image(gameover)).padBottom(100).row();

        // Back Button
        Image backBtn = new Image(backButton);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(backBtn).width(200).height(200);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        gameover.dispose();
        backButton.dispose();
    }

    // Other required Screen methods...
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
}
