package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Alex Torrents on 26/12/2015.
 */
public class Game {
    private SceneLoader gameSL;
    private ItemWrapper rootItem;

    private Player player;
    private PlayerLight playerLight;
    private PlayerCircleLight playerCircleLight;
    private Viewport viewport;

    public Game (Viewport viewport) {
        this.viewport = viewport;
        gameSL = new SceneLoader();
        gameSL.loadScene("MainScene", this.viewport);
        rootItem = new ItemWrapper(gameSL.getRoot());

        playerLight = new PlayerLight(viewport.getCamera());
        playerCircleLight = new PlayerCircleLight();
        player = new Player(gameSL.world, playerLight, playerCircleLight);

        rootItem.getChild("player").addScript(player);
        rootItem.getChild("playerLight").addScript(playerLight);
        rootItem.getChild("playerCircleLight").addScript(playerCircleLight);

        playerLight.setBody(player.getWidth(), player.getHeight());
    }

    public void render() {
        gameSL.getEngine().update(Gdx.graphics.getDeltaTime());
        ((OrthographicCamera) viewport.getCamera()).position.set(player.getCenterX(), player.getCenterY() + Gdx.graphics.getHeight() / 4, 1);
    }
}
