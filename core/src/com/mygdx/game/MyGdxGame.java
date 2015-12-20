package com.mygdx.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	private SceneLoader sl;
	private Player player;
	private PlayerLight playerLight;
	private ItemWrapper rootItem;
	private Viewport viewport;

	private float screenQuarter;
	private float screenDifference;
	private float screenChange = 0;

	private RayHandler rayHandler;

	@Override
	public void create () {
		viewport = new FitViewport(1600, 900);
		sl = new SceneLoader();
		sl.loadScene("MainScene", viewport);

		playerLight = new PlayerLight(viewport.getCamera());
		player = new Player(sl.world, playerLight);



		rootItem = new ItemWrapper(sl.getRoot());
		rootItem.getChild("player").addScript(player);
		rootItem.getChild("playerLight").addScript(playerLight);
		Gdx.input.setInputProcessor(this);
		playerLight.setBody(player.getWidth(), player.getHeight());

		screenQuarter = Gdx.graphics.getWidth() / 8;
		screenDifference =  Gdx.graphics.getWidth() - screenQuarter;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sl.getEngine().update(Gdx.graphics.getDeltaTime());
		//setCameraPosition();
		((OrthographicCamera) viewport.getCamera()).position.set(player.getCenterX(), player.getCenterY(), 1);
	}

	private void setCameraPosition() {
		if (player.getScaleX() >= 0)  {
			if (screenChange <= screenQuarter) {
				screenChange+=1;
			}
			updateCam(1,player.getCenterX()+screenChange, player.getCenterY());
		} else {
			if (screenChange >= screenQuarter) {
				screenChange-=2;
			}
			updateCam(1,player.getCenterX()-screenChange, player.getCenterY());
		}
	}
	public void updateCam(float delta,float Xtaget, float Ytarget) {

		//Creating a vector 3 which represents the target location myplayer)
		Vector3 target = new Vector3(Xtaget,Ytarget,0);
		//Change speed to your need
		final float speed=delta,ispeed=1.0f-speed;
		//The result is roughly: old_position*0.9 + target * 0.1
		Vector3 cameraPosition = ((OrthographicCamera) viewport.getCamera()).position;
		cameraPosition.scl(ispeed);
		target.scl(speed);
		cameraPosition.add(target);
		((OrthographicCamera) viewport.getCamera()).position.set(cameraPosition);
	}

	@Override
	public void dispose() {
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
