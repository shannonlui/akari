package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    // Grid dimensions
    private int gridWidth = 7;
    private int gridHeight = 7;
    private float borderWidth = 5;

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

        font = new BitmapFont();
        createGrid();
    }

    public void createGrid() {
        float cellLength = (Akari.GAME_WIDTH - ((gridWidth + 1) * borderWidth))/gridWidth;
        float yStartPos = (Akari.GAME_HEIGHT - ((gridHeight - 1) * borderWidth) - cellLength * gridHeight) / 2;
        float x = borderWidth;
        float y = yStartPos;

        // Adjust the size of the light bulb sprite
        float bulbHeight = cellLength * 3/4;
        bulbSprite.setSize(bulbHeight/bulbSprite.getHeight() * bulbSprite.getWidth(), bulbHeight);

        // Add cells to the cellMap
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                GridCell cell = new GridCell(cellTexture, i, j);
                if ((i == 2 && j == 4) || (i==4 && j ==1)) {
                    cell.tintBlack();
                }
                cell.setSize(cellLength, cellLength);
                cell.setPosition(x, y);

                cellMap.put(new Vector2(i, j), cell);
                y += borderWidth + cellLength;
            }
            x += borderWidth + cellLength;
            y = yStartPos;
        }
    }

    public void update(float delta) {
        // If touch detected, get touch position
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            // Iterate through the grid to check if a cell has been touched.
            Iterator<GridCell> itr = cellMap.values();
            GridCell cell;
            while (itr.hasNext()) {
                cell = itr.next();
                if (cell.getState() != GridCell.State.BLACK
                        && cell.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                    System.out.println("Touched " + cell.getCoords());
                    if (cell.getState() == GridCell.State.LIGHTBULB) {
                        cell.removeBulb();
                        updateNeighbours(cell, false);
                    } else {
                        cell.addBulb();
                        updateNeighbours(cell, true);
                    }

                }
            }
        }
    }

    // Light up the cells in the same column or row as the given vector
    public void updateNeighbours(GridCell cell, boolean lightUp) {
        updateRow(cell, -1, lightUp);
        updateRow(cell, 1, lightUp);
        updateColumn(cell, -1, lightUp);
        updateColumn(cell, 1, lightUp);
    }

    // Light up cells in this row up until a black cell or the end of the grid
    public void updateRow(GridCell cell, int value, boolean lightUp) {
        float currX = cell.getCoords().x + value;
        float y = cell.getCoords().y;
        GridCell curr = cellMap.get(new Vector2(currX, y));
        while (curr != null && curr.getState() != GridCell.State.BLACK) {
            if (lightUp) {
                curr.incrCount();
                if (curr.getState() == GridCell.State.LIGHTBULB) {
                    System.out.println("yes also lightbulb");
                    curr.incrConflict();
                    cell.incrConflict();
                }
            } else {
                curr.decrCount();
                if (curr.getState() == GridCell.State.LIGHTBULB) {
                    curr.decrConflict();
                    cell.decrConflict();
                }
            }
            currX = currX + value;
            curr = cellMap.get(new Vector2(currX, y));
        }
    }

    // Light up cells in this column up until a black cell or the end of the grid
    public void updateColumn(GridCell cell, int value, boolean lightUp) {
        float currY = cell.getCoords().y + value;
        float x = cell.getCoords().x;
        GridCell curr = cellMap.get(new Vector2(x, currY));
        while (curr != null && curr.getState() != GridCell.State.BLACK) {
            if (lightUp) {
                curr.incrCount();
                if (curr.getState() == GridCell.State.LIGHTBULB) {
                    curr.incrConflict();
                    cell.incrConflict();
                }
            } else {
                curr.decrCount();
                if (curr.getState() == GridCell.State.LIGHTBULB) {
                    curr.decrConflict();
                    cell.decrConflict();
                }
            }
            currY = currY + value;
            curr = cellMap.get(new Vector2(x, currY));
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        // Draw the grid
        Iterator<GridCell> itr = cellMap.values();
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
               /* case BLACK:
                    font.draw(game.batch, "4", cell.getX(), cell.getY());*/
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
}
