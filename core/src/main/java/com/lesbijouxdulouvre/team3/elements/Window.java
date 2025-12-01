package com.lesbijouxdulouvre.team3.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Window {
    private Texture texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private float hitboxHeight;
    
    public Window(float x, float y) {
        this.texture = new Texture("element/Winwindowsfinal.png");
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.hitboxHeight = 10;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y + 10, width, hitboxHeight);
    }
    
    public float getTopY() {
        return y + hitboxHeight;
    }
    
    public void render(SpriteBatch sb) {
        sb.draw(texture, x, y);
    }
    
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}