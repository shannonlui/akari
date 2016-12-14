package com.pyxisgames.akari;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by shannonlui on 2016-12-13.
 */

public class GridCell extends Sprite {
    private Vector2 coordinates;

    public GridCell(Texture texture, int x, int y) {
        super(texture);
        coordinates = new Vector2(x, y);
    }
}
