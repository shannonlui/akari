package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;
import com.pyxisgames.akari.GridCell;

/**
 * Created by shannonlui on 2016-12-11.
 */

public class PlayScreen implements Screen {
    private Akari game;
    Texture img;
    private OrthographicCamera cam;
    private Viewport vp;
    private Stage grid;
    private Sprite s;
    private Array<Sprite> cells;
    private int gridWidth = 7;
    private int gridHeight = 7;
    private float borderWidth = 8;

    public PlayScreen(Akari game) {
        this.game = game;
        img = new Texture("badlogic.jpg");
        cam = new OrthographicCamera();
        cam.position.set(Akari.GAME_WIDTH / 2, Akari.GAME_HEIGHT / 2, 0);
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);
        s = new GridCell(img, 0 , 0);
        s.setSize(40, 40);
        s.setPosition(40,40);
        createGrid();
    }

    public void createGrid() {
        float cellLength = (Akari.GAME_WIDTH - ((gridWidth + 1) * borderWidth))/gridWidth;
        float gridYPosition = (Akari.GAME_HEIGHT - ((gridHeight - 1) * borderWidth) - cellLength * gridHeight) / 2;
        System.out.println(cellLength);
        float x = borderWidth;
        float y = gridYPosition;

        cells = new Array<Sprite>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Sprite cell = new GridCell(img, i, j);
                cell.setSize(cellLength, cellLength);
                cell.setPosition(x, y);
                cells.add(cell);
                y += borderWidth + cellLength;
            }
            x += borderWidth + cellLength;
            y = gridYPosition;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        //s.draw(game.batch);
        for (Sprite sprite : cells) {
            sprite.draw(game.batch);
        }
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
