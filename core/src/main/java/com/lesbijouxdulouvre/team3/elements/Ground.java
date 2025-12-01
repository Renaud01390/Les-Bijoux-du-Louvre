package com.lesbijouxdulouvre.team3.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
    private Texture texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private float higher = 0;
    private float hitboxHeight = 5;
    
    public Ground(float startX, float startY) {
        this.texture = new Texture("element/Sooool.png");
        this.x = startX;
        this.y = startY;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }
    
    public void render(SpriteBatch sb) {
        sb.draw(texture, x, y);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y + higher, width, hitboxHeight);
    }
    
    public float getTopY() {
        return y + higher + hitboxHeight;
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