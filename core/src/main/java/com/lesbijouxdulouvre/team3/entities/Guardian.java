package com.lesbijouxdulouvre.team3.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lesbijouxdulouvre.team3.elements.VisionCone;

public class Guardian {

    private Vector2 position;
    private Vector2 velocity;
    private float width, height;

    private Texture texture;
    private TextureRegion sprite;

    private Rectangle bounds;

    private float pauseTimer = 0f;
    private VisionCone cone;

    private boolean facingRight = true;

    private float patrolMinX;
    private float patrolMaxX;
    private boolean hasPatrol = false;

    private float armOffsetX = 3.5f;
    private float armOffsetY = 7.5f;

    public Guardian(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        texture = new Texture("character/Guardian.png");
        sprite = new TextureRegion(texture);

        width = texture.getWidth();
        height = texture.getHeight();

        bounds = new Rectangle(x, y, width, height);

        cone = new VisionCone(x, y, 30f, 15f);
    }

    public Guardian stat(float velocityX, float velocityY, float minX, float maxX, Matrix4 projection) {
        this.setVelocity(velocityX, velocityY);
        this.setPatrolLimits(minX, maxX);
        this.cone.setProjectionMatrix(projection);
        return this;
    }

    public void update(float delta) {

        if (velocity.x > 0 && !facingRight) {
            sprite.flip(true, false);
            facingRight = true;
        } 
        else if (velocity.x < 0 && facingRight) {
            sprite.flip(true, false);
            facingRight = false;
        }

        if (pauseTimer > 0) {
            pauseTimer -= delta;
        } else {
            position.add(velocity.x * delta, velocity.y * delta);
            bounds.setPosition(position.x, position.y);
        }

        if (hasPatrol) {
            if (position.x < patrolMinX) {
                position.x = patrolMinX;
                bounds.setX(position.x);
                reverseDirectionWithPause(0.3f);
            } 
            else if (position.x + width > patrolMaxX) {
                position.x = patrolMaxX - width;
                bounds.setX(position.x);
                reverseDirectionWithPause(0.3f);
            }
        }

        float cx;
        float cy = position.y + armOffsetY;

        if (facingRight) 
            cx = position.x + width - armOffsetX;
        else 
            cx = position.x + armOffsetX;

        cone.setPosition(cx, cy);
        cone.setAngleFromVector(velocity.x, velocity.y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y);
    }

    public void renderCone() {
        cone.render();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle peekNextBounds(float delta) {
        return new Rectangle(
                position.x + velocity.x * delta,
                position.y + velocity.y * delta,
                width, height
        );
    }

    public void applyResolvedPosition(Rectangle corrected) {
        position.x = corrected.x;
        position.y = corrected.y;
        bounds.setPosition(corrected.x, corrected.y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public void reverseDirectionWithPause(float duration) {
        velocity.scl(-1);
        pauseTimer = duration;
    }

    public VisionCone getVisionCone() {
        return cone;
    }

    public void setPatrolLimits(float minX, float maxX) {
        patrolMinX = minX;
        patrolMaxX = maxX;
        hasPatrol = true;
    }

    public void dispose() {
        texture.dispose();
        cone.dispose();
    }
}