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

        backgroundTexture = new Texture("background.png");
        Texture leaderboardTitleTexture;
        leaderboardTitleTexture = new Texture(Gdx.files.internal("leaderboards.png"));
        backButtonTexture = new Texture("arrow-button.png");

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(table);

        // leaderboard title
        Image leaderboardTitle = new Image(leaderboardTitleTexture);
        table.add(leaderboardTitle)
            .padBottom(50)
            .width(900)
            .height(300)
            .padBottom(0)
            .row();

        /**
         BitmapFont font = new BitmapFont();
         font.getData().setScale(10f);           oh my days does this even do anything smh
         Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
         **/

        // dummy high scores until player comes through
        List<String> scores = new ArrayList<>();
        scores.add("1. Player1 - 50");
        scores.add("2. Player2 - 40");
        scores.add("3. Player3 - 30");
        scores.add("4. Player4 - 20");
        scores.add("5. Player5 - 10");

        for (String score : scores) {
            Label scoreLabel = new Label(score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            scoreLabel.setFontScale(5f); // bruh
            table.add(scoreLabel).padBottom(10).row();
        }

        // back or quit button
        Image backButton = new Image(backButtonTexture);
        backButton.setSize(200, 200);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(backButton)
            .padTop(20)
            .width(200)
            .height(200)
            .center();
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
