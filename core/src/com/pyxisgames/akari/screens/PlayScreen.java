package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;
import com.pyxisgames.akari.Grid;
import com.pyxisgames.akari.GridCell;
import com.pyxisgames.akari.Hud;

import java.util.Iterator;

/**
 * Created by shannonlui on 2016-12-11.
 */

public class PlayScreen implements Screen, GestureDetector.GestureListener {
    private Akari game;
    private OrthographicCamera cam;
    private Viewport vp;
    private Grid grid;
    private int lvl = 1;
    private Hud hud;
    private GestureDetector gestureDetector;

    // Textures and Sprites
    private Sprite bulbSprite;
    private Sprite backSprite;


    public PlayScreen(Akari game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.position.set(Akari.GAME_WIDTH / 2, Akari.GAME_HEIGHT / 2, 0);
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);
        hud = new Hud(game, vp, lvl);
        game.inputMultiplexer.addProcessor(hud.stage);
        gestureDetector = new GestureDetector(this);
        game.inputMultiplexer.addProcessor(gestureDetector);

        // Set up textures and sprites
        bulbSprite = new Sprite(game.lightBulb);
        bulbSprite.setColor(Color.DARK_GRAY);
        backSprite = new Sprite(game.backTexture);
        backSprite.setColor(Color.DARK_GRAY);
        backSprite.setSize(30, 30);
        backSprite.setPosition(15, 760);

       // createGrid();
        grid = new Grid(7, 7, 2);
        setBulbSize(grid.getCellLength());
    }

    @Override
    public void render(float delta) {
       // update(delta);

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        drawGrid();
        /*backSprite.draw(game.batch);
        game.labelFont.draw(game.batch, "level " + lvl, 60, 785);*/

        game.batch.end();

        hud.stage.draw();
    }

    public void drawGrid() {
        GlyphLayout layout = new GlyphLayout();
        Iterator<GridCell> itr = grid.getCellMap().values();
        GridCell cell;
        while (itr.hasNext()) {
            cell = itr.next();
            cell.draw(game.batch);
            // Draw light bulb on top of cell if cell has bulb
            switch (cell.getState()) {
                case BLACK:
                    if (cell.getBlackNum() > 0) {
                        // Label the black cell with its number
                        String num = Integer.toString(cell.getBlackNum());
                        layout.setText(game.numFont, num);
                        float fontX = cell.getX() + (cell.getWidth() - layout.width) / 2;
                        float fontY = cell.getY() + (cell.getHeight() + layout.height) / 2;
                        game.numFont.draw(game.batch, num, fontX, fontY);
                    }
                    break;
                case CONFLICT:
                case LIGHTBULB:
                    bulbSprite.setCenter(cell.getX() + cell.getWidth()/2, cell.getY() + cell.getHeight()/2);
                    bulbSprite.draw(game.batch);
                    break;
            }
        }
    }

    @Override
    public void show() {
        //Gdx.input.setInputProcessor(hud.stage);
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
        game.inputMultiplexer.removeProcessor(hud.stage);
        game.inputMultiplexer.removeProcessor(gestureDetector);
        hud.dispose();
    }

    public void setBulbSize(float cellLength) {
        float bulbHeight = cellLength * 3/4;
        bulbSprite.setSize(bulbHeight/bulbSprite.getHeight() * bulbSprite.getWidth(), bulbHeight);
    }



    /* Gesture */
    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (!grid.cleared) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            vp.unproject(touchPos);

            if (grid.update(touchPos)) {
                hud.updateMoves();

                if (grid.cleared) {
                    System.out.println("LEVEL CLEARED with moves=" + hud.getMoves());
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
