package com.pyxisgames.akari;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by shannonlui on 2016-12-18.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport vp;
    private int moves = 0;
    Label movesLabel;

    public Hud(Akari game, Viewport vp, int lvl) {
        this.vp = vp;
        stage = new Stage(vp, game.batch);

        Actor backButton = new Image(game.backTexture);
        backButton.setSize(30, 30);
        backButton.setColor(Color.DARK_GRAY);
        backButton.setPosition(15, 760);
        stage.addActor(backButton);
        final Akari akari = game;
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                akari.prevScreen();
            }
        });

        LabelStyle lvlStyle = new LabelStyle(game.numFont, Color.DARK_GRAY);
        Actor lvlLabel = new Label("level " + lvl, lvlStyle);
        lvlLabel.setPosition(60, 757);
        stage.addActor(lvlLabel);

        LabelStyle style = new LabelStyle(game.labelFont, Color.DARK_GRAY);
        movesLabel = new Label("moves: " + moves, style);
        movesLabel.setPosition(15, 685);
        stage.addActor(movesLabel);

        Actor bestLabel = new Label("my best: --", style);
        bestLabel.setPosition(Akari.GAME_WIDTH - bestLabel.getWidth() - 15, 685);
        stage.addActor(bestLabel);

        // Akari.GAME_WIDTH / 2 -
        Actor restartLabel = new Label("restart", style);
        restartLabel.setPosition((Akari.GAME_WIDTH - restartLabel.getWidth()) /2, 150);
        restartLabel.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                //akari.prevScreen();
            }
        });
        stage.addActor(restartLabel);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void updateMoves() {
        moves++;
        movesLabel.setText("moves: " + moves);

    }

    public int getMoves() {
        return moves;
    }
}
