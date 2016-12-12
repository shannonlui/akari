package com.pyxisgames.akari;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pyxisgames.akari.screens.PlayScreen;

public class Akari extends Game {
	public SpriteBatch batch;
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 800;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
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
