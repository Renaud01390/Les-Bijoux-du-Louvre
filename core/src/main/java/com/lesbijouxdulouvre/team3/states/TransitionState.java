package com.lesbijouxdulouvre.team3.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lesbijouxdulouvre.team3.managers.GameStateManager;

public class TransitionState extends GameState {
    private Animation<TextureRegion> titleAnimation;
    private float stateTime;
    private float transitionDuration;
    private GameState newlevel;
    private Texture titleTexture;
    
    public TransitionState(GameStateManager gsm, GameState newlevel, float duration) {
        super(gsm);
        this.newlevel = newlevel;
        this.transitionDuration = duration;
        this.stateTime = 0f;
        titleTexture = new Texture("element/titlefinal.png");
        TextureRegion[][] tmp = TextureRegion.split(titleTexture, 72, 32);
        int totalFrames = tmp.length * tmp[0].length;
        TextureRegion[] animFrames = new TextureRegion[totalFrames];
        int index = 0;
        for (int row = 0; row < tmp.length; row++) {
            for (int col = 0; col < tmp[row].length; col++) {
                animFrames[index++] = tmp[row][col];
            }
        }
        titleAnimation = new Animation<>(0.1f, animFrames);
        titleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    
    @Override
    public void handleInput() {}
    
    @Override
    public void update(float dt) {
        stateTime += dt;
        if (stateTime >= transitionDuration) {
            gsm.setGameState(newlevel);
        }
    }
    
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        TextureRegion frame = titleAnimation.getKeyFrame(stateTime);
        float scale = 10f;
        float width = frame.getRegionWidth() * scale;
        float height = frame.getRegionHeight() * scale;
        float x = (cam.viewportWidth - width) / 2;
        float y = (cam.viewportHeight - height) / 2;
        sb.draw(frame, x, y, width, height);
        sb.end();
    }
    
    @Override
    public void dispose() {
        if (titleTexture != null) {
            titleTexture.dispose();
        }
    }
}