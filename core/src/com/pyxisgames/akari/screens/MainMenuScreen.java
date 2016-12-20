package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;
import com.pyxisgames.akari.GridCell;

/**
 * Created by shannonlui on 2016-12-18.
 */

public class MainMenuScreen implements Screen {
    private Akari game;
    private OrthographicCamera cam;
    private Viewport vp;
    private Sprite cell;
    //float width = 9;
    private float width = 2;
    private float borderEdge = 25;
    private float borderBetween = 5;
    private float cellLength;
    private float initialY = 180;
    private Stage stage;
    private Label.LabelStyle labelStyle;

    public MainMenuScreen(Akari game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.position.set(Akari.GAME_WIDTH / 2, Akari.GAME_HEIGHT / 2, 0);
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);
        cell = new Sprite(game.cellTexture);
        cellLength = (Akari.GAME_WIDTH - (borderEdge * 2 + borderBetween))/width;
        cell.setSize(cellLength, cellLength);
        cell.setColor(Color.valueOf("#bfbfbf"));

        stage = new Stage(vp, game.batch);
        setButtons();
        game.inputMultiplexer.addProcessor(stage);
    }

    public void setButtons() {
        //labelStyle = new Label.LabelStyle(game.numFont, Color.GRAY);

        // Easy button
        ClickListener easyListener = new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new SelectLevelScreen(game, Akari.Difficulty.EASY));
            }
        };
        newButton(borderEdge,  initialY + borderBetween + cellLength, Color.valueOf("#fffda3"), Color.GRAY, easyListener, "easy");

        // Medium button
        ClickListener medListener = new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PlayScreen(game, 1));
            }
        };
        newButton(borderEdge + cellLength + borderBetween,  initialY + borderBetween + cellLength,
                Color.valueOf("#9cecf2"), Color.WHITE, medListener, "medium");

        // Hard button
        ClickListener hardListener = new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PlayScreen(game, 1));
            }
        };
        newButton(borderEdge, initialY, Color.valueOf("#ff8a8a"), Color.WHITE, hardListener, "hard");

        // Settings button
        ClickListener settingsListener = new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                //dispose();
            }
        };
        newButton(borderEdge + cellLength + borderBetween, initialY,
                Color.valueOf("#d9d9d9"), Color.GRAY, settingsListener, "settings");
    }

    public Actor newButton(float x, float y, Color bckgColor, Color fontColor, ClickListener cl, String label) {
        // Create button actor
        Actor button = new Image(game.cellTexture);
        button.setSize(cellLength, cellLength);
        button.setColor(bckgColor);
        button.setPosition(x, y);
        button.addListener(cl);
        stage.addActor(button);

        // Create button label
        Label.LabelStyle style = new Label.LabelStyle(game.numFont, fontColor);
        Label lb = new Label(label, style);
        lb.setPosition(button.getX() + (button.getWidth() - lb.getWidth()) /2,
                button.getY() + (button.getHeight() - lb.getHeight()) /2);
        lb.addListener(cl);
        stage.addActor(lb);
        return button;
    }

    @Override
    public void show() {
       // Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        GlyphLayout layout = new GlyphLayout();

        game.batch.begin();
        String title = "akari";
        layout.setText(game.titleFont, title);
        float fontX = (Akari.GAME_WIDTH - layout.width) / 2;
        float fontY = 740;
        game.titleFont.draw(game.batch, title, fontX, fontY);
        game.batch.end();

        stage.draw();

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
        game.inputMultiplexer.removeProcessor(stage);
        stage.dispose();
    }
}
