package com.pyxisgames.akari;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * if black: count = number on the cell
 * else: count = number of light bulbs lighting this cell
 * Created by shannonlui on 2016-12-13.
 */

public class GridCell extends Sprite {
    private Vector2 coords;
    private boolean isBlack = false;
    private boolean hasBulb = false;
    private int count = 0;

    public GridCell(Texture texture, int x, int y) {
        super(texture);
        coords = new Vector2(x, y);
    }

    public GridCell(Texture texture, int x, int y, boolean isBlack) {
        this(texture, x, y);
        this.isBlack = isBlack;
        if (isBlack) {
            setColor(Color.BLACK);
        }
    }

    public Vector2 getCoords() {
        return coords;
    }

    public void tintBlack() {
        isBlack = true;
        setColor(Color.DARK_GRAY);
    }

    public void lightUp() {
        setColor(Color.valueOf("#f4e648"));

    }

    public void addBulb() {
        hasBulb = true;
        count++;
        lightUp();
    }

    public void removeBulb() {
        hasBulb = false;
        decCount();
    }

    public void decCount() {
        count--;
        if (count == 0) {
            setColor(Color.WHITE);
        }
    }

    public boolean isBlack() {
        return isBlack;
    }

    public boolean hasBulb() {
        return hasBulb;
    }
}
