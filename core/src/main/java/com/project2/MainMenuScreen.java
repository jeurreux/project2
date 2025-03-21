package com.project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MainMenuScreen implements Screen {

    private Game game; // Store reference to game
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private Texture startButtonTexture;
    private Texture leaderboardButtonTexture;

    public MainMenuScreen(Game game) {
        this.game = game; // Initialize game reference
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        // Load images
        backgroundTexture = new Texture("background.png"); // Same background as other pages
        titleTexture = new Texture("title.png");
        startButtonTexture = new Texture("play-button.png");
        leaderboardButtonTexture = new Texture("leaderboards-button.png");

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage); // Set background
        stage.addActor(table);

        // game title
        Image titleImage = new Image(titleTexture);
        titleImage.setSize(990, 350);
        table.add(titleImage).width(990).height(350).padTop(300).row();

        // space to push buttons down
        table.add().height(1000).row();

        // start button
        Image startButton = new Image(startButtonTexture);
        startButton.setSize(475, 200);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(startButton).width(475).height(200).padBottom(20).row();

        // leaderboard button
        Image leaderboardButton = new Image(leaderboardButtonTexture);
        leaderboardButton.setSize(600, 200);
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardScreen(game)); // Switch to leaderboard
            }
        });
        table.add(leaderboardButton).width(600).height(200).padTop(20);

        stage.addActor(table);
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
    public void pause() {
        // Required method, can be left empty
    }

    @Override
    public void resume() {
        // Required method, can be left empty
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        titleTexture.dispose();
        startButtonTexture.dispose();
        leaderboardButtonTexture.dispose();
    }
}




