package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.pyxisgames.akari.Akari;
import com.pyxisgames.akari.GridCell;

import java.util.Iterator;

/**
 * Created by shannonlui on 2016-12-17.
 */

public class Grid {
    // Grid dimensions
    private int width;
    private int height;
    private float borderWidth = 5;
    private float cellLength;

    private ObjectMap<Vector2, GridCell> cellMap = new ObjectMap();

    private Texture cellTexture;

    public Grid(int width, int height, int lvl) {
        cellTexture = new Texture("cell.png");
        cellTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.width = width;
        this.height = height;
        createGrid(lvl);
    }

    public void createGrid(int lvlNum) {
        cellLength = (Akari.GAME_WIDTH - ((width + 1) * borderWidth))/width;
        float yStartPos = (Akari.GAME_HEIGHT - ((height - 1) * borderWidth) - cellLength * height) / 2;
        float x = borderWidth;
        float y = yStartPos;

        // Add blank cells to the cellMap
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                GridCell cell = new GridCell(cellTexture, i, j);
                cell.setSize(cellLength, cellLength);
                cell.setPosition(x, y);

                cellMap.put(new Vector2(i, j), cell);
                y += borderWidth + cellLength;
            }
            x += borderWidth + cellLength;
            y = yStartPos;
        }

        // Add black cells from level data file
        FileHandle handle = Gdx.files.internal("levels/easy.json");
        JsonValue root = new JsonReader().parse(handle);
        JsonValue blackCells = root.get("levels").get(lvlNum).get("blackCells");
        for (JsonValue blk : blackCells) {
            GridCell curr = cellMap.get(new Vector2(blk.get(0).asFloat(), blk.get(1).asFloat()));
            curr.tintBlack(blk.get(2).asInt());
        }
    }

    public void update(Vector3 touchPos) {
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

    public ObjectMap<Vector2, GridCell> getCellMap() {
        return cellMap;
    }

    public float getCellLength() {
        return cellLength;
    }
}
