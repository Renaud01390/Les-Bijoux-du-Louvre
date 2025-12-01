package com.lesbijouxdulouvre.team3.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Crown extends Item {
    private Animation<TextureRegion> animation;
    private float stateTime;
    
    public Crown(float x, float y) {
        this.texture = new Texture("item/Crown.png");
        this.x = x;
        this.y = y;
        this.score = 1000;
 
        TextureRegion[][] tmp = TextureRegion.split(texture, 12, 7);
        TextureRegion[] frames = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length && index < 16; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.1f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0f;
    }
    
    @Override
    public void render(SpriteBatch sb) {
        if (!collected) {
            stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime);
            sb.draw(currentFrame, x, y);
        }
    }
}