package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;

/**
 * Created by shannonlui on 2016-12-11.
 */

public class PlayScreen implements Screen {
    private Akari game;
    Texture img;
    private OrthographicCamera cam;
    private Viewport vp;


    public PlayScreen(Akari game) {
        this.game = game;
        img = new Texture("badlogic.jpg");
        cam = new OrthographicCamera();
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(img, 0, 0);
        game.batch.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        img.dispose();
    }
}
