package com.pyxisgames.akari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pyxisgames.akari.Akari;

/**
 * Created by shannonlui on 2016-12-19.
 */

public class SelectLevelScreen implements Screen {

    private Akari game;
    private OrthographicCamera cam;
    private Viewport vp;
    private Akari.Difficulty difficulty;
    private Stage stage;
    private int cellsPerRow = 6;
    private int cellsPerCol = 7;
    private float cellLength;
    private float borderWidth = 20;


    public SelectLevelScreen(Akari game, Akari.Difficulty diff) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.position.set(Akari.GAME_WIDTH / 2, Akari.GAME_HEIGHT / 2, 0);
        vp = new FitViewport(Akari.GAME_WIDTH, Akari.GAME_HEIGHT, cam);
        this.difficulty = diff;

        stage = new Stage(vp, game.batch);
        game.inputMultiplexer.addProcessor(stage);
        createButtons();
    }

    public void createButtons() {
        cellLength = (Akari.GAME_WIDTH - ((cellsPerRow + 1) * borderWidth))/cellsPerRow;
        float x = borderWidth;
        float y = 700;
        int lvlNum = 1;
        for (int j = cellsPerCol; j >= 1; j--) {
            for (int i = 1; i <= cellsPerRow; i++) {
                final int lvl = lvlNum;
                ClickListener cl = new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        dispose();
                        game.setScreen(new PlayScreen(game, lvl));
                    }
                };
                Actor button = new Image(game.cellTexture);
                button.setSize(cellLength, cellLength);
                button.setColor(Color.valueOf("#e8e8e8"));
                button.setPosition(x, y);
                button.addListener(cl);
                stage.addActor(button);

                // Create button label
                Label.LabelStyle style = new Label.LabelStyle(game.numFont, Color.DARK_GRAY);
                Label lb = new Label(Integer.toString(lvlNum), style);
                lb.setPosition(button.getX() + (button.getWidth() - lb.getWidth()) /2,
                        button.getY() + (button.getHeight() - lb.getHeight()) /2);
                lb.addListener(cl);
                stage.addActor(lb);
                x += borderWidth + cellLength;
                lvlNum++;
            }
            x = borderWidth;
            y -= (borderWidth + cellLength);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        GlyphLayout layout = new GlyphLayout();

        game.batch.begin();
        /*String title = "akari";
        layout.setText(game.titleFont, title);
        float fontX = (Akari.GAME_WIDTH - layout.width) / 2;
        float fontY = 740;
        game.titleFont.draw(game.batch, title, fontX, fontY);*/
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
