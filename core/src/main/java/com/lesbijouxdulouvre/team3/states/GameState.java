package com.lesbijouxdulouvre.team3.states;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lesbijouxdulouvre.team3.managers.GameStateManager;

public abstract class GameState {
    protected OrthographicCamera cam;
    protected GameStateManager gsm;
    protected Viewport viewport;
    protected static final float VIEWPORT_WIDTH = 800f;
    protected static final float VIEWPORT_HEIGHT = 480f;
    protected static final float ZOOM_LEVEL = 0.3f;
    private static final float CAM_MIN_X = 120f;
    private static final float CAM_MAX_X = 392f;
    private static final float CAM_MIN_Y = 72f;
    private static final float CAM_MAX_Y = Float.MAX_VALUE;
    protected GameState(GameStateManager gsm){
        this.cam = new OrthographicCamera();
        this.gsm = gsm;
        this.viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, cam);
        cam.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        cam.zoom = ZOOM_LEVEL;
    }
    protected void centerOnTarget(float targetX, float targetY) {
        float clampedX = Math.max(CAM_MIN_X, Math.min(targetX, CAM_MAX_X));
        float clampedY = Math.max(CAM_MIN_Y, Math.min(targetY, CAM_MAX_Y));
        cam.position.set(clampedX, clampedY, 0);
        cam.update();
    }
    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
