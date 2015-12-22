package com.mygdx.game;

import box2dLight.ConeLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Alex Torrents on 24/11/2015.
 */
public class Player implements IScript {

    private final World world;
    Entity entity;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    private final float speed = 500f;
    private final float speedMobile = 1000f;

    private float distancePassed = 0;

    private final float gravity = -40f;
    private float velocity = 0;
    private final float jumpSpeed = 15f;
    private boolean landed = false;
    private PlayerLight light;
    private PlayerCircleLight playerCircleLight;
    private PlayerCircleLight playerCircleLight2;

    public Player(World world, PlayerLight light, PlayerCircleLight playerCircleLight,PlayerCircleLight playerCircleLight2) {
        this.world = world;
        this.light = light;
        this.playerCircleLight = playerCircleLight;
        this.playerCircleLight2 = playerCircleLight2;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;

        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        transformComponent.originX = dimensionsComponent.width / 2;
        transformComponent.originY = dimensionsComponent.height / 2;
    }

    @Override
    public void act(float delta) {
        float diff = 0;
        boolean lightFlipped;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            diff = -speed * delta;
            transformComponent.scaleX = -1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            diff = +speed * delta;
            transformComponent.scaleX = 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (landed) {
                velocity = jumpSpeed;
                landed = false;
            }
        }
        velocity += gravity*delta;

        transformComponent.x += diff;
        transformComponent.y += velocity;
        distancePassed += Math.abs(diff);
        rayCast();

        // flip the lightt of the Player
        if (transformComponent.scaleX > 0) {
            lightFlipped = false;
        } else {
            lightFlipped = true;
        }

        light.setPosition(transformComponent.x, transformComponent.y, lightFlipped);
        playerCircleLight.setPosition(getCenterX(),getCenterY());
        playerCircleLight2.setPosition(getCenterX(), getCenterY());
    }

    @Override
    public void dispose() {

    }

    public float getScaleX() {
        return transformComponent.scaleX ;
    }

    public float getCenterX() {
        return transformComponent.x + dimensionsComponent.width / 2;
    }

    public float getCenterY() {
        return transformComponent.y + dimensionsComponent.height / 2;
    }

    public float getDistancePassed() {
        return distancePassed;
    }

    /**
     * This method isn't mine,
     * it's from Azakhary
     * I copied pasted from here https://github.com/azakhary/platformer_tutorial
     */
    private void rayCast() {
        float rayGap = dimensionsComponent.height/2;

        // Ray size is the exact size of the deltaY change we plan for this frame
        float raySize = -(velocity) * Gdx.graphics.getDeltaTime();

        // only check for collisions when moving down
        if(velocity > 0) return;

        // Vectors of ray from middle middle
        Vector2 rayFrom = new Vector2((transformComponent.x+dimensionsComponent.width/2)*PhysicsBodyLoader.getScale(), (transformComponent.y+rayGap)* PhysicsBodyLoader.getScale());
        Vector2 rayTo = new Vector2((transformComponent.x+dimensionsComponent.width/2)* PhysicsBodyLoader.getScale(), (transformComponent.y - raySize)*PhysicsBodyLoader.getScale());

        // Cast the ray
        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                // Stop the player
                velocity = 0;
                landed = true;
                // reposition player slightly upper the collision point
                transformComponent.y  = point.y / PhysicsBodyLoader.getScale() + 0.01f;

                return 0;
            }
        }, rayFrom, rayTo);
    }

    public float getHeight() {
        return dimensionsComponent.height;
    }

    public float getWidth() {
        return dimensionsComponent.width;
    }
}