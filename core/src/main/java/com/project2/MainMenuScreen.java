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
// smh so many imports
public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private Texture startButtonTexture;
    private Texture leaderboardButtonTexture;
    private Texture rocketTexture;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("background.png");
        titleTexture = new Texture("title.png");
        startButtonTexture = new Texture("play-button.png");
        leaderboardButtonTexture = new Texture("leaderboards-button.png");
        rocketTexture = new Texture("rocket-up.png"); // load rocket texture

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // DO NOT TOUCH VALUES I SPENT 15 MINUTES ADJUUSTING THE VALUES TO MAKE IT PERFECT
        // Nah - Haldon
        // values were touched </3 jk
        Image titleImage = new Image(titleTexture);
        rootTable.add(titleImage)
            .width(900)
            .height(300)
            .padBottom(0)
            .row();

        // DO NOT TOUCH TOO OR ELSE I WILL KMS LEAVE IT AS IS
        //Im gonna adjust the forbidden properties - Haldon
        Image rocketImage = new Image(rocketTexture);
        rootTable.add(rocketImage)
            .width(135)
            .height(100)
            .padTop(565)
            .padBottom(320)
            .row();

        Image startButton = new Image(startButtonTexture);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        // literally just space with oddly specific values plz dont touch
        rootTable.add(startButton).width(375).height(275).padTop(50).row();

        Image leaderboardButton = new Image(leaderboardButtonTexture);
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardScreen(game));
            }
        });
        // space again :/
        rootTable.add(leaderboardButton).width(600).height(200).padTop(20);

        stage.addActor(rootTable);
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
    //nothing yet queen
    }

    @Override
    public void resume() {
     //nothing yet
    }

    @Override
    public void hide() {
        // none yet
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        titleTexture.dispose();
        startButtonTexture.dispose();
        leaderboardButtonTexture.dispose();
        rocketTexture.dispose();
    }
}
