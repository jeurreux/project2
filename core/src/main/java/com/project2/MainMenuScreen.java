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

public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;
    private Texture background, title, playButton, leaderboardButton, rocket;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load textures
        background = new Texture("background.png");
        title = new Texture("title.png");
        playButton = new Texture("play-button.png");
        leaderboardButton = new Texture("leaderboards-button.png");
        rocket = new Texture("rocket-up.png");

        Table table = new Table();
        table.setFillParent(true);

        // Background
        Image bgImage = new Image(background);
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        // DO NOT TOUCH THE VALUES IT WILL AFFECT THE MOTHER FUCKING ROCKET >:(
        table.add(new Image(title)).width(900).height(300).padBottom(0).row();

        // DO NOT TOUCH VALUES I SPENT 15 MINUTES TO LINE IT UP PERFECTLY I WILL KMS IF YOU 2 BREAK IT
        table.add(new Image(rocket))
            .width(135).height(100)
            .padTop(565).padBottom(320).row();

        // DO NOT TOUC THE SHIT ISTG FUCK HTHIS SHIT
        Image playBtn = new Image(playButton);
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(playBtn).width(375).height(275).padTop(50).row();

        Image leaderboardBtn = new Image(leaderboardButton);
        leaderboardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardScreen(game));
            }
        });
        table.add(leaderboardBtn).width(600).height(200).padTop(20);

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
        playButton.dispose();
        leaderboardButton.dispose();
        rocket.dispose();
    }


    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
}
