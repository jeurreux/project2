package com.project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import java.util.List;
import java.util.ArrayList;
// smh so many imports

public class LeaderboardScreen implements Screen {

    private Game game;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture leaderboardTitleTexture;
    private Texture backButtonTexture;

    public LeaderboardScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load images
        backgroundTexture = new Texture("background.png");
        Texture leaderboardTitleTexture;
        leaderboardTitleTexture = new Texture(Gdx.files.internal("leaderboards.png"));
        backButtonTexture = new Texture("arrow-button.png");

        Table table = new Table();
        table.setFillParent(true);

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(table);

        // leaderboard title
        Image leaderboardTitle = new Image(leaderboardTitleTexture);
        table.add(leaderboardTitle).padBottom(50).row();

        // dummy high scores until player comes through
        List<String> scores = new ArrayList<>();
        scores.add("1. Player1 - 5000");
        scores.add("2. Player2 - 4000");
        scores.add("3. Player3 - 3000");
        scores.add("4. Player4 - 2000");
        scores.add("5. Player5 - 1000");

        for (String score : scores) {
            Label scoreLabel = new Label(score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            table.add(scoreLabel).padBottom(10).row();
        }

        // back or quit button
        Image backButton = new Image(backButtonTexture);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(backButton).padTop(20);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        leaderboardTitleTexture.dispose();
        backButtonTexture.dispose();
    }
}

