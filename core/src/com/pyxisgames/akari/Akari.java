package com.pyxisgames.akari;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.pyxisgames.akari.screens.MainMenuScreen;
import com.pyxisgames.akari.screens.PlayScreen;

public class Akari extends Game {
	public SpriteBatch batch;
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 800;
	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		// Generate font for cells
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("./fonts/vanilla-extract.regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 18;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
