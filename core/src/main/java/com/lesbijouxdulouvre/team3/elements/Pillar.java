package com.lesbijouxdulouvre.team3.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Pillar {
    private Texture texture;
    private float x;
    private float y;

    public Pillar(float x, float y) {
        this.texture = new Texture("element/pillar.png");
        this.x = x;
        this.y = y;
    }
    public void render(SpriteBatch sb) {
        sb.draw(texture, x, y);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
