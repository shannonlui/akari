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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;
import com.pyxisgames.akari.GridCell;

import java.util.Iterator;

/**
 * Created by shannonlui on 2016-12-11.
 */

public class PlayScreen implements Screen {
    private Akari game;
    private OrthographicCamera cam;
    private Viewport vp;
    private ObjectMap<Vector2, GridCell> cellMap = new ObjectMap();
    private BitmapFont font;
    private Grid grid;

    // Textures and Sprites
    private Texture cellTexture;
    private Texture lightBulb;
    private Sprite bulbSprite;


    public PlayScreen(Akari game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.position.set(Akari.GAME_WIDTH / 2, Akari.GAME_HEIGHT / 2, 0);
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);

        // Set up textures and sprites
        cellTexture = new Texture("cell.png");
        cellTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        lightBulb = new Texture("light_bulb9.png");
        lightBulb.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bulbSprite = new Sprite(lightBulb);
        bulbSprite.setColor(Color.DARK_GRAY);

       // createGrid();
        grid = new Grid(7, 7);
        setBulbSize(grid.getCellLength());

        // Generate font for cells
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("./fonts/vanilla-extract.regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
    }

    public void update(float delta) {
        // If touch detected, get touch position
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            vp.unproject(touchPos);

            grid.update(touchPos);

        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        GlyphLayout layout = new GlyphLayout();

        // Draw the grid
        Iterator<GridCell> itr = grid.getCellMap().values();
        GridCell cell;
        while (itr.hasNext()) {
            cell = itr.next();
            cell.draw(game.batch);
            // Draw light bulb on top of cell if cell has bulb
            switch (cell.getState()) {
                case LIGHTBULB:
                    bulbSprite.setCenter(cell.getX() + cell.getWidth()/2, cell.getY() + cell.getHeight()/2);
                    bulbSprite.draw(game.batch);
                    break;
                case BLACK:
                    if (cell.getBlackNumber() > 0) {
                        String num = Integer.toString(cell.getBlackNumber());
                        layout.setText(font, num);
                        float fontX = cell.getX() + (cell.getWidth() - layout.width) / 2;
                        float fontY = cell.getY() + (cell.getHeight() + layout.height) / 2;
                        font.draw(game.batch, num, fontX, fontY);
                    }
                    break;
            }
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
        cellTexture.dispose();
        lightBulb.dispose();
    }

    public void setBulbSize(float cellLength) {
        float bulbHeight = cellLength * 3/4;
        bulbSprite.setSize(bulbHeight/bulbSprite.getHeight() * bulbSprite.getWidth(), bulbHeight);
    }

}
