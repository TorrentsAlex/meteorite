package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.light.LightObjectComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Alex Torrents on 24/11/2015.
 */
public class PlayerLight implements  IScript {

    private final Camera camera;
    Entity entity;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    private LightObjectComponent lightObjectComponent;
    private int scrolledAmount;

    private float distancePassed = 0;
    private float parentHeight;
    private float parentWidth;

    // variables for ligth angle;
    private boolean antFlipped;
    private Vector3 m3;
    private Vector2 mouse;
    private float targetAngle;
    private float xPosition;
    private float inter;
    private float angle;
    private float rad = 57.29f;

    public PlayerLight(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        lightObjectComponent = ComponentRetriever.get(entity, LightObjectComponent.class);
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        transformComponent.originX = dimensionsComponent.width / 2;
        transformComponent.originY = dimensionsComponent.height / 2;
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void dispose() {

    }

    /**
        This method is called from Player every tick
    */
    public void setPosition(float x, float y, boolean flipped) {
        m3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mouse = new Vector2(m3.x, m3.y);
        targetAngle = mouse.sub(new Vector2(x, y)).angleRad();
        xPosition = 0;

        inter = new Interpolation.Swing(1).apply(lightObjectComponent.directionDegree, targetAngle, MathUtils.clamp(5 / .5f, 0, 1));
        angle =  inter * rad;

        if (flipped) {
            if (angle > 105 || angle < -105) {
                lightObjectComponent.directionDegree = angle;
            }
            xPosition = x+15;
        } else {
            if (angle < 76 && angle > -76) {
                lightObjectComponent.directionDegree = angle;
            }
            xPosition = x+parentWidth-15;
        }

        // reset the light if flip the image
        if (antFlipped != flipped) {
            if (flipped) {
                lightObjectComponent.directionDegree = 180;
            } else {
                lightObjectComponent.directionDegree = 0;
            }
        }
        antFlipped = flipped;
        transformComponent.x = xPosition;
        transformComponent.y = y+parentHeight/2;
    }

    public void setBody(float width, float height) {
        this.parentWidth = width;
        this.parentHeight = height;
    }
}