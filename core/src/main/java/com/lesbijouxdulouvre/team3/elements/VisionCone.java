package com.lesbijouxdulouvre.team3.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;

public class VisionCone {

    private final ShapeRenderer sr = new ShapeRenderer();
    private float x, y;
    private float length;
    private float halfSpreadDeg;
    private float angleDeg;

    public VisionCone(float x, float y, float length, float spreadDeg) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.halfSpreadDeg = spreadDeg * 0.5f;
        this.angleDeg = 0f;
    }

    public void setProjectionMatrix(Matrix4 projection) {
        sr.setProjectionMatrix(projection);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setAngleFromVector(float vx, float vy) {
        if (vx == 0 && vy == 0) return;
        this.angleDeg = MathUtils.atan2(vy, vx) * MathUtils.radiansToDegrees;
    }

    public float[] getTriangleVertices() {
        float a = angleDeg;
        float a1 = (a - halfSpreadDeg) * MathUtils.degreesToRadians;
        float a2 = (a + halfSpreadDeg) * MathUtils.degreesToRadians;

        float x1 = x + MathUtils.cos(a1) * length;
        float y1 = y + MathUtils.sin(a1) * length;
        float x2 = x + MathUtils.cos(a2) * length;
        float y2 = y + MathUtils.sin(a2) * length;

        return new float[]{
                x, y,
                x1, y1,
                x2, y2
        };
    }

    public void render() {
        float a = angleDeg;
        float a1 = (a - halfSpreadDeg) * MathUtils.degreesToRadians;
        float a2 = (a + halfSpreadDeg) * MathUtils.degreesToRadians;

        float x1 = x + MathUtils.cos(a1) * length;
        float y1 = y + MathUtils.sin(a1) * length;
        float x2 = x + MathUtils.cos(a2) * length;
        float y2 = y + MathUtils.sin(a2) * length;

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1f, 1f, 0f, 0.25f));
        sr.triangle(x, y, x1, y1, x2, y2);
        sr.end();
    }

    public void dispose() {
        sr.dispose();
    }
}