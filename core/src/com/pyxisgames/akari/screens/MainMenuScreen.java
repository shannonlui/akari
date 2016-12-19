package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;
import com.pyxisgames.akari.GridCell;

/**
 * Created by shannonlui on 2016-12-18.
 */

public class MainMenuScreen implements Screen {
    private Akari game;
    private OrthographicCamera cam;
    private Viewport vp;
    private Sprite cell;
    float width = 9;
    float borderWidth = 3;
    float cellLength;


    public MainMenuScreen(Akari game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.position.set(Akari.GAME_WIDTH / 2, Akari.GAME_HEIGHT / 2, 0);
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);
        cell = new Sprite(game.cellTexture);
        cellLength = (Akari.GAME_WIDTH - ((width + 1) * borderWidth))/width;
        cell.setSize(cellLength, cellLength);
        cell.setColor(Color.valueOf("#bfbfbf"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        GlyphLayout layout = new GlyphLayout();

        game.batch.begin();
        float x,y;
        x = 140;
        y = 500;
        String easy = "easy";
        for (int j = 0; j < 4; j++) {
            cell.setPosition(x, y);
            cell.draw(game.batch);
            String easyChar = easy.substring(j, j+1);
            layout.setText(game.numFont, easyChar);
            float fontX = cell.getX() + (cell.getWidth() - layout.width) / 2;
            float fontY = cell.getY() + (cell.getHeight() + layout.height) / 2;
            game.numFont.draw(game.batch, easyChar, fontX, fontY);
            x += borderWidth + cellLength;
        }

        x = 90;
        y = 400;
        String normal = "normal";
        for (int j = 0; j < 6; j++) {
            cell.setPosition(x, y);
            cell.draw(game.batch);
            String easyChar = normal.substring(j, j+1);
            layout.setText(game.numFont, easyChar);
            float fontX = cell.getX() + (cell.getWidth() - layout.width) / 2;
            float fontY = cell.getY() + (cell.getHeight() + layout.height) / 2;
            game.numFont.draw(game.batch, easyChar, fontX, fontY);
            x += borderWidth + cellLength;
        }

        x = 140;
        y = 300;
        String hard = "hard";
        for (int j = 0; j < 4; j++) {
            cell.setPosition(x, y);
            cell.draw(game.batch);
            String easyChar = hard.substring(j, j+1);
            layout.setText(game.numFont, easyChar);
            float fontX = cell.getX() + (cell.getWidth() - layout.width) / 2;
            float fontY = cell.getY() + (cell.getHeight() + layout.height) / 2;
            game.numFont.draw(game.batch, easyChar, fontX, fontY);
            x += borderWidth + cellLength;
        }


        String title = "akari";
        layout.setText(game.titleFont, title);
        float fontX = (Akari.GAME_WIDTH - layout.width) / 2;
        float fontY = 700;
        game.titleFont.draw(game.batch, title, fontX, fontY);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
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

    }
}
