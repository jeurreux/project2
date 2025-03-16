package com.project2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class flappymain extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    Texture [] rockets;
    int rocketstate = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        rockets = new Texture[2];
        rockets [0]  = new Texture("rocket-up.png");
        rockets [1] = new Texture("rocket-down.png");
    }

    @Override
    public void render() {
        if (Gdx.input.justTouched())
            Gdx.app.log("touched", "don't touch me!");
        if(rocketstate == 0){
            rocketstate= 1;
        } else{
            rocketstate = 0;
        }
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(rockets[rocketstate], Gdx.graphics.getWidth() /2 - rockets[rocketstate].getWidth(), Gdx.graphics.getHeight() / 2 - rockets[rocketstate].getHeight() /2);
        batch.end();
    }


}
