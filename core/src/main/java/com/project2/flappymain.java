package com.project2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;


public class flappymain extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;

    Texture gameover;

    Texture[] rockets;
    int flyState = 0;
    float rocketY = 0;
    float velocity = 0;
    Circle rocketCircle;
    int score = 0;
    int scoringTube = 0;
    BitmapFont font;

    int gameState = 0;
    float gravity = 2;

    Texture asteroids;
    Texture satellites;
    float gap = 400;
    float maxdebrisOffset;
    Random randomGenerator;
    float debrisVelocity = 4;
    int numberOfdebris = 4;
    float[] debrisX = new float[numberOfdebris];
    float[] debrisOffset = new float[numberOfdebris];
    float distanceBetweendebris;
    Rectangle[] asteroidsRectangles;
    Rectangle[] satellitesRectangles;



    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        gameover = new Texture("gameover.png");
        //shapeRenderer = new ShapeRenderer();
        rocketCircle = new Circle();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

        rockets = new Texture[2];
        rockets[0] = new Texture("rocket-up.png");
        rockets[1] = new Texture("rocket-down.png");


        asteroids = new Texture("asteroids.png");
        satellites = new Texture("satellites.png");
        maxdebrisOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        distanceBetweendebris = Gdx.graphics.getWidth() * 3 / 4;
        asteroidsRectangles = new Rectangle[numberOfdebris];
        satellitesRectangles = new Rectangle[numberOfdebris];


        startGame();





    }

    public void startGame() {

        rocketY = Gdx.graphics.getHeight() / 2 - rockets[0].getHeight() / 2;

        for (int i = 0; i < numberOfdebris; i++) {

            debrisOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

            debrisX[i] = Gdx.graphics.getWidth() / 2 - asteroids.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweendebris;

            asteroidsRectangles[i] = new Rectangle();
            satellitesRectangles[i] = new Rectangle();

        }

    }

    @Override
    public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            if (debrisX[scoringTube] < Gdx.graphics.getWidth() / 2) {

                score++;

                Gdx.app.log("Score", String.valueOf(score));

                if (scoringTube < numberOfdebris - 1) {

                    scoringTube++;

                } else {

                    scoringTube = 0;

                }

            }

            if (Gdx.input.justTouched()) {

                velocity = -30;

            }

            for (int i = 0; i < numberOfdebris; i++) {

                if (debrisX[i] < - asteroids.getWidth()) {

                    debrisX[i] += numberOfdebris * distanceBetweendebris;
                    debrisOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

                } else {

                    debrisX[i] = debrisX[i] - debrisVelocity;



                }

                batch.draw(asteroids, debrisX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + debrisOffset[i]);
                batch.draw(satellites, debrisX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - satellites.getHeight() + debrisOffset[i]);

                asteroidsRectangles[i] = new Rectangle(debrisX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + debrisOffset[i], asteroids.getWidth(), asteroids.getHeight());
                satellitesRectangles[i] = new Rectangle(debrisX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - satellites.getHeight() + debrisOffset[i], satellites.getWidth(), satellites.getHeight());
            }



            if (rocketY > 0) {

                velocity = velocity + gravity;
                rocketY -= velocity;

            } else {

                gameState = 2;

            }

        } else if (gameState == 0) {

            if (Gdx.input.justTouched()) {

                gameState = 1;


            }

        } else if (gameState == 2) {

            batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);

            if (Gdx.input.justTouched()) {

                gameState = 1;
                startGame();
                score = 0;
                scoringTube = 0;
                velocity = 0;


            }

        }

        if (flyState == 0) {
            flyState = 1;
        } else {
            flyState = 0;
        }



        batch.draw(rockets[flyState], Gdx.graphics.getWidth() / 2 - rockets[flyState].getWidth() / 2, rocketY);

        font.draw(batch, String.valueOf(score), 100, 200);

        rocketCircle.set(Gdx.graphics.getWidth() / 2, rocketY + rockets[flyState].getHeight() / 2, rockets[flyState].getWidth() / 2);


        for (int i = 0; i < numberOfdebris; i++) {


            if (Intersector.overlaps(rocketCircle, asteroidsRectangles[i]) || Intersector.overlaps(rocketCircle, satellitesRectangles[i])) {

                gameState = 2;

            }

        }

        batch.end()



    }


}
