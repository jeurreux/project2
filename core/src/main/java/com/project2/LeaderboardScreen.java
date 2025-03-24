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
import com.badlogic.gdx.Preferences;
import java.util.Arrays;

public class LeaderboardScreen implements Screen {
    private Game game;
    private Stage stage;
    private Texture background, title, backButton;
    private BitmapFont font;
    private int[] highScores;

    public LeaderboardScreen(Game game) {
        this.game = game;
        Preferences prefs = Gdx.app.getPreferences("RocketPocketPrefs");
        String scores = prefs.getString("scores", "0,0,0");
        highScores = Arrays.stream(scores.split(","))
            .mapToInt(Integer::parseInt)
            .toArray();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background.png");
        title = new Texture("leaderboards.png");
        backButton = new Texture("quit-button.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);

        Table table = new Table();
        table.setFillParent(true);

        // Background
        Image bgImage = new Image(background);
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        // Title
        table.add(new Image(title)).width(900).height(300).padBottom(50).row();

        // High Scores
        for (int i = 0; i < highScores.length; i++) {
            Label scoreLabel = new Label((i+1) + ". " + highScores[i], new Label.LabelStyle(font, Color.WHITE));
            table.add(scoreLabel).padBottom(20).row();
        }

        // Back Button
        Image backBtn = new Image(backButton);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(backBtn).width(200).height(200).padTop(50);

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
        title.dispose();
        backButton.dispose();
        font.dispose();
    }

    // Other required Screen methods...
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
}
