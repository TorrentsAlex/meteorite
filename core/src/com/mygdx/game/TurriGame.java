package com.mygdx.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TurriGame extends ApplicationAdapter {

	private Viewport viewport;

	private int gameState;
	private SpriteBatch batch;
	private Texture greyBackground;
	private Game game;

	private interface GameState {
		int RENDERING = 0;
		int MENU = 1;
		int GAME = 2;
	}

	@Override
	public void create () {
		viewport = new FitViewport(1600, 900);
		batch = new SpriteBatch();
		greyBackground = new Texture("backgroundgrey.png");
		game = new Game(viewport);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(greyBackground,0 ,0);
		batch.end();

		game.render();
	}

	@Override
	public void dispose() {

	}

}
