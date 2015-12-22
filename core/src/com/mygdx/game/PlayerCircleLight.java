package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Alex Torrents on 22/12/2015.
 */
public class PlayerCircleLight implements IScript {

    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;

    @Override
    public void init(Entity entity) {
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        transformComponent.originX = dimensionsComponent.width / 2;
        transformComponent.originY = dimensionsComponent.height / 2;
    }

    @Override
    public void act(float delta) {

    }
    public void setPosition(float x, float y) {
        transformComponent.x = x;
        transformComponent.y = y;

    }

    @Override
    public void dispose() {

    }
}
