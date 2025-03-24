package com.project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import java.util.Random;

public class RocketPocket {
    private Game game;
    private SpriteBatch batch;
    private Texture background, gameover, backButton;
    private Preferences prefs;
    private int[] highScores = new int[3];
    private Texture[] rockets;
    private int flyState = 0;
    private float rocketY = 0;
    private double velocity = 0;
    private Circle rocketCircle;
    private int score = 0;
    private int scoringTube = 0;
    private BitmapFont font;
    private int gameState = 0;
    private double gravity = 1.5;
    private Texture asteroids, satellites;
    private float gap = 700;
    private float maxDebrisOffset;
    private Random random;
    private float debrisVelocity = 4;
    private int numberOfDebris = 4;
    private float[] debrisX = new float[numberOfDebris];
    private float[] debrisOffset = new float[numberOfDebris];
    private float distanceBetweenDebris;
    private Rectangle[] asteroidsRectangles;
    private Rectangle[] satellitesRectangles;
    private Stage gameOverStage;

    public RocketPocket(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("background.png");
        gameover = new Texture("gameover.png");
        backButton = new Texture("quit-button.png");
        rocketCircle = new Circle();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

        prefs = Gdx.app.getPreferences("RocketPocketPrefs");
        loadHighScores();

        rockets = new Texture[2];
        rockets[0] = new Texture("rocket-up.png");
        rockets[1] = new Texture("rocket-down.png");

        asteroids = new Texture("asteroids.png");
        satellites = new Texture("satellites.png");
        maxDebrisOffset = Gdx.graphics.getHeight()/2 - gap/2 - 100;
        random = new Random();
        distanceBetweenDebris = Gdx.graphics.getWidth() * 3/4;
        asteroidsRectangles = new Rectangle[numberOfDebris];
        satellitesRectangles = new Rectangle[numberOfDebris];

        gameOverStage = new Stage(new ScreenViewport());

        startGame();
    }

    private void startGame() {
        rocketY = Gdx.graphics.getHeight()/2 - rockets[0].getHeight()/2;
        for (int i = 0; i < numberOfDebris; i++) {
            debrisOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
            debrisX[i] = Gdx.graphics.getWidth()/2 - asteroids.getWidth()/2 + Gdx.graphics.getWidth() + i * distanceBetweenDebris;
            asteroidsRectangles[i] = new Rectangle();
            satellitesRectangles[i] = new Rectangle();
        }
    }

    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) { // Game running
            // Game logic...
            if (debrisX[scoringTube] < Gdx.graphics.getWidth()/2) {
                score++;
                if (scoringTube < numberOfDebris - 1) {
                    scoringTube++;
                } else {
                    scoringTube = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -30;
            }

            for (int i = 0; i < numberOfDebris; i++) {
                if (debrisX[i] < -asteroids.getWidth()) {
                    debrisX[i] += numberOfDebris * distanceBetweenDebris;
                    debrisOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                } else {
                    debrisX[i] -= debrisVelocity;
                }

                batch.draw(asteroids, debrisX[i], Gdx.graphics.getHeight()/2 + gap/2 + debrisOffset[i]);
                batch.draw(satellites, debrisX[i], Gdx.graphics.getHeight()/2 - gap/2 - satellites.getHeight() + debrisOffset[i]);

                asteroidsRectangles[i].set(debrisX[i], Gdx.graphics.getHeight()/2 + gap/2 + debrisOffset[i],
                    asteroids.getWidth(), asteroids.getHeight());
                satellitesRectangles[i].set(debrisX[i], Gdx.graphics.getHeight()/2 - gap/2 - satellites.getHeight() + debrisOffset[i],
                    satellites.getWidth(), satellites.getHeight());
            }

            if (rocketY > 0 && rocketY < Gdx.graphics.getHeight()) {
                velocity += gravity;
                rocketY -= velocity;
            } else {
                gameState = 2; // Game over
            }

        } else if (gameState == 0) { // Ready screen
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) { // Game over
            batch.draw(gameover, Gdx.graphics.getWidth()/2 - gameover.getWidth()/2,
                Gdx.graphics.getHeight()/2 - gameover.getHeight()/2);
            batch.end();

            // Show game over UI
            if (gameOverStage.getActors().size == 0) {
                setupGameOverUI();
            }

            gameOverStage.act();
            gameOverStage.draw();
            return;
        }

        flyState = (flyState == 0) ? 1 : 0;
        batch.draw(rockets[flyState], Gdx.graphics.getWidth()/2 - rockets[flyState].getWidth()/2, rocketY);
        font.draw(batch, String.valueOf(score), 100, 200);

        rocketCircle.set(Gdx.graphics.getWidth()/2, rocketY + rockets[flyState].getHeight()/2,
            rockets[flyState].getWidth()/2);

        for (int i = 0; i < numberOfDebris; i++) {
            if (Intersector.overlaps(rocketCircle, asteroidsRectangles[i]) ||
                Intersector.overlaps(rocketCircle, satellitesRectangles[i])) {
                gameState = 2;
                break;
            }
        }

        batch.end();
    }

    private void setupGameOverUI() {
        Gdx.input.setInputProcessor(gameOverStage);
        addScoreToLeaderboard(score);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(100);

        Image backBtn = new Image(backButton);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(backBtn).width(200).height(200);
        gameOverStage.addActor(table);
    }

    private void loadHighScores() {
        String storedScores = prefs.getString("scores", "0,0,0");
        String[] split = storedScores.split(",");
        for (int i = 0; i < split.length && i < highScores.length; i++) {
            highScores[i] = Integer.parseInt(split[i]);
        }
    }

    private void addScoreToLeaderboard(int newScore) {
        for (int i = 0; i < highScores.length; i++) {
            if (newScore > highScores[i]) {
                for (int j = highScores.length - 1; j > i; j--) {
                    highScores[j] = highScores[j - 1];
                }
                highScores[i] = newScore;
                break;
            }
        }
        saveScoresToFile();
    }

    private void saveScoresToFile() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < highScores.length; i++) {
            sb.append(highScores[i]);
            if (i < highScores.length - 1) sb.append(",");
        }
        prefs.putString("scores", sb.toString());
        prefs.flush();
    }

    public void dispose() {
        batch.dispose();
        background.dispose();
        gameover.dispose();
        backButton.dispose();
        for (Texture t : rockets) t.dispose();
        asteroids.dispose();
        satellites.dispose();
        font.dispose();
        gameOverStage.dispose();
    }
}
