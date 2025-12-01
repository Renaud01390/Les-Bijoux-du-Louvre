package com.lesbijouxdulouvre.team3.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Matrix4;

public class Laser {

    private float x1, y1, x2, y2;
    private boolean visible = true;
    private float timer = 0f;
    private float visibleTime = 1.5f;
    private float invisibleTime = 1.0f;
    private float initialOffset;
    private ShapeRenderer renderer = new ShapeRenderer();
    private Matrix4 projection;

    public Laser(float x1, float y1, float x2, float y2, float initialOffset) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.initialOffset = initialOffset;
        this.timer = initialOffset;
    }

    public void update(float delta) {
        timer += delta;
        float cycle = visibleTime + invisibleTime;
        visible = (timer % cycle) < visibleTime;
    }

    public void render() {
        if (!visible) return;
        renderer.setProjectionMatrix(projection);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.rectLine(x1, y1, x2, y2, 4f);
        renderer.end();
    }

    public void setProjectionMatrix(Matrix4 projection) {
        this.projection = projection;
    }

    public Rectangle getHitbox() {
        float minX = Math.min(x1, x2) - 1.5f;
        float minY = Math.min(y1, y2);
        float h = Math.abs(y2 - y1);
        return new Rectangle(minX, minY, 3f, h);
    }

    public boolean isVisible() {
        return visible;
    }

    public void dispose() {
        renderer.dispose();
    }
}