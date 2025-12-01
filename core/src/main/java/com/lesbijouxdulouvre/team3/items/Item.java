package com.lesbijouxdulouvre.team3.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Item {
    protected int score;
    protected Texture texture;
    protected TextureRegion sprite;
    protected float x;
    protected float y;
    protected boolean collected = false;
    
    public abstract void render(SpriteBatch sb);
    
    public boolean isCollected() { return collected; }
    public void collect() { collected = true; }
    public int getScore() { return score; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return sprite.getRegionWidth(); }
    public float getHeight() { return sprite.getRegionHeight(); }
    
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}