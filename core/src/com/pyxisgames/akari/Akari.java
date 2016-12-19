package com.pyxisgames.akari;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.pyxisgames.akari.screens.MainMenuScreen;
import com.pyxisgames.akari.screens.PlayScreen;

public class Akari extends Game {
	public SpriteBatch batch;
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 800;
	public BitmapFont numFont;
	public BitmapFont labelFont;
	public BitmapFont titleFont;
	public Texture cellTexture;
	public Texture backTexture;
	public Texture lightBulb;
	public InputMultiplexer inputMultiplexer;


	@Override
	public void create () {
		batch = new SpriteBatch();
		cellTexture = new Texture("cell.png");
		cellTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		backTexture = new Texture("back_arrow.png");
		backTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		lightBulb = new Texture("light_bulb9.png");
		lightBulb.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		// Generate font for cells

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/vanilla-extract.regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 26;
		numFont = generator.generateFont(parameter);
		//parameter.color = Color.DARK_GRAY;
		//labelFont = generator.generateFont(parameter);
		generator.dispose();

		FreeTypeFontGenerator titleFontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/lane.narrow.ttf"));
		parameter.size = 85;
		parameter.color = Color.DARK_GRAY;
		titleFont = titleFontGen.generateFont(parameter);
		titleFontGen.dispose();

		FreeTypeFontGenerator labelFontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ek-mukta.regular.ttf"));
		parameter.size = 28;
		//parameter.color = Color.DARK_GRAY;
		parameter.color = Color.WHITE;
		labelFont = labelFontGen.generateFont(parameter);
		labelFontGen.dispose();

		numFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		titleFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		labelFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);

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

	public void prevScreen() {
		Screen old = getScreen();
		old.dispose();
		setScreen(new MainMenuScreen(this));
	}
}
