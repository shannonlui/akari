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
        EMPTY, BLACK, LIGHTBULB, CONFLICT, LIT
    }

    private Vector2 coords;
    private State state;
    private int lightCount = 0;
    private int conflictCount = 0;
    private int blackNum = 0;

    public GridCell(Texture texture, int x, int y) {
        super(texture);
        coords = new Vector2(x, y);
        this.state = State.EMPTY;
    }

    public Vector2 getCoords() {
        return coords;
    }

    // Change state of the cell and tint color corresponding to the state
    public void setState(State state) {
        this.state = state;
        switch (state) {
            case EMPTY:
                setColor(Color.valueOf("#e8e8e8"));
                break;
            case BLACK:
                setColor(Color.DARK_GRAY);
                break;
            case CONFLICT:
                setColor(Color.valueOf("#ff8a8a"));
                break;
            case LIGHTBULB:
            case LIT:
                setColor(Color.valueOf("#b6f2f6"));
                break;
        }
    }

    public State getState() {
        return this.state;
    }

    public void tintBlack(int num) {
        setState(State.BLACK);
        blackNum = num;
    }

    public void addBulb() {
        setState(State.LIGHTBULB);
        incrCount();
    }

    public void removeBulb() {
        conflictCount = 0;
        if (state == State.CONFLICT) {
            setState(State.LIT);
        }
        decrCount();
    }

    public void incrCount() {
        lightCount++;
        if (lightCount == 1) {
            if (state == State.EMPTY) {
                setState(State.LIT);
            }
        }
    }

    public void decrCount() {
        lightCount--;
        if (lightCount == 0) {
            setState(State.EMPTY);
        }
    }

    public void incrConflict() {
        conflictCount++;
        if (conflictCount == 1) {
            setState(State.CONFLICT);
        }
    }

    public void decrConflict() {
        conflictCount--;
        if (conflictCount == 0) {
            setState(State.LIGHTBULB);
        }
    }

    public int getBlackNum() {
        return blackNum;
    }
}
