package com.lesbijouxdulouvre.team3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Voleur {
    private Texture texture;
    private TextureRegion sprite;
    private float x;
    private float y;
    private float velocityY;
    private boolean canJump;
    private boolean facingRight = true;
    private float animationTimer;
    private int currentFrame;
    private static final float MOVE_SPEED = 100f;
    private static final float GRAVITY = -500f;
    private static final float JUMP_FORCE = 225f;
    private static final float FRAME_DURATION = 0.1f;
    private static final int TOTAL_FRAMES = 8;
    private static final int MAX_X = 520;
    private static final int MIN_X = -10;
    
    public Voleur(float startX, float startY) {
        this.texture = new Texture("character/Voleur.png");
        this.sprite = new TextureRegion(texture, 0, 0, 32, 32);
        this.x = startX;
        this.y = startY;
        this.canJump = false;
    }
    
    public void handleInput() {
        boolean isMoving = false;
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (facingRight) {
                sprite.flip(true, false);
                facingRight = false;
            }
            x -= MOVE_SPEED * Gdx.graphics.getDeltaTime();
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (!facingRight) {
                sprite.flip(true, false);
                facingRight = true;
            }
            x += MOVE_SPEED * Gdx.graphics.getDeltaTime();
            isMoving = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && canJump) {
            velocityY = JUMP_FORCE;
            canJump = false;
        }
    }
    
    public void update(float dt) {
        dt = Math.min(dt, 0.05f);
        handleInput();
        velocityY += GRAVITY * dt;
        y += velocityY * dt;
        if (x < MIN_X) {
                x = MIN_X;
            }
            if (x + 32 > MAX_X) {
                x = MAX_X - 32;
            }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            animationTimer += dt;
            if (animationTimer >= FRAME_DURATION) {
                animationTimer = 0;
                currentFrame++;
                
                if (currentFrame >= TOTAL_FRAMES) {
                    currentFrame = 0;
                }
                int column = currentFrame % 3;
                int row = currentFrame / 3;
                int regionX = column * 32;
                int regionY = row * 32;
                sprite.setRegion(regionX, regionY, 32, 32);
                if (!facingRight && !sprite.isFlipX()) {
                    sprite.flip(true, false);
                } else if (facingRight && sprite.isFlipX()) {
                    sprite.flip(true, false);
                }
            }
        } else {
            currentFrame = 0;
            animationTimer = 0;
            sprite.setRegion(0, 0, 32, 32);
            if (!facingRight && !sprite.isFlipX()) {
                sprite.flip(true, false);
            } else if (facingRight && sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
    
    public void render(SpriteBatch sb) {
        sb.draw(sprite, x, y);
    }
    
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getVelocityY() { return velocityY; }
    public void setVelocityY(float velocityY){ this.velocityY = velocityY; }
    public float getWidth() { return 32; }
    public float getHeight() { return 32; }
}