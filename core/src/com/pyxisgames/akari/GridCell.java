package com.pyxisgames.akari;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by shannonlui on 2016-12-13.
 */


public class GridCell extends Sprite {
    public enum State {
        EMPTY, BLACK, LIGHTBULB, CONFLICT
    }

    private Vector2 coords;
    private State state;
    private int lightCount = 0;
    private int conflictCount = 0;
    private int blackNumber = 0;

    public GridCell(Texture texture, int x, int y) {
        super(texture);
        coords = new Vector2(x, y);
        this.state = State.EMPTY;
    }

    public Vector2 getCoords() {
        return coords;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public void tintBlack(int num) {
        setState(State.BLACK);
        setColor(Color.DARK_GRAY);
        blackNumber = num;
    }

    public void addBulb() {
        setState(State.LIGHTBULB);
        incrCount();
    }

    public void removeBulb() {
        setState(State.EMPTY);
        decrCount();
    }

    public void incrCount() {
        lightCount++;
        if (lightCount == 1) {
            setColor(Color.valueOf("#f4e648"));
        }
    }

    public void decrCount() {
        lightCount--;
        if (lightCount == 0) {
            setColor(Color.WHITE);
        }
    }

    public void incrConflict() {
        conflictCount++;
        if (conflictCount == 1) {
            setColor(Color.FIREBRICK);
        }
    }

    public void decrConflict() {
        conflictCount--;
        if (conflictCount == 0) {
            setColor(Color.valueOf("#f4e648"));
        }
    }

    public int getBlackNumber() {
        return blackNumber;
    }
}
